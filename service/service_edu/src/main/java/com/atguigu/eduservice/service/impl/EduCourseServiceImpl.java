package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.*;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.*;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-16
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService descriptionService;

    @Autowired
    EduCourseService courseService;

    @Autowired
    EduTeacherService teacherService;

    @Autowired
    EduChapterService chapterService;

    @Autowired
    EduVideoService videoService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 向课程表中添加基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert <= 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }

        // 向课程简介表中添加简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(eduCourse.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        descriptionService.save(courseDescription);

        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        EduCourseDescription description = descriptionService.getById(courseId);
        courseInfoVo.setDescription(description.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        descriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        CoursePublishVo courseInfo = baseMapper.getPublishCourseInfo(courseId);
        return courseInfo;
    }

    @Override
    public Map<String, Object> getCourseListPage(Integer current, Integer limit, CourseQuery courseQuery) {
        Page<EduCourse> coursePage = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(courseQuery.getOneSunjectId())) {
            wrapper.eq("subject_parent_id", courseQuery.getOneSunjectId());
        }

        if (!StringUtils.isEmpty(courseQuery.getTwoSubjectId())) {
            wrapper.eq("subject_id", courseQuery.getTwoSubjectId());
        }

        if (!StringUtils.isEmpty(courseQuery.getTitle())) {
            wrapper.like("title", courseQuery.getTitle());
        }

        if (!StringUtils.isEmpty(courseQuery.getTeacherId())) {
            wrapper.eq("teacher_id", courseQuery.getTeacherId());
        }

        wrapper.eq("status", "Normal");

        wrapper.orderByDesc("gmt_create");

        //不能使用baseMapper，要用本类来分页，不然返回的对象字段会有下划线
        courseService.page(coursePage, wrapper);

        List<EduCourse> records = coursePage.getRecords();

        for (EduCourse course : records) {
            EduTeacher teacher = teacherService.getById(course.getTeacherId());
            String name = "";
            if (teacher != null) {
                name = teacher.getName();
            }
            course.setTeacherName(name);
        }

        long total = coursePage.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("total", total);

        return map;
    }

    @Override
    public void removeCourse(String courseId) {
        // 1、根据课程id删除 小节
        //远程调用  删除阿里云端的视频
        videoService.removeVideoByCourseId(courseId);

        // 2、根据课程id删除 章节
        chapterService.removeChapterByCourseId(courseId);

        // 3、根据课程id删除 描述
        descriptionService.removeById(courseId);

        // 4、根据课程id删除 课程
        int result = baseMapper.deleteById(courseId);
        if (result == 0) {
            throw new GuliException(20001, "删除失败");
        }

    }

    @Override
    @Cacheable(value = "course", key = "'selectIndexList'")
    public List<EduCourse> getCourseRecords() {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("is_deleted", 0);
        courseQueryWrapper.orderByDesc("view_count");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> courses = baseMapper.selectList(courseQueryWrapper);
        return courses;
    }

    @Override
    public Map<String, Object> getCourseFrontList(long page, long limit, CourseFrontVo courseFrontVo) {
        Page<EduCourse> coursePage = new Page<>(page, limit);

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        //判断条件是否为空，不为空拼接
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {  //判断一级分类
            queryWrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {  //判断二级分类
            queryWrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {  //判断关注度
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage(coursePage, queryWrapper);

        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        CourseWebVo courseWebVo = baseMapper.getBaseCourseInfo(courseId);
        return courseWebVo;
    }
}
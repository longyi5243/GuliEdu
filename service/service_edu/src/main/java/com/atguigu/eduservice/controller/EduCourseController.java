package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 添加课程基本信息
     *
     * @param courseInfoVo
     * @return
     */
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    @GetMapping("/getCourseById")
    public R getCourseById(String courseId) {
        CourseInfoVo courseInfo = courseService.getCourseInfo(courseId);
        return R.ok().data("course", courseInfo);
    }

    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询确认的课程信息
    @PostMapping("/getPublishCourseInfo")
    public R getPublishCourseInfo(String courseId) {
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(courseId);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId) {
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    /**
     * 条件分页查询
     *
     * @param current
     * @param limit
     * @param courseQuery
     * @return
     */
    @PostMapping("/getCourseList/{current}/{limit}")
    public R getCourseList(@PathVariable Integer current,
                           @PathVariable Integer limit,
                           @RequestBody(required = false) CourseQuery courseQuery) {
        Map<String, Object> map = courseService.getCourseListPage(current, limit, courseQuery);
        return R.ok().data(map);
    }

    /**
     * 删除课程
     *
     * @param courseId
     * @return
     */
    @DeleteMapping("/deleteCourse")
    public R getCourseList(String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }

}


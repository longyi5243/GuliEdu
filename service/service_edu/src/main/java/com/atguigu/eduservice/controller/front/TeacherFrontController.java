package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    EduTeacherService teacherService;

    @Autowired
    EduCourseService courseService;

    /**
     * 名师分页列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/pageList/{pageNum}/{pageSize}")
    public R pageList(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Map<String, Object> map = teacherService.pageListWeb(pageNum, pageSize);
        return R.ok().data(map);
    }

    /**
     * 讲师详情页面数据
     * @param teacherId
     * @return
     */
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        // 1.根据讲师id查询讲师基本信息
        EduTeacher teacher = teacherService.getById(teacherId);

        // 2.根据讲师id查询所讲课程
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = courseService.list(queryWrapper);

        return R.ok().data("teacher", teacher).data("courseList", courseList);
    }

}

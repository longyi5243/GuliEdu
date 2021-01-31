package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;

/**
 * @author LY1
 * @create 2021-01-29 0:52
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    EduTeacherService teacherService;

    @Autowired
    EduCourseService courseService;

    /**
     * 查8条热门课程、4条名师记录
     *
     * @return
     */
    @GetMapping("/index")
    public R getAll() {
        //4条名师记录
        List<EduTeacher> teachers = teacherService.getTeacherRecords();

        //8条热门课程
        List<EduCourse> courses = courseService.getCourseRecords();

        HashMap<String, Object> map = new HashMap<>();
        map.put("teachers", teachers);
        map.put("courses", courses);

        return R.ok().data(map);
    }

}

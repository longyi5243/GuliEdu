package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CrouseFrontController {

    @Autowired
    EduCourseService courseService;

    /**
     * 课程列表条件查询
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public R pageList(@PathVariable("page") long page,
                      @PathVariable("limit") long limit,
                      @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Map<String, Object> map = courseService.getCourseFrontList(page, limit, courseFrontVo);
        return R.ok().data(map);
    }


}

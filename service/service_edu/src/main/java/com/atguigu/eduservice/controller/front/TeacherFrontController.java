package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    EduTeacherService teacherService;

    @GetMapping("/pageList/{pageNum}/{pageSize}")
    public R pageList(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Map<String, Object> map = teacherService.pageListWeb(pageNum, pageSize);
        return R.ok().data(map);
    }

}

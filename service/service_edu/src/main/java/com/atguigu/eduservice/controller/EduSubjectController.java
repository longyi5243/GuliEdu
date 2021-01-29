package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author LY
 * @create 2021-01-15 10:33
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    EduSubjectService subjectService;

    // 添加课程分类
    // 获取上传的文件，把文件内容读取出来
    @PostMapping("/upload")
    public R addSubject(MultipartFile file) {
        // 上传文件
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    //树形列表
    @GetMapping("/treeList")
    public R getAllSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }

}

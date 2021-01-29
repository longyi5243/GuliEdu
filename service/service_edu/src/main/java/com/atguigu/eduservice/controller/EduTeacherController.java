package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-05
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    //注入service
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询教师表中所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher() {
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageTeacher(@PathVariable Integer current, @PathVariable Integer limit) {
        try {
            Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);

            eduTeacherService.page(eduTeacherPage, null);

            long total = eduTeacherPage.getTotal();
            List<EduTeacher> records = eduTeacherPage.getRecords();
            return R.ok().data("total", total).data("rows", records);
        } catch (Exception e) {
            throw new GuliException(20001, "实行自定义异常处理");
        }
    }

    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable Integer current,
                                  @PathVariable Integer limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
//                                  String name, Integer level, String begin, String end) {

//        TeacherQuery teacherQuery = new TeacherQuery(name, level, begin, end);

        //创建Page分页对象
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);

        //创建QueryWrapper条件对象，构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        if (teacherQuery == null) {
            eduTeacherService.page(eduTeacherPage, null);
            Long total = eduTeacherPage.getTotal();
            List<EduTeacher> records = eduTeacherPage.getRecords();
            return R.ok().data("total", total).data("rows", records);
        }

        //多条件组合查询 动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level)) {
            //构建条件
            wrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            //构建条件
            wrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            //构建条件
            wrapper.le("gmt_create", end);
        }

        wrapper.orderByDesc("gmt_create");


        //调用方法实现条件查询分页
        eduTeacherService.page(eduTeacherPage, wrapper);

        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody(required = false) EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        }
        return R.error();
    }

}


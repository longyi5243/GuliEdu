package com.atguigu.staservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    StatisticsDailyService staService;

    /**
     * 远程调用Ucenter模块
     * 查询某个日期注册的人数
     *
     * @param day
     * @return
     */
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable("day") String day) {
        staService.registerCount(day);
        return R.ok();
    }

    /**
     * 图表显示
     * 返回两部分数组：
     * 1、日期json数组
     * 2、数量json数组
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable("type") String type,
                      @PathVariable("begin") String begin,
                      @PathVariable("end") String end){

        Map<String,Object> map = staService.showData(type,begin,end);
        return R.ok().data(map);

    }

}


package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-20
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {

        //添加记录之前，把统计表中相同日期的记录删除，保证统计表中每个日期只有一个
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        baseMapper.delete(queryWrapper);

        //远程调用Ucenter模块，查询某个日期注册的人数
        R registerR = ucenterClient.countRegister(day);
        Map<String, Object> data = registerR.getData();
        Integer countRegister = (Integer) data.get("countRegister");

        //创建统计对象，设置值
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(countRegister);
        statisticsDaily.setDateCalculated(day);

        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));

        //插入统计表
        baseMapper.insert(statisticsDaily);
    }
}

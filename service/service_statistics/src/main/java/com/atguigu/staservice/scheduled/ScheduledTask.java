package com.atguigu.staservice.scheduled;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    StatisticsDailyService staService;

    //每天凌晨一点执行，查询前一天的数据并添加到统计表中
    @Scheduled(cron = "0 0 1 * * ?")
    public void createStaDataAuto() {
        Date date = DateUtil.addDays(new Date(), -1);
        String day = DateUtil.formatDate(date);
        staService.registerCount(day);
    }

}

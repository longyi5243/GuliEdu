package com.atguigu.staservice.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcenterClient;
import org.springframework.stereotype.Component;

/**
 * @author LY
 * @create 2021-02-20 19:13
 */
@Component
public class UcenterClientFallback implements UcenterClient {
    @Override
    public R countRegister(String day) {
        return R.error().message("查询注册人数失败");
    }
}

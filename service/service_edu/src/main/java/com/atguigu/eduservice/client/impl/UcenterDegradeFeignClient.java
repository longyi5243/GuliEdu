package com.atguigu.eduservice.client.impl;

import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.entity.UcenterMember;
import org.springframework.stereotype.Component;

/**
 * @author LY
 * @create 2021-02-07 2:13
 */
@Component
public class UcenterDegradeFeignClient implements UcenterClient {
    @Override
    public UcenterMember getInfo(String token) {
        return null;
    }
}

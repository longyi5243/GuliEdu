package com.atguigu.eduorder.client.impl;

import com.atguigu.eduorder.entity.UcenterMember;
import com.atguigu.eduorder.client.UcenterClient;
import org.springframework.stereotype.Component;

/**
 * @author LY
 * @create 2021-02-10 17:51
 */
@Component
public class UcenterClientFallback implements UcenterClient {

    @Override
    public UcenterMember getUserInfoOrder(String memberId) {
        return null;
    }
}

package com.atguigu.eduorder.client;

import com.atguigu.eduorder.entity.UcenterMember;
import com.atguigu.eduorder.client.impl.UcenterClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author LY
 * @create 2021-02-10 17:50
 */
@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientFallback.class)
public interface UcenterClient {

    @PostMapping("/educenter/member/getUserInfoOrder/{memberId}")
    public UcenterMember getUserInfoOrder(@PathVariable("memberId") String memberId);

}

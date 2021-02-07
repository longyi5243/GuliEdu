package com.atguigu.eduservice.client;

import com.atguigu.eduservice.client.impl.UcenterDegradeFeignClient;
import com.atguigu.eduservice.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author LY
 * @create 2021-02-07 2:12
 */
@FeignClient(name = "service-ucenter", fallback = UcenterDegradeFeignClient.class)
@Component
public interface UcenterClient {

    @PostMapping("/educenter/member/getInfo/{token}")
    public UcenterMember getInfo(@PathVariable String token);

}

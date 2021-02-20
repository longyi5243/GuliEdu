package com.atguigu.staservice.client;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.impl.UcenterClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author LY
 * @create 2021-02-20 19:13
 */
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientFallback.class)
public interface UcenterClient {

    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);

}

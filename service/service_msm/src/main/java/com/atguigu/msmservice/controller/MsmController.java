package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author LY
 * @create 2021-01-31 22:31
 */
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    MsmService msmService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        //1.从redis中获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        //2.如果从redis中获取不到验证码，则生成随机值作为验证码，传给阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        //调用msmService中的方法发送短信
        boolean isSend = msmService.send(param, phone);
        if (isSend) {
            //发送成功，把发送成功的验证码存到redis中
            //同时设置有效时间
            //第一个参数：key  第二个参数：value  第三个参数：时间数值  第四个参数：时间数值的单位
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }

    }

}

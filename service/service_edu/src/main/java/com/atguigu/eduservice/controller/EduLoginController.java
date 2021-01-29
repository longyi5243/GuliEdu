package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LY
 * @create 2021-01-10 21:20
 */
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {

    @PostMapping("/login")
    public R login() {
        try {
            return R.ok().data("token", "admin");
        } catch (Exception e) {
            throw new GuliException(200001, "token异常");
        }
    }

    @GetMapping("/info")
    public R info() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("roles", "admin");
            map.put("name", "admin");
            map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            return R.ok().data(map);
        } catch (Exception e) {
            throw new GuliException(200001, "异常");
        }
    }

}

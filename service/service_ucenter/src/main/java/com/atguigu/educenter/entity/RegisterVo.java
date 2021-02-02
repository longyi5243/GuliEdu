package com.atguigu.educenter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterVo {

    private String nickname;

    private String mobile;

    private String password;

    //验证码
    private String code;

}
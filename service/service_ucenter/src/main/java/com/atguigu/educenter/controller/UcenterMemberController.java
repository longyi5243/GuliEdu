package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.RegisterVo;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-01
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    UcenterMemberService memberService;

    /**
     * 登录 返回token给前端，前端每一次请求都可以带着token来请求，获取用户信息
     *
     * @param member
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember member) {
        String token = memberService.login(member);
        return R.ok().data("token", token);
    }

    /**
     * 注册
     *
     * @param registerVo
     * @return
     */
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 根据token  前端每一次请求都可以带着token请求
     *
     * @param request
     * @return
     */
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用JWT工具类中的方法，根据request对象获取Head信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        //根据会员id查询用户信息
        UcenterMember member = memberService.getById(memberId);

        return R.ok().data("userInfo", member);
    }

    /**
     * 提供会员详细信息（远程调用）
     * @param token
     */
    @PostMapping("/getInfo/{token}")
    public UcenterMember getInfo(@PathVariable String token){
        String memberId = JwtUtils.getMemberIdByJwtTokenStr(token);
        UcenterMember member = memberService.getById(memberId);
        return member;
    }

    /**
     * 提供会员详细信息（远程调用）
     * @param memberId
     */
    @PostMapping("/getUserInfoOrder/{memberId}")
    public UcenterMember getUserInfoOrder(@PathVariable("memberId") String memberId){
        UcenterMember member = memberService.getById(memberId);
        return member;
    }

}


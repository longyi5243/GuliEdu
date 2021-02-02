package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.RegisterVo;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-01
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /**
     * 登录  校验、生成token、返回token
     *
     * @param member
     * @return
     */
    @Override
    public String login(UcenterMember member) {
        //取出手机号与密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号、密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "手机号或密码为空，登录失败");
        }

        //判断手机号是否存在
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(queryWrapper);
        if (mobileMember == null) {
            throw new GuliException(20001, "无此手机号，登录失败");
        }

        //判断密码
        //因为库中存储的是加密后的密码，所以先要把输入的密码进行加密，再与数据库中的密码进行比较
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001, "密码错误，登录失败");
        }

        //判断用户是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new GuliException(20001, "此账户已禁用，登录失败");
        }

        //登录成功，使用JWT生成token
        String token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return token;
    }

    /**
     * 注册
     *
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickName = registerVo.getNickname();
        String password = registerVo.getPassword();

        //手机号、密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code) || StringUtils.isEmpty(nickName)) {
            throw new GuliException(20001, "有数据为空，登录失败");
        }

        //判断验证码  从redis中获取手机号对应的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001,"验证码错误，注册失败");
        }

        //判断手机在库中唯一，不可重复
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count > 0){
            throw new GuliException(20001,"手机号已存在，注册失败");
        }

        //数据添加至数据库中
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setNickname(nickName);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("https://edu-client.oss-cn-beijing.aliyuncs.com/default.jpg");

        baseMapper.insert(ucenterMember);

    }
}

package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller// 注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    UcenterMemberService memberService;

    /**
     * 生成微信扫描的二维码
     *
     * @param session
     * @return
     */
    @GetMapping("/login")
    public String genQrConnect(HttpSession session) {

        // 固定地址 后面拼接参数  这种普通拼接方式执行效率低
        //String url = "https://open.weixin.qq.com/connect/qrconnect?appid" + ConstantWxUtils.WX_OPEN_APP_ID + "&response_type=code";

        // 微信开放平台授权baseUrl  %s：占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_uri进行URLEncoder编码
        String redirect_uri = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirect_uri = URLEncoder.encode(redirect_uri, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //向占位符中设置数据值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirect_uri,
                "atguigu"
        );

        // 重定向请求微信地址
        return "redirect:" + url;

    }

    /**
     * 微信扫码成功之后，点击登录的回调方法
     *
     * @return
     */
    @GetMapping("/callback")
    public String callback(String code, String state) {

        //第一步：获取code，临时票据，类似于验证码（微信扫码确认后回调本地接口时会传code）

        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问

        //第二步：拿着code请求微信提供的固定地址，获取两个值  access_token  openid
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        //拼接三个参数：id  密钥  code
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code
        );

        //请求这个拼接好的地址，得到返回的两个值  access_token  openid
        //使用httpclient发送请求，得到返回结果
        String resultUserInfo = null;
        try {
            resultUserInfo = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "获取access_token&openid失败");
        }

        //从resultUserInfo字符串中获取 access_token  openid
        //把resultUserInfo字符串转成Map集合，再根据Map中key获取对应值
        //使用转换工具 Gson  解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(resultUserInfo, HashMap.class);
        String access_token = (String) map.get("access_token");
        String openid = (String) map.get("openid");

        //第三步：拿着第二步得到的access_token、openid两个值，去请求一个微信提供的固定地址，最终得到微信扫码人的信息
        //查询数据库当前用用户是否曾经使用过微信登录
        UcenterMember member = memberService.getByOpenid(openid);
        if (member == null) {
            System.out.println("新用户注册");

            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
            String userInfo = null;
            try {
                //发送请求
                userInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                throw new GuliException(20001, "获取用户信息失败");
            }

            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) mapUserInfo.get("nickname");
            String headimgurl = (String) mapUserInfo.get("headimgurl");

            //向数据库中插入一条记录
            member = new UcenterMember();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }

        // 使用jwt根据member对象生成token字符串
        String jwtToken = JwtUtils.getJwtToken(member.getId(),member.getNickname());

        return "redirect:http://localhost:3000?token=" + jwtToken;
    }

}

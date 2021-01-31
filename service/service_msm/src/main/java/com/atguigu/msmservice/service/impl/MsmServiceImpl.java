package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author LY
 * @create 2021-01-31 22:32
 */
@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 发送短信
     *
     * @param param
     * @param phone
     * @return
     */
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if (StringUtils.isEmpty(phone)) return false;

        //设置阿里云的AK账密
        DefaultProfile profile = DefaultProfile.getProfile("default",
                "LTAI4G4y82f363EGnFgDdSaz", "wA4Ag60vcNs1OVcqJzkTq16mGAhI8z");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关的固定参数，不可改变
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数   其中的参数名称是固定，不可改变
        request.putQueryParameter("PhoneNumbers", phone);  //手机号
        request.putQueryParameter("SignName", "我的腾飞在线教育网站");  //申请的阿里云的签名名称
        request.putQueryParameter("TemplateCode", "SMS_211001290");  //申请的阿里云的模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));  //生成验证码的json字符串

        //最终的发送
        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}

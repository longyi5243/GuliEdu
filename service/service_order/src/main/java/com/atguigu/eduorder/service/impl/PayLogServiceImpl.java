package com.atguigu.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.entity.PayLog;
import com.atguigu.eduorder.mapper.PayLogMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.service.PayLogService;
import com.atguigu.eduorder.utils.HttpClient;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-07
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    OrderService orderService;

    @Override
    public Map createNative(String orderNo) {

        try {

            //1、根据订单号查询订单信息
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(queryWrapper);

            //2、使用map设置生成二维码需要的固定参数
            Map<String, String> map = new HashMap<>();
            map.put("appid", "wx74862e0dfcf69954");  //appid
            map.put("mch_id", "1558950191");  //商户号
            map.put("nonce_str", WXPayUtil.generateNonceStr());  //生成一个随机的字符串，使每一个二维码都不一样
            map.put("body", order.getCourseTitle());  //课程信息（课程标题）
            map.put("out_trade_no", orderNo);  //二维码的标识（订单号）
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");  //二维码中价格
            map.put("spbill_create_ip", "127.0.0.1");  //支付服务器的域名
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");  //回调地址
            map.put("trade_type", "NATIVE");  //根据价格生成二维码的类型

            //3、发送HttpClient请求，传递参数xml格式数据，微信支付提供的固定的地址
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml参数
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            httpClient.setHttps(true);
            //执行post发送请求
            httpClient.post();

            //4、得到发送请求的返回结果
            String xml = httpClient.getContent();
            //把xml格式转换成map集合，把map集合返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据的封装
            Map returnMap = new HashMap<>();
            returnMap.put("out_trade_no", orderNo);
            returnMap.put("course_id", order.getCourseId());
            returnMap.put("total_fee", order.getTotalFee());
            returnMap.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
            returnMap.put("code_url", resultMap.get("code_url"));  //二维码地址

            return returnMap;

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "生成二维码异常");
        }

    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {

            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、发送HttpClient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3、得到请求返回的内容
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void updateOrdersStatus(Map<String, String> map) {
        //从map中获取订单id
        String orderNo = map.get("out_trade_no");

        //根据订单号查询订单信息
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(queryWrapper);

        //更改订单状态
        if (order.getStatus().intValue() == 1) {
            return;
        }
        order.setStatus(1);  //1代表已支付
        orderService.updateById(order);

        //支付记录表添加记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(order.getOrderNo());  //支付订单号
        payLog.setPayTime(new Date());  //支付完成时间
        payLog.setPayType(1);  //支付类型 1：微信
        payLog.setTotalFee(order.getTotalFee());  //总金额(分)
        payLog.setTradeState(map.get("trade_state"));  //支付状态
        payLog.setTransactionId(map.get("transaction_id"));  //订单流水号
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);

    }

}

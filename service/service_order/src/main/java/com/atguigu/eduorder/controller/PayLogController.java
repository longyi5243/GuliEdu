package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-07
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    PayLogService payLogService;

    /**
     * 生成微信支付二维码
     *
     * @return
     */
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable("orderNo") String orderNo) {
        Map map = payLogService.createNative(orderNo);
        System.out.println("返回二维码map集合：" + map);
        return R.ok().data(map);
    }

    /**
     * 根据订单号查询订单支付状态
     * 在支付记录表中添加记录
     * 更改订单状态
     *
     * @return
     */
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable("orderNo") String orderNo) {
        Map map = payLogService.queryPayStatus(orderNo);
        System.out.println("查询订单状态map集合：" + map);

        if (map == null) {
            return R.error().message("支付出现异常");
        }

        //如果返回map里面不为空，通过map获取订单状态
        if (map.get("trade_state").equals("SUCCESS")) {  //支付成功
            //在支付记录表中添加记录，更改订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功").data(map);
        }

        return R.ok().code(25000).message("支付中");
    }

}


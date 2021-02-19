package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-07
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 点击立即购买，生成订单
     * 远程调用获取课程信息、会员信息
     *
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        String orderNo = orderService.createOrder(courseId, request);
        return R.ok().data("orderNo", orderNo);
    }

    /**
     * 根据订单编号查询订单
     *
     * @param orderNo
     * @return
     */
    @GetMapping("/getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable("orderNo") String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(queryWrapper);
        return R.ok().data("item", order);
    }

    /**
     * 根据课程id和用户id查询订单表中的订单状态
     *
     * @param courseId
     * @param memberId
     * @return
     */
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId) {

        boolean isBuyFlag = orderService.isBuyCourse(courseId, memberId);

        return isBuyFlag;

    }

}


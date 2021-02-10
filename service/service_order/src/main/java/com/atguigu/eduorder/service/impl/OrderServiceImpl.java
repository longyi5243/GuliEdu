package com.atguigu.eduorder.service.impl;

import com.atguigu.eduorder.client.CourseClient;
import com.atguigu.eduorder.entity.EduCourse;
import com.atguigu.eduorder.entity.UcenterMember;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-07
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    UcenterClient ucenterClient;

    @Autowired
    CourseClient courseClient;

    @Override
    public String createOrder(String courseId, HttpServletRequest request) {
        //获取会员信息
//        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String memberId = "1356782667089076225";
        UcenterMember member = ucenterClient.getUserInfoOrder(memberId);

        //获取课程信息
        EduCourse course = courseClient.getCourseInfoOrder(courseId);

        String orderNo = OrderNoUtil.getOrderNo();

        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setCourseId(courseId);
        order.setCourseCover(course.getCover());
        order.setCourseTitle(course.getTitle());
        order.setTeacherName(course.getTeacherName());
        order.setMemberId(memberId);
        order.setNickname(member.getNickname());
        order.setMobile(member.getMobile());
        order.setTotalFee(course.getPrice());
        order.setPayType(1);
        order.setStatus(0);

        baseMapper.insert(order);

        return orderNo;
    }
}

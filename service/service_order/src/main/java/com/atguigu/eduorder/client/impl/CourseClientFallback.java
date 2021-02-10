package com.atguigu.eduorder.client.impl;

import com.atguigu.eduorder.client.CourseClient;
import com.atguigu.eduorder.entity.EduCourse;
import org.springframework.stereotype.Component;

/**
 * @author LY
 * @create 2021-02-10 18:06
 */
@Component
public class CourseClientFallback implements CourseClient {
    @Override
    public EduCourse getCourseInfoOrder(String courseId) {
        return null;
    }
}

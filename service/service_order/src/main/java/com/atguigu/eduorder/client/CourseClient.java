package com.atguigu.eduorder.client;

import com.atguigu.eduorder.client.impl.CourseClientFallback;
import com.atguigu.eduorder.entity.EduCourse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author LY
 * @create 2021-02-10 18:05
 */
@Component
@FeignClient(name = "service-edu",fallback = CourseClientFallback.class)
public interface CourseClient {

    @GetMapping("/eduservice/coursefront/getCourseInfoOrder/{courseId}")
    public EduCourse getCourseInfoOrder(@PathVariable("courseId") String courseId);

}

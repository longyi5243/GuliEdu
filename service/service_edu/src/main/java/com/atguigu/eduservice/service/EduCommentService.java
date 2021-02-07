package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author LY
 * @create 2021-02-07 1:22
 */
public interface EduCommentService extends IService<EduComment> {
    Map<String, Object> getCommentListPage(long current, long limit, String courseId);

    boolean addComment(HttpServletRequest request, EduComment comment);
}

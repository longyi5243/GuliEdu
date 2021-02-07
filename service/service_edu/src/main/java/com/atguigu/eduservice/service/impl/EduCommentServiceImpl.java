package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.entity.UcenterMember;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.service.EduCommentService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LY
 * @create 2021-02-07 1:23
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    EduCommentService commentService;

    @Autowired
    UcenterClient ucenterClient;

    @Override
    public Map<String, Object> getCommentListPage(long current, long limit, String courseId) {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByDesc("gmt_create");

        Page<EduComment> page = new Page<>(current, limit);
        commentService.page(page, wrapper);

        List<EduComment> records = page.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", page.getCurrent());
        map.put("pages", page.getPages());
        map.put("size", page.getSize());
        map.put("total", page.getTotal());
        map.put("hasNext", page.hasNext());
        map.put("hasPrevious", page.hasPrevious());

        return map;

    }

    @Override
    public boolean addComment(HttpServletRequest request, EduComment comment) {
        String token = request.getHeader("token");
        UcenterMember member = ucenterClient.getInfo(token);

        if (member == null) {
            return false;
        }

        comment.setMemberId(member.getId());
        comment.setAvatar(member.getAvatar());
        comment.setNickname(member.getNickname());

        baseMapper.insert(comment);

        return true;
    }

}

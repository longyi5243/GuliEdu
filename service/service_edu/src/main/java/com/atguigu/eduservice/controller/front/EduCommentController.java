package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    EduCommentService commentService;

    /**
     * 评论分页列表
     *
     * @param current
     * @param limit
     * @return
     */
    @GetMapping("/getCommentListPage/{current}/{limit}")
    public R getCommentListPage(@PathVariable("current") long current,
                                @PathVariable("limit") long limit,
                                String courseId) {
        Map<String, Object> map = commentService.getCommentListPage(current, limit, courseId);
        return R.ok().data(map);
    }

    /**
     * 添加评论
     *
     * @param request
     * @return
     */
    @PostMapping("/addComment")
    public R getInfo(HttpServletRequest request, @RequestBody EduComment comment) {
        boolean flag = commentService.addComment(request, comment);
        if (flag) {
            return R.ok();
        }
        return R.error().message("用户未登录");

    }

}

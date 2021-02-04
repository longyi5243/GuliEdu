package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CrouseFrontController {

    @Autowired
    EduCourseService courseService;

    @Autowired
    EduChapterService chapterService;

    /**
     * 课程列表条件查询
     *
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public R pageList(@PathVariable("page") long page,
                      @PathVariable("limit") long limit,
                      @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Map<String, Object> map = courseService.getCourseFrontList(page, limit, courseFrontVo);
        return R.ok().data(map);
    }

    /**
     * 根据课程id查询课程信息
     * 根据课程id查询章节和小节
     * @param courseId
     * @return
     */
    @PostMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable("courseId") String courseId) {
        //根据课程id查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideo = chapterService.getChapterVideo(courseId);

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideo);
    }


}

package com.atguigu.eduservice.entity.vo;

import lombok.Data;

/**
 * @author LY
 * @create 2021-01-20 22:53
 */
@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示

}

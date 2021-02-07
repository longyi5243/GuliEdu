package com.atguigu.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author LY
 * @create 2021-02-07 1:25
 */
@Data
public class EduComment {

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String courseId;

    private String teacherId;

    private String memberId;

    private String nickname;

    private String avatar;

    private String content;

    @TableLogic
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}

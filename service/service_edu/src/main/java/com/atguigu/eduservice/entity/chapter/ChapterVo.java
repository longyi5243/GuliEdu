package com.atguigu.eduservice.entity.chapter;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LY
 * @create 2021-01-19 10:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterVo {

    private String id;

    private String courseId;

    private String title;

    private Integer sort;

    private List<EduVideo> children = new ArrayList<>();

}

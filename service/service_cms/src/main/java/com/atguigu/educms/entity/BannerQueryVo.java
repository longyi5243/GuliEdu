package com.atguigu.educms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LY
 * @create 2021-01-28 1:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerQueryVo {

    private String title;

    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    private String end;

}

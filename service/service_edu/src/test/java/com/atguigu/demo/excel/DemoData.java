package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LY
 * @create 2021-01-14 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoData {

    //设置excel表头的名称
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;

    //设置excel表头的名称
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;

}

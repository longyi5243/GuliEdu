package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LY
 * @create 2021-01-14 17:29
 */
public class TestEasyExcel {

    public static void main(String[] args) {
        //实现excel写操作
    }

    /**
     * 实现excel写操作，此方法会自动关流
     */
    @Test
    public void write() {
        // 1.设置写入文件夹的地址和excel文件名称
        String fileName = "F:\\write.xlsx";

        // 2.调用easyexcel里面的方法实现写操作
        //write()方法两个参数：第一个参数文件路径名称，第二个参数实体类class
        EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getData());
    }

    //创建方法返回list集合
    private static List<DemoData> getData() {
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy" + i);
            list.add(data);
        }
        return list;
    }

    /**
     * 实现excel读操作，此方法会自动关流
     */
    @Test
    public void read() {
        // 1.设置读取文件的地址和excel文件名称
        String fileName = "F:\\write.xlsx";

        // 2.调用easyexcel里面的方法实现读操作
        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
    }

}

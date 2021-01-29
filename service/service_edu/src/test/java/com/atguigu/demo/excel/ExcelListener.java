package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author LY
 * @create 2021-01-14 18:18
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {

    // 逐行读取excel内容
    @Override
    public void invoke(DemoData data, AnalysisContext analysisContext) {
        System.out.println("*****" + data);
    }

    // 读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

    // 读取完成之后执行该方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

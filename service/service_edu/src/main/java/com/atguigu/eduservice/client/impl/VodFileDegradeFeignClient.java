package com.atguigu.eduservice.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程调用的程序出错了才会执行以下对应的方法
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了/(ㄒoㄒ)/~~");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("批量删除视频出错了/(ㄒoㄒ)/~~");
    }
}

package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.AliyunVodSDKUtils;
import com.atguigu.vod.utils.ConstantVodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author LY
 * @create 2021-01-24 20:37
 */
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    VodService vodService;

    /**
     * 上传视频
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    /**
     * 删除阿里云端单个视频完全方法体
     */
//    @DeleteMapping("/removeAlyVideo/{id}")
//    public R removeAlyVideo(@PathVariable String id) {
//        try {
//            vodService.removeVideo(id);
//            return R.ok();
//        } catch (ClientException e) {
//            e.printStackTrace();
//            throw new GuliException(20001, "删除视频失败");
//        }
//    }

    /**
     * 简写  删除阿里云端单个视频
     *
     * @param id
     * @return
     */
    @DeleteMapping("/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        try {
            //初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    /**
     * 阿里云端视频批量删除
     *
     * @param videoList
     * @return
     */
    @DeleteMapping("/delete-batch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList) {
        vodService.removeMoreVideo(videoList);
        return R.ok();
    }

    /**
     * 获取视频凭证
     * @param videoId
     * @return
     */
    @GetMapping("/getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable("videoId") String videoId) {
        String playAuth = vodService.getPlayAuth(videoId);
        return R.ok().data("playAuth", playAuth);
    }

}

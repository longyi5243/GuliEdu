package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    EduVideoService videoService;

    @Autowired
    VodClient vodClient;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }

    @GetMapping("/getVideoById")
    public R getVideoById(String videoId) {
        EduVideo video = videoService.getById(videoId);
        return R.ok().data("video", video);
    }

    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId) {
        //获取小节id
        EduVideo video = videoService.getById(videoId);
        String sourceId = video.getVideoSourceId();

        if (!StringUtils.isEmpty(sourceId)) {
            //根据小节id删除阿里云端的视频  (远程调用)
            R result = vodClient.removeAlyVideo(sourceId);
            if (result.getCode() == 20001) {
                throw new GuliException(20001, "熔断器：删除视频失败");
            }
        }

        //根据小节id删除库中的数据
        videoService.removeById(videoId);
        return R.ok();
    }

}


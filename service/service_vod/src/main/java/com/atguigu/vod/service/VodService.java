package com.atguigu.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author LY
 * @create 2021-01-24 20:38
 */
public interface VodService {
    String uploadVideo(MultipartFile file);

    void removeVideo(String id);

    void removeMoreVideo(List videoList);

    String getPlayAuth(String videoId);
}

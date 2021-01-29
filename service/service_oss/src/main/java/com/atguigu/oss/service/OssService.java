package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author LY
 * @create 2021-01-13 22:55
 */
public interface OssService {

    public String uploadFileAvatar(MultipartFile file);

}

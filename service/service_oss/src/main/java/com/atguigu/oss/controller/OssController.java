package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LY
 * @create 2021-01-13 22:55
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R uploadOssFile(MultipartFile file) {
        //获取上传的文件  使用MultipartFile类
        String url = ossService.uploadFileAvatar(file);

        return R.ok().data("url", url);
    }

}

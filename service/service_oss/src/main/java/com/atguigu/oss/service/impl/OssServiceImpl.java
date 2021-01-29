package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstandPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author LY
 * @create 2021-01-13 22:56
 */
@Service
public class OssServiceImpl implements OssService {

    //上传文件
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstandPropertiesUtils.END_POIND;
        String accessKeyId = ConstandPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstandPropertiesUtils.KEY_SECRET;
        String bucketName = ConstandPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            // 获取文件的名称
            String fileName = file.getOriginalFilename();

            // 上传名字相同的文件会产生覆盖的情况  解决方法：给每个文件名加个唯一值
            // .replaceAll("-", "")  去除uuid中的 -
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            // 把文件按照日期进行分类
            // pom中导入了joda-time依赖，日期工具类
            // 获取当前日期
            String dataPath = new DateTime().toString("yyyy/MM/dd");
            fileName = dataPath + "/" + fileName;

            // 调用oss方法实现上传
            // 第一个参数：Bucket名称
            // 第二个参数：上传到oss文件路径和文件名称   /aa/bb/01.jpg  只传文件名，文件存在了oss的根目录下；传带有路径的文件名，oss会自动创建相应的文件路径并存储
            // 第三个参数：上传文件的输入流
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient
            ossClient.shutdown();

            // 把上传之后的文件路径返回
            // 需要把上传到阿里云oss路径手动拼接出来
            // 格式：https://edu-client.oss-cn-beijing.aliyuncs.com/05.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

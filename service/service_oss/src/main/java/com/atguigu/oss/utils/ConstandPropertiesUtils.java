package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author LY
 * @create 2021-01-13 22:38
 */
//当项目已启动，spring接口，spring加载之后，执行接口中一个方法
@Component
public class ConstandPropertiesUtils implements InitializingBean {

    //读取配置文件中的内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyid;

    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    //定义公开静态常量
    public static String END_POIND;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POIND = this.endpoint;
        KEY_ID = this.keyid;
        KEY_SECRET = this.keysecret;
        BUCKET_NAME = this.bucketname;
    }
}

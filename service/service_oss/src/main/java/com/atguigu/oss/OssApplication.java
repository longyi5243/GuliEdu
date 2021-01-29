package com.atguigu.oss;

import com.atguigu.oss.utils.ConstandPropertiesUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author LY
 * @create 2021-01-13 22:13
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  //exclude该属性不去加载配置文件中的数据源
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.atguigu"})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}

package com.atguigu.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author LY
 * @create 2021-01-31 22:23
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  //exclude该属性不去加载配置文件中的数据源
@ComponentScan(value = {"com.atguigu"})
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class, args);
    }
}

package com.atguigu.msmservice.service;

import java.util.Map;

/**
 * @author LY
 * @create 2021-01-31 22:32
 */
public interface MsmService {
    boolean send(Map<String, Object> param, String phone);
}

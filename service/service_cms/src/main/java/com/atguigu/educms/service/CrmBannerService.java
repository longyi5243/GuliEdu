package com.atguigu.educms.service;

import com.atguigu.educms.entity.BannerQueryVo;
import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-27
 */
public interface CrmBannerService extends IService<CrmBanner> {

    Map<String, Object> pageBanner(long current, long limit, BannerQueryVo bannerQueryVo);

    List<CrmBanner> getAllBanner();

}

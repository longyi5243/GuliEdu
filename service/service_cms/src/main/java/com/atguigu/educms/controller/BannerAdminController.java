package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.BannerQueryVo;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 后台banner管理接口
 * </p>
 *
 * @author testjava
 * @since 2021-01-27
 */
@RestController
@CrossOrigin
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {

    @Autowired
    CrmBannerService crmBannerService;

    /**
     * 前台首页轮播图分页查询
     *
     * @param current
     * @param limit
     * @return
     */
    @PostMapping("/pageBanner/{current}/{limit}")
    public R pageBanner(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) BannerQueryVo bannerQueryVo) {
        Map<String, Object> map = crmBannerService.pageBanner(current, limit, bannerQueryVo);
        return R.ok().data(map);
    }

    /**
     * 新增轮播图
     *
     * @param banner
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody CrmBanner banner) {
        crmBannerService.saveBanner(banner);
        return R.ok();
    }

    /**
     * 更新轮播图
     *
     * @param crmBanner
     * @return
     */
    @PutMapping("/update")
    public R update(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateBannerById(crmBanner);
        return R.ok();
    }

    /**
     * 删除轮播图
     *
     * @param id
     * @return
     */
    @PostMapping("/remove")
    public R remove(String id) {
        crmBannerService.removeBannerById(id);
        return R.ok();
    }

    /**
     * 根据id查询具体的轮播图
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public R getById(String id) {
        CrmBanner byId = crmBannerService.getById(id);
        return R.ok().data("item", byId);
    }

}


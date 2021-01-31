package com.atguigu.educms.service.impl;

import com.atguigu.educms.entity.BannerQueryVo;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-27
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Autowired
    CrmBannerService crmBannerService;

    @Override
    public Map<String, Object> pageBanner(long current, long limit, BannerQueryVo bannerQueryVo) {
        Page<CrmBanner> bannerPage = new Page<>(current, limit);

        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();

        if (bannerQueryVo != null) {
            if (!StringUtils.isEmpty(bannerQueryVo.getTitle())) {
                wrapper.like("title", bannerQueryVo.getTitle());
            }

            if (!StringUtils.isEmpty(bannerQueryVo.getBegin())) {
                //构建条件
                wrapper.ge("gmt_create", bannerQueryVo.getBegin());
            }

            if (!StringUtils.isEmpty(bannerQueryVo.getEnd())) {
                //构建条件
                wrapper.le("gmt_create", bannerQueryVo.getEnd());
            }
        }

        wrapper.orderByDesc("gmt_create");

        crmBannerService.page(bannerPage, wrapper);

        List<CrmBanner> records = bannerPage.getRecords();
        long total = bannerPage.getTotal();

        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("total", total);

        return map;
    }

    @Override
    @Cacheable(value = "banner", key = "'selectIndexList'")
    public List<CrmBanner> getAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted", 0);
        wrapper.orderByAsc("id");
        wrapper.last("limit 5");
        List<CrmBanner> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    @CacheEvict(value = "banner", key = "'banner::selectIndexList'", allEntries = true)
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    @Override
    @CacheEvict(value = "banner", key = "'banner::selectIndexList'", allEntries = true)
    public void updateBannerById(CrmBanner crmBanner) {
        baseMapper.updateById(crmBanner);
    }

    @Override
    @CacheEvict(value = "banner", key = "'banner::selectIndexList'", allEntries = true)
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }
}

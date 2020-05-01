package com.yc.fresh.busi.cache;

import com.yc.fresh.common.cache.service.impl.AbstractCacheServiceImpl;
import com.yc.fresh.common.cache.template.RedissonTemplate;
import com.yc.fresh.service.IGoodsSaleInfoService;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import org.springframework.stereotype.Component;

/**
 * Created by quy on 2020/4/22.
 * Motto: you can do it
 */
@Component
public class SaleGoodsCacheService extends AbstractCacheServiceImpl<GoodsSaleInfo> {

    public SaleGoodsCacheService(RedissonTemplate redissonTemplate, IGoodsSaleInfoService goodsSaleInfoService) {
        super(redissonTemplate, goodsSaleInfoService);
    }
}

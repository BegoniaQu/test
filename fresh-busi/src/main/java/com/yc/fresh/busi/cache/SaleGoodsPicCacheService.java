package com.yc.fresh.busi.cache;

import com.yc.fresh.busi.cache.key.RedisKeyUtils;
import com.yc.fresh.common.cache.service.impl.AbstractCacheServiceImpl;
import com.yc.fresh.common.cache.template.RedissonTemplate;
import com.yc.fresh.service.IGoodsSalePicService;
import com.yc.fresh.service.entity.GoodsSalePic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by quy on 2020/5/8.
 * Motto: you can do it
 */
@Component
@Slf4j
public class SaleGoodsPicCacheService extends AbstractCacheServiceImpl<GoodsSalePic, Long> {

    public SaleGoodsPicCacheService(RedissonTemplate redissonTemplate, IGoodsSalePicService goodsSalePicService) {
        super(redissonTemplate, goodsSalePicService);
    }


    public void cache(String goodsId, List<GoodsSalePic> pics) {
        String key = RedisKeyUtils.getSaleGdPics(goodsId);
        this.listAdd(key, pics);
    }

    public void unCache(String goodsId) {
        String key = RedisKeyUtils.getSaleGdPics(goodsId);
        this.listDel(key);
    }

    public void reCache(String goodsId, List<GoodsSalePic> pics) {
        unCache(goodsId);
        try {
            cache(goodsId, pics);//失败时,findPics方法会拿不到图片,
        } catch (Exception e) {
            log.error("{} cache pics  failed, so save to db", goodsId); //TODO 失败补偿
        }
    }

    public List<GoodsSalePic> findPics(String goodsId) {
        String key = RedisKeyUtils.getSaleGdPics(goodsId);
        return this.findT(key); //如果未获取到数据,并不打算从数据库拿
    }
}

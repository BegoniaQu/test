package com.yc.fresh.busi.cache;

import com.yc.fresh.busi.cache.key.RedisKeyUtils;
import com.yc.fresh.common.cache.service.impl.AbstractCacheServiceImpl;
import com.yc.fresh.common.cache.template.RedissonTemplate;
import com.yc.fresh.service.IGoodsSaleInfoService;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by quy on 2020/4/22.
 * Motto: you can do it
 */
@Component
@Slf4j
public class SaleGoodsCacheService extends AbstractCacheServiceImpl<GoodsSaleInfo, String> {

    public SaleGoodsCacheService(RedissonTemplate redissonTemplate, IGoodsSaleInfoService goodsSaleInfoService) {
        super(redissonTemplate, goodsSaleInfoService);
    }

    public void cache(GoodsSaleInfo t) {
        //去除不需要的字段,只缓存必要字段
        t.setLastModifiedTime(null);
        t.setCreateTime(null);
        this.add(t);
        //add mapping
        String key = RedisKeyUtils.getWarehouseFc2List(t.getWarehouseCode(), t.getFCategoryId());
        try {
            this.listAdd(key, t.getGoodsId());
        } catch (Exception e) {
            log.error("{} mapping {} cache failed, so save to db", key, t.getGoodsId()); //TODO 失败补偿
        }
    }

    public void unCache(GoodsSaleInfo t) {
        this.removeByPid(t.getGoodsId()); //确保先移除信息
        //rmv mapping
        String key = RedisKeyUtils.getWarehouseFc2List(t.getWarehouseCode(), t.getFCategoryId());
        try {
            this.listRmv(key, t.getGoodsId());
        } catch (Exception e) {
            log.error("{} mapping {} unCache failed, so save to db", key, t.getGoodsId()); //TODO 失败补偿
        }
    }

    public List<GoodsSaleInfo> findList(String warehouseCode, Integer fCategoryId) {
        String key = RedisKeyUtils.getWarehouseFc2List(warehouseCode, fCategoryId);
        List<String> saleGoodsIds = this.findS(key);
        if (CollectionUtils.isEmpty(saleGoodsIds)) {
            return Collections.emptyList();
        }
        List<GoodsSaleInfo> saleInfos = new ArrayList<>();
        //
        List<String> parts = new ArrayList<>();
        int len = 30; //据有人测试，len<50,redis性能和速度都是理想的
        int counter = 0;
        for (String id : saleGoodsIds) {
            parts.add(id);
            counter++;
            if (counter == len) {
                List<GoodsSaleInfo> ts = this.findT(parts);
                saleInfos.addAll(ts);
                counter = 0;
                parts.clear();
            }
        }
        if (counter > 0) { //未达到40个的处理掉
            List<GoodsSaleInfo> ts = this.findT(parts);
            saleInfos.addAll(ts);
        }
        return saleInfos;
    }


}

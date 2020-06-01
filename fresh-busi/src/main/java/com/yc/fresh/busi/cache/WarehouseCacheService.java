package com.yc.fresh.busi.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.fresh.busi.cache.key.RedisKeyUtils;
import com.yc.fresh.common.cache.service.impl.AbstractCacheServiceImpl;
import com.yc.fresh.common.cache.template.RedisTemplate;
import com.yc.fresh.service.IWarehouseService;
import com.yc.fresh.service.entity.Warehouse;
import com.yc.fresh.service.enums.WarehouseStatusEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by quy on 2020/5/29.
 * Motto: you can do it
 */
@Component
public class WarehouseCacheService extends AbstractCacheServiceImpl<Warehouse, String> {

    public WarehouseCacheService(RedisTemplate redisTemplate, IWarehouseService dbService) {
        super(redisTemplate, dbService);
    }


    public void removeAll() {
        String key = RedisKeyUtils.warehseList;
        this.listDel(key);
    }

    public List<Warehouse> findValidWarehouse() {
        String key = RedisKeyUtils.warehseList;
        List<Warehouse> list = this.findT(key);
        if (CollectionUtils.isEmpty(list)) {
            QueryWrapper<Warehouse> query = Wrappers.query();
            query.eq(Warehouse.STATUS, WarehouseStatusEnum.AVAILABLE.getV());
            list = dbService.list(query);
            if (CollectionUtils.isEmpty(list)) {
                return Collections.emptyList();
            }
            List<Warehouse> needCacheList = list.stream().map(t -> {
                Warehouse newOne = new Warehouse();
                newOne.setCode(t.getCode());
                newOne.setDeliveryFee(t.getDeliveryFee());
                newOne.setThresholdAmount(t.getThresholdAmount());
                newOne.setLocationX(t.getLocationX());
                newOne.setLocationY(t.getLocationY());
                return newOne;
            }).collect(Collectors.toList());
            this.listAdd(key, needCacheList);
        }
        return list;
    }

}

package com.yc.fresh.busi.cache;

import com.yc.fresh.busi.cache.key.RedisKeyUtils;
import com.yc.fresh.common.cache.service.impl.AbstractCacheServiceImpl;
import com.yc.fresh.common.cache.template.RedisTemplate;
import com.yc.fresh.common.exception.SCRedisRuntimeException;
import com.yc.fresh.service.IWarehouseStockService;
import com.yc.fresh.service.entity.WarehouseStock;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by quy on 2020/6/19.
 * Motto: you can do it
 */

@Component
public class SkuInventoryCacheService extends AbstractCacheServiceImpl<WarehouseStock, String> {

    public SkuInventoryCacheService(RedisTemplate redisTemplate, IWarehouseStockService warehouseStockService) {
        super(redisTemplate, warehouseStockService);
    }


    public void optInventory(String warehouseCode, Long skuId, int num) {
        String key = RedisKeyUtils.getWarehouseSkuInventory(warehouseCode, skuId);
        boolean result = super.redisTemplate.optNum(key, num, null);
        if (!result) {
            throw new SCRedisRuntimeException("cacheInventory failed");
        }
    }

    public Integer getInventory(String warehouseCode, Long skuId) {
        String key = RedisKeyUtils.getWarehouseSkuInventory(warehouseCode, skuId);
        Long num = super.redisTemplate.getNum(key);
        String str = num == null ? "0" : String.valueOf(num);
        return Integer.parseInt(str);
    }


    public void clearInventory(String warehouseCode, Long skuId) {
        String key = RedisKeyUtils.getWarehouseSkuInventory(warehouseCode, skuId);
        boolean result = super.redisTemplate.clearNum(key);
        if (!result) {
            throw new SCRedisRuntimeException("clearInventory failed");
        }
    }


    /**
     * 批量获取指定仓库的 sku的库存
     * @param warehouseCode
     * @param skuIds
     * @return
     */
    public Map<Long, Integer> findInventory(String warehouseCode, List<Long> skuIds) {
        Map<Long, Integer> skuInventoryMap = new HashMap<>();
        //
        List<String> keys = new ArrayList<>();
        int counter = 0;
        for (Long skuId : skuIds) {
            String key = RedisKeyUtils.getWarehouseSkuInventory(warehouseCode, skuId);
            keys.add(key);
            counter++;
            if (counter == 30) {
                Map<String, Integer> partMap = super.redisTemplate.findNums(keys);
                if (partMap == null) {
                    return skuInventoryMap;
                }
                for (Map.Entry<String, Integer> entry : partMap.entrySet()) {
                    skuInventoryMap.put(RedisKeyUtils.splitSkuId(entry.getKey()), entry.getValue());
                }
                counter = 0;
                keys.clear();
            }
        }
        if (counter > 0) {
            Map<String, Integer> partMap = super.redisTemplate.findNums(keys);
            if (partMap == null) {
                return skuInventoryMap;
            }
            for (Map.Entry<String, Integer> entry : partMap.entrySet()) {
                skuInventoryMap.put(RedisKeyUtils.splitSkuId(entry.getKey()), entry.getValue());
            }
        }

        return skuInventoryMap;
    }
}

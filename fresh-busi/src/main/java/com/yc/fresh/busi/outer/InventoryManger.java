package com.yc.fresh.busi.outer;

import com.yc.fresh.busi.cache.SkuInventoryCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by quy on 2020/6/19.
 * Motto: you can do it
 */
@Component
public class InventoryManger {

    private final SkuInventoryCacheService skuInventoryCacheService;

    @Autowired
    public InventoryManger(SkuInventoryCacheService skuInventoryCacheService) {
        this.skuInventoryCacheService = skuInventoryCacheService;
    }


    public Map<Long, Integer> findInventory(String warehouseCode, List<Long> skuIds) {
        return skuInventoryCacheService.findInventory(warehouseCode, skuIds);
    }

    public int getInventory(String warehouseCode, Long skuId) {
        return skuInventoryCacheService.getInventory(warehouseCode, skuId);
    }
}

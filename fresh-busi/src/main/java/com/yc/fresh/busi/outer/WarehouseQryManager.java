package com.yc.fresh.busi.outer;

import com.yc.fresh.busi.cache.WarehouseCacheService;
import com.yc.fresh.service.entity.Warehouse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by quy on 2020/6/1.
 * Motto: you can do it
 */
@Component
public class WarehouseQryManager {

    private final WarehouseCacheService warehouseCacheService;

    public WarehouseQryManager(WarehouseCacheService warehouseCacheService) {
        this.warehouseCacheService = warehouseCacheService;
    }


    public List<Warehouse> findValid() {
        return warehouseCacheService.findValidWarehouse();
    }
}

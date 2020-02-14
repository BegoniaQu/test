package com.yc.fresh.busi.validator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.fresh.service.IWarehouseStockService;
import com.yc.fresh.service.entity.WarehouseStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by quy on 2019/12/2.
 * Motto: you can do it
 */
@Component
public class WarehouseStockValidator implements Validator{

    private final IWarehouseStockService warehouseStockService;

    @Autowired
    public WarehouseStockValidator(IWarehouseStockService warehouseStockService) {
        this.warehouseStockService = warehouseStockService;
    }

    public WarehouseStock validate(String warehouseCode, Long skuId) {
        Assert.hasText(warehouseCode, getErrorMsg("null(warehouseCode) found"));
        Assert.notNull(skuId, getErrorMsg("null(skuId) found"));
        QueryWrapper<WarehouseStock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(WarehouseStock.WAREHOUSE_CODE, warehouseCode);
        queryWrapper.eq(WarehouseStock.SKU_ID, skuId);
        WarehouseStock one = warehouseStockService.getOne(queryWrapper);
        Assert.notNull(one, getErrorMsg("invalid(warehouseCode, skuId)"));
        return one;
    }
}

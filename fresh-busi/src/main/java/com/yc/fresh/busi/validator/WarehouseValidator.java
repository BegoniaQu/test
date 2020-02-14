package com.yc.fresh.busi.validator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.fresh.service.IWarehouseService;
import com.yc.fresh.service.entity.Warehouse;
import com.yc.fresh.service.enums.WarehouseStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by quy on 2019/12/1.
 * Motto: you can do it
 */

@Component
public class WarehouseValidator implements Validator{

    private final IWarehouseService warehouseService;

    @Autowired
    public WarehouseValidator(IWarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }


    public Warehouse validateWarehouseCode(String warehouseCode) {
        Assert.hasText(warehouseCode, getErrorMsg("null(warehouseCode) found"));
        QueryWrapper<Warehouse> qryWrapper = new QueryWrapper<>();
        qryWrapper.eq(Warehouse.CODE, warehouseCode);
        Warehouse one = this.warehouseService.getOne(qryWrapper);
        Assert.notNull(one, getErrorMsg("unknown(warehouseCode)"));
        Assert.isTrue(one.getStatus() == WarehouseStatusEnum.AVAILABLE.getV(), getErrorMsg("invalid(warehouseCode)"));
        return one;
    }
}

package com.yc.fresh.busi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yc.fresh.common.ServiceAssert;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.IWarehouseService;
import com.yc.fresh.service.entity.Warehouse;
import com.yc.fresh.service.enums.WarehouseStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by quy on 2019/11/25.
 * Motto: you can do it
 */
@Component
public class WarehouseManager {

    private final IWarehouseService warehouseService;

    @Autowired
    public WarehouseManager(IWarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void doAdd(Warehouse t) {
        boolean isOk = this.warehouseService.save(t);
        ServiceAssert.isOk(isOk, "add warehouse failed");
    }

    @Transactional(rollbackFor = Exception.class)
    public void doUpdateByCode(Warehouse t) {
        String code = t.getCode();
        t.setCode(null);
        UpdateWrapper<Warehouse> wrapper = new UpdateWrapper<>();
        wrapper.eq(Warehouse.CODE, code);
        boolean isOk = this.warehouseService.update(t, wrapper);
        ServiceAssert.isOk(isOk, "update warehouse failed");
    }

    @Transactional(rollbackFor = Exception.class)
    public void doClose(String warehouseCode) {
        Warehouse t = new Warehouse();
        t.setLastModifiedTime(DateUtils.getCurrentDate());
        t.setStatus(WarehouseStatusEnum.INVALID.getV());
        //
        UpdateWrapper<Warehouse> wrapper = new UpdateWrapper<>();
        wrapper.eq(Warehouse.CODE, warehouseCode);
        wrapper.eq(Warehouse.STATUS, WarehouseStatusEnum.AVAILABLE.getV());

        boolean isOk = this.warehouseService.update(t, wrapper);
        ServiceAssert.isOk(isOk, "close warehouse failed");
    }

    public Warehouse getByCode(String code) {
        QueryWrapper<Warehouse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Warehouse.CODE, code);
        return this.warehouseService.getOne(queryWrapper);
    }

    @Transactional(readOnly = true)
    public IPage<Warehouse> page(String name, String city, Integer status, IPage<Warehouse> iPage) {
        QueryWrapper<Warehouse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like(Warehouse.NAME, name);
        }
        if (!StringUtils.isEmpty(city)) {
            queryWrapper.like(Warehouse.CITY, name);
        }
        if (status != null) {
            queryWrapper.eq(Warehouse.STATUS, status);
        }
        return this.warehouseService.page(iPage, queryWrapper);
    }


    public List<Warehouse> query(Integer status) {
        QueryWrapper<Warehouse> qryWrapper = new QueryWrapper<>();
        if (status != null) {
            qryWrapper.eq(Warehouse.STATUS, status);
        }
        return this.warehouseService.list(qryWrapper);
    }


}

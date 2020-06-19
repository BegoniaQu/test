package com.yc.fresh.busi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.fresh.service.enums.SkuStatusEnum;
import com.yc.fresh.busi.validator.GdCategoryValidator;
import com.yc.fresh.busi.validator.SkuValidator;
import com.yc.fresh.common.ServiceAssert;
import com.yc.fresh.service.ISkuInfoService;
import com.yc.fresh.service.IWarehouseStockService;
import com.yc.fresh.service.entity.SkuInfo;
import com.yc.fresh.service.entity.WarehouseStock;
import com.yc.fresh.service.enums.UnitTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by quy on 2019/11/27.
 * Motto: you can do it
 */
@Component
public class SkuManager {

    private final ISkuInfoService skuInfoService;
    private final GdCategoryValidator gdCategoryValidator;
    private final SkuValidator skuValidator;
    private final IWarehouseStockService warehouseStockService;

    @Autowired
    public SkuManager(ISkuInfoService skuInfoService, GdCategoryValidator gdCategoryValidator, SkuValidator skuValidator, IWarehouseStockService warehouseStockService) {
        this.skuInfoService = skuInfoService;
        this.gdCategoryValidator = gdCategoryValidator;
        this.skuValidator = skuValidator;
        this.warehouseStockService = warehouseStockService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void doAdd(SkuInfo t) {
        gdCategoryValidator.validate(t.getFCategoryId(), t.getSCategoryId());
        UnitTypeEnum.verify(t.getUnitType());
        boolean isOk = skuInfoService.save(t);
        ServiceAssert.isOk(isOk, "add sku failed");
    }

    @Transactional(rollbackFor = Exception.class)
    public void doUpdate(SkuInfo t) {
        gdCategoryValidator.validate(t.getFCategoryId(), t.getSCategoryId());
        SkuInfo sku = skuValidator.validateSkuId(t.getSkuId());
        Assert.isTrue(sku.getStatus() == SkuStatusEnum.unuse.getV(), "SKU已被使用, 无法更新");
        UnitTypeEnum.verify(t.getUnitType());
        //要没有库存
       /* QueryWrapper<WarehouseStock> queryWrapper = Wrappers.query();
        queryWrapper.eq(WarehouseStock.SKU_ID, t.getSkuId());
        List<WarehouseStock> list = this.warehouseStockService.list(queryWrapper);
        Assert.isTrue(CollectionUtils.isEmpty(list), "已添加库存, 不能更新");*/
        boolean isOk = skuInfoService.updateById(t);
        ServiceAssert.isOk(isOk, "update sku failed");
    }


    public SkuInfo doGet(Long skuId) {
        return skuValidator.validateSkuId(skuId);
    }

    @Transactional(readOnly = true)
    public IPage<SkuInfo> page(String skuName, Integer fCategoryId, Integer sCategoryId, IPage<SkuInfo> iPage) {
        QueryWrapper<SkuInfo> wrapper = Wrappers.query();
        if (!StringUtils.isEmpty(skuName)) {
            wrapper.like(SkuInfo.SKU_NAME, skuName);
        }
        if (fCategoryId != null) {
            wrapper.eq(SkuInfo.F_CATEGORY_ID, fCategoryId);
        }
        if (sCategoryId != null) {
            wrapper.eq(SkuInfo.S_CATEGORY_ID, sCategoryId);
        }
        return this.skuInfoService.page(iPage, wrapper);
    }
}

package com.yc.fresh.busi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.fresh.service.enums.SkuStatusEnum;
import com.yc.fresh.busi.validator.SkuValidator;
import com.yc.fresh.busi.validator.WarehouseStockValidator;
import com.yc.fresh.busi.validator.WarehouseValidator;
import com.yc.fresh.common.ServiceAssert;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.IGoodsSaleInfoService;
import com.yc.fresh.service.ISkuInfoService;
import com.yc.fresh.service.IWarehouseStockService;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.SkuInfo;
import com.yc.fresh.service.entity.WarehouseStock;
import com.yc.fresh.service.enums.SaleGoodsStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by quy on 2019/12/1.
 * Motto: you can do it
 */
@Component
public class WarehouseStockManager {

    private final IWarehouseStockService warehouseStockService;
    private final WarehouseValidator warehouseValidator;
    private final SkuValidator skuValidator;
    private final WarehouseStockValidator warehouseStockValidator;
    private final IGoodsSaleInfoService goodsSaleInfoService;
    private final ISkuInfoService skuInfoService;


    @Autowired
    public WarehouseStockManager(IWarehouseStockService warehouseStockService, WarehouseValidator warehouseValidator, SkuValidator skuValidator, WarehouseStockValidator warehouseStockValidator, IGoodsSaleInfoService goodsSaleInfoService, ISkuInfoService skuInfoService) {
        this.warehouseStockService = warehouseStockService;
        this.warehouseValidator = warehouseValidator;
        this.skuValidator = skuValidator;
        this.warehouseStockValidator = warehouseStockValidator;
        this.goodsSaleInfoService = goodsSaleInfoService;
        this.skuInfoService = skuInfoService;
    }

    /**
     * 新增库存商品以及初始化库存
     * @param t
     */
    @Transactional(rollbackFor = Exception.class)
    public void doAdd(WarehouseStock t) {
        warehouseValidator.validateWarehouseCode(t.getWarehouseCode());
        SkuInfo skuInfo = skuValidator.validateSkuId(t.getSkuId());
        t.setSkuName(skuInfo.getSkuName());
        t.setFCategoryId(skuInfo.getFCategoryId());
        t.setSCategoryId(skuInfo.getSCategoryId());
        t.setUnit(skuInfo.getUnit());
        t.setUnitType(skuInfo.getUnitType());
        t.setSpec(skuInfo.getSpec());
        boolean flag = this.warehouseStockService.save(t);
        ServiceAssert.isOk(flag, "add warehouseStock failed");
        //更新sku status
        if (skuInfo.getStatus() == SkuStatusEnum.unuse.getV()) {
            SkuInfo skuUpt = new SkuInfo();
            skuUpt.setSkuId(t.getSkuId());
            skuUpt.setStatus(SkuStatusEnum.used.getV());
            skuUpt.setLastModifiedTime(DateUtils.getCurrentDate());
            this.skuInfoService.updateById(skuUpt);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void doDel(String warehouseCode, Long skuId) {
        WarehouseStock t = warehouseStockValidator.validate(warehouseCode, skuId);
        QueryWrapper<GoodsSaleInfo> queryWrapper = Wrappers.query();
        queryWrapper.eq(GoodsSaleInfo.WAREHOUSE_CODE, warehouseCode);
        queryWrapper.eq(GoodsSaleInfo.SKU_ID, skuId);
        queryWrapper.gt(GoodsSaleInfo.STATUS, SaleGoodsStatusEnum.INVALID.getV());
        List<GoodsSaleInfo> goodsSaleInfos = this.goodsSaleInfoService.list(queryWrapper);
        Assert.isTrue(CollectionUtils.isEmpty(goodsSaleInfos), "已添加售卖商品, 无法删除");
        this.warehouseStockService.removeById(t.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStockNum(String warehouseCode, Long skuId, Integer num, BigDecimal costPrice) {
        WarehouseStock t = warehouseStockValidator.validate(warehouseCode, skuId);
        //update
        UpdateWrapper<WarehouseStock> updateWrapper = new UpdateWrapper<>();
        Assert.isTrue(num != null || costPrice != null, "not found necessary param");
        if (costPrice != null) {
            updateWrapper.set(WarehouseStock.COST_PRICE, costPrice);
        }
        if (num != null) {
            updateWrapper.setSql(String.format("num = num + %d", num));
        }
        updateWrapper.set(WarehouseStock.LAST_MODIFIED_TIME, DateUtils.getCurrentDate());
        updateWrapper.eq(WarehouseStock.ID, t.getId());
        boolean flag = this.warehouseStockService.update(updateWrapper);
        ServiceAssert.isOk(flag, "update warehouseStock failed");
    }

    @Transactional(readOnly = true)
    public IPage<WarehouseStock> page(String warehouseCode, String skuName, Integer fCategoryId ,Integer sCategoryId, IPage<WarehouseStock> iPage) {
        QueryWrapper<WarehouseStock> wrapper = Wrappers.query();
        if (!StringUtils.isEmpty(warehouseCode)) {
            wrapper.eq(WarehouseStock.WAREHOUSE_CODE, warehouseCode);
        }
        if (!StringUtils.isEmpty(skuName)) {
            wrapper.like(WarehouseStock.SKU_NAME, skuName);
        }
        if (fCategoryId != null) {
            wrapper.eq(WarehouseStock.F_CATEGORY_ID, fCategoryId);
        }
        if (sCategoryId != null) {
            wrapper.eq(WarehouseStock.S_CATEGORY_ID, sCategoryId);
        }
        return this.warehouseStockService.page(iPage, wrapper);
    }
}

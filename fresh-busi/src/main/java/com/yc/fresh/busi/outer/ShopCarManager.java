package com.yc.fresh.busi.outer;

import com.yc.fresh.busi.cache.SaleGoodsCacheService;
import com.yc.fresh.busi.cache.ShopCarCacheService;
import com.yc.fresh.busi.cache.SkuInventoryCacheService;
import com.yc.fresh.busi.enums.GoodsStateEnum;
import com.yc.fresh.common.exception.SCApiRuntimeException;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.ShoppingCar;
import com.yc.fresh.service.enums.SaleGoodsStatusEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by quy on 2020/5/21.
 * Motto: you can do it
 */

@Component
public class ShopCarManager {

    private final ShopCarCacheService shopCarCacheService;
    private final SaleGoodsCacheService saleGoodsCacheService;
    private final SkuInventoryCacheService skuInventoryCacheService;

    public ShopCarManager(ShopCarCacheService shopCarCacheService, SaleGoodsCacheService saleGoodsCacheService, SkuInventoryCacheService skuInventoryCacheService) {
        this.shopCarCacheService = shopCarCacheService;
        this.saleGoodsCacheService = saleGoodsCacheService;
        this.skuInventoryCacheService = skuInventoryCacheService;
    }


    public List<ShoppingCar> find(Long userId) {
        return this.shopCarCacheService.findCar(userId);
    }


    public int populate(ShoppingCar t, String warehouseCode) {
        GoodsSaleInfo goodsSaleInfo = saleGoodsCacheService.getT(t.getGoodsId());
        Assert.notNull(goodsSaleInfo, "illegal request");
        if (goodsSaleInfo.getStatus() != SaleGoodsStatusEnum.SALEABLE.getV()) {
            return GoodsStateEnum.gdDown.getState();
        }
        Integer inventory = this.skuInventoryCacheService.getInventory(warehouseCode, goodsSaleInfo.getSkuId());
        if (inventory <= 0) {
            return GoodsStateEnum.saleOut.getState();
        }
        //
        boolean isOk = shopCarCacheService.populateCar(t);
        if (!isOk) {
            throw new SCApiRuntimeException();
        }
        return GoodsStateEnum.ok.getState();
    }

    public void remove(ShoppingCar t) {
        boolean isOk = shopCarCacheService.removeFromCar(t);
        if (!isOk) {
            throw new SCApiRuntimeException();
        }
    }

    public void clean(Long userId) {
        boolean isOk = shopCarCacheService.delCar(userId);
        if (!isOk) {
            throw new SCApiRuntimeException();
        }
    }
}

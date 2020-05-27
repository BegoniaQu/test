package com.yc.fresh.busi.outer;

import com.yc.fresh.busi.cache.SaleGoodsCacheService;
import com.yc.fresh.busi.cache.ShopCarCacheService;
import com.yc.fresh.common.exception.SCApiRuntimeException;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.ShoppingCar;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by quy on 2020/5/21.
 * Motto: you can do it
 */

@Component
public class ShopCarManager {

    private final ShopCarCacheService shopCarCacheService;
    private final SaleGoodsCacheService saleGoodsCacheService;

    public ShopCarManager(ShopCarCacheService shopCarCacheService, SaleGoodsCacheService saleGoodsCacheService) {
        this.shopCarCacheService = shopCarCacheService;
        this.saleGoodsCacheService = saleGoodsCacheService;
    }


    public List<ShoppingCar> find(Long userId) {
        return this.shopCarCacheService.findCar(userId);
    }


    public void populate(ShoppingCar t) {
        GoodsSaleInfo goodsSaleInfo = saleGoodsCacheService.getByPid(t.getGoodsId());
        if (goodsSaleInfo == null) {
            throw new SCApiRuntimeException("该商品下架啦");
        }
        if (goodsSaleInfo.getInventory() <= 0) {
            throw new SCApiRuntimeException("该商品卖完啦");
        }
        boolean isOk = shopCarCacheService.populateCar(t);
        if (!isOk) {
            throw new SCApiRuntimeException();
        }
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

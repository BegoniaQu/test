package com.yc.fresh.busi.outer;

import com.yc.fresh.busi.cache.SaleGoodsPicCacheService;
import com.yc.fresh.busi.cache.SaleGoodsCacheService;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.GoodsSalePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */
@Component
public class SaleGoodsQryManager {

    private final SaleGoodsCacheService saleGoodsCacheService;
    private final SaleGoodsPicCacheService saleGoodsPicCacheService;

    @Autowired
    public SaleGoodsQryManager(SaleGoodsCacheService saleGoodsCacheService, SaleGoodsPicCacheService saleGoodsPicCacheService) {
        this.saleGoodsCacheService = saleGoodsCacheService;
        this.saleGoodsPicCacheService = saleGoodsPicCacheService;
    }


    public List<GoodsSaleInfo> findSaleGoods(String warehouseCode, Integer fCategoryId) {
        return saleGoodsCacheService.findList(warehouseCode, fCategoryId);
    }

    public GoodsSaleInfo getByGoodsId(String goodsId) {
        return saleGoodsCacheService.getByPid(goodsId);
    }

    public List<GoodsSalePic> findSaleGdPics(String goodsId) {
        return saleGoodsPicCacheService.findPics(goodsId);
    }
}

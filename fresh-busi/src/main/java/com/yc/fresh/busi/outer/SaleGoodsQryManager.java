package com.yc.fresh.busi.outer;

import com.yc.fresh.busi.cache.SaleGoodsPicCacheService;
import com.yc.fresh.busi.cache.SaleGoodsCacheService;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.GoodsSalePic;
import com.yc.fresh.service.enums.SaleGoodsStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        List<GoodsSaleInfo> list = saleGoodsCacheService.findList(warehouseCode, fCategoryId);
        //因为当其他地方调用getT时 内存中可能会出现非可售状态的商品,没有库存的可以依旧显示在页面
        return list.stream().
                //filter(t->t.getInventory() > 0).
                filter(t->t.getStatus() == SaleGoodsStatusEnum.SALEABLE.getV()).
                collect(Collectors.toList());
    }

    public List<GoodsSaleInfo> findSaleGoods(List<String> goodsIds) {
        return saleGoodsCacheService.findList(goodsIds);
    }

    public GoodsSaleInfo getByGoodsId(String goodsId) {
        return saleGoodsCacheService.getT(goodsId);
    }

    public List<GoodsSalePic> findSaleGdPics(String goodsId) {
        return saleGoodsPicCacheService.findPics(goodsId);
    }

    public List<GoodsSaleInfo> doSearch(String name, String warehouseCode) {
        List<GoodsSaleInfo> goodsSaleInfos = saleGoodsCacheService.search(name, warehouseCode);
        return goodsSaleInfos.stream().
                filter(t->t.getStatus() == SaleGoodsStatusEnum.SALEABLE.getV()).
                collect(Collectors.toList());
    }
}

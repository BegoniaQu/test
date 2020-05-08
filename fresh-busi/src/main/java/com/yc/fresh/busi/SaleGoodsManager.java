package com.yc.fresh.busi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.fresh.busi.cache.SaleGoodsCacheService;
import com.yc.fresh.busi.cache.SaleGoodsPicCacheService;
import com.yc.fresh.busi.validator.SaleGoodsValidator;
import com.yc.fresh.busi.validator.WarehouseStockValidator;
import com.yc.fresh.common.ServiceAssert;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.IGoodsSaleInfoService;
import com.yc.fresh.service.IGoodsSalePicService;
import com.yc.fresh.service.ISkuInfoService;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.GoodsSalePic;
import com.yc.fresh.service.entity.SkuInfo;
import com.yc.fresh.service.entity.WarehouseStock;
import com.yc.fresh.service.enums.SaleGoodsStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by quy on 2019/12/2.
 * Motto: you can do it
 */
@Component
public class SaleGoodsManager {

    private final IGoodsSaleInfoService goodsSaleInfoService;
    private final ISkuInfoService skuInfoService;
    private final IGoodsSalePicService goodsSalePicService;
    private final WarehouseStockValidator warehouseStockValidator;
    private final SaleGoodsValidator saleGoodsValidator;
    private final SaleGoodsCacheService saleGoodsCacheService;
    private final SaleGoodsPicCacheService saleGoodsPicCacheService;

    @Autowired
    public SaleGoodsManager(IGoodsSaleInfoService goodsSaleInfoService, WarehouseStockValidator warehouseStockValidator, ISkuInfoService skuInfoService, IGoodsSalePicService goodsSalePicService, SaleGoodsValidator saleGoodsValidator, SaleGoodsCacheService saleGoodsCacheService, SaleGoodsPicCacheService saleGoodsPicCacheService) {
        this.goodsSaleInfoService = goodsSaleInfoService;
        this.warehouseStockValidator = warehouseStockValidator;
        this.skuInfoService = skuInfoService;
        this.goodsSalePicService = goodsSalePicService;
        this.saleGoodsValidator = saleGoodsValidator;
        this.saleGoodsCacheService = saleGoodsCacheService;
        this.saleGoodsPicCacheService = saleGoodsPicCacheService;
    }


    @Transactional(rollbackFor = Exception.class)
    public void doAdd(GoodsSaleInfo t) {
        WarehouseStock ws = warehouseStockValidator.validate(t.getWarehouseCode(), t.getSkuId());
        SkuInfo sku = skuInfoService.getById(t.getSkuId());
        t.setFCategoryId(sku.getFCategoryId());
        t.setSCategoryId(sku.getSCategoryId());
        t.setUnit(ws.getUnit());
        boolean flag = goodsSaleInfoService.save(t);
        ServiceAssert.isOk(flag, "add saleGoods failed");
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String goodsId, Integer newStatus) {
        GoodsSaleInfo goods = saleGoodsValidator.validate(goodsId);

        UpdateWrapper<GoodsSaleInfo> wrapper = Wrappers.update();
        wrapper.set(GoodsSaleInfo.STATUS, newStatus);
        wrapper.set(GoodsSaleInfo.LAST_MODIFIED_TIME, DateUtils.getCurrentDate());
        wrapper.eq(GoodsSaleInfo.GOODS_ID, goodsId);
        //
        int currentStatus = goods.getStatus();
        if (newStatus == SaleGoodsStatusEnum.SALEABLE.getV()) { //上架
            List<Integer> properStatusList = Arrays.asList(SaleGoodsStatusEnum.UNSALEABLE.getV(), SaleGoodsStatusEnum.AVAILABLE.getV());
            Assert.isTrue(properStatusList.contains(currentStatus), "illegal operation");
            wrapper.in(GoodsSaleInfo.STATUS, properStatusList);
        }else if (newStatus == SaleGoodsStatusEnum.UNSALEABLE.getV()) {//下架
            Assert.isTrue(currentStatus == SaleGoodsStatusEnum.SALEABLE.getV(), "illegal operation");
            wrapper.eq(GoodsSaleInfo.STATUS, currentStatus);
        }
        boolean flag = this.goodsSaleInfoService.update(wrapper);
        ServiceAssert.isOk(flag, "take on or take off failed");
        if (newStatus == SaleGoodsStatusEnum.SALEABLE.getV()) { //上架
            WarehouseStock stock = warehouseStockValidator.validate(goods.getWarehouseCode(), goods.getSkuId());
            goods.setInventory(getRealNum(stock.getNum(), goods)); //TODO 后续可能会从saleableNum获取
            goods.setStatus(newStatus);//本次状态要带上
            this.saleGoodsCacheService.cache(goods);
        } else if (newStatus == SaleGoodsStatusEnum.UNSALEABLE.getV()) {//下架
           this.saleGoodsCacheService.unCache(goods);
        }
    }

    private int getRealNum(int stockNum, GoodsSaleInfo goods) {
        int unitOcuppyNum = goods.getBundles()*goods.getSaleCv();
        return stockNum/unitOcuppyNum;
    }



    @Transactional(readOnly = true)
    public GoodsSaleInfo doGet(String goodsId) {
        GoodsSaleInfo goods = saleGoodsValidator.validate(goodsId);
        return goods;
    }


    @Transactional(rollbackFor = Exception.class)
    public void doUpdate(GoodsSaleInfo t) {
        GoodsSaleInfo goods = saleGoodsValidator.validate(t.getGoodsId());
        Assert.isTrue(goods.getStatus() != SaleGoodsStatusEnum.SALEABLE.getV(), "上架的商品不能进行更新");

        List<Integer> properStatusList = Arrays.asList(SaleGoodsStatusEnum.UNSALEABLE.getV(), SaleGoodsStatusEnum.AVAILABLE.getV());
        QueryWrapper<GoodsSaleInfo> wrapper = Wrappers.query();
        wrapper.eq(GoodsSaleInfo.GOODS_ID, t.getGoodsId());
        wrapper.in(GoodsSaleInfo.STATUS, properStatusList);
        t.setGoodsId(null);
        boolean flag = goodsSaleInfoService.update(t, wrapper);
        ServiceAssert.isOk(flag, "edit saleGoods failed");
    }

    @Transactional(rollbackFor = Exception.class)
    public void doDel(String goodsId) {
        GoodsSaleInfo goods = saleGoodsValidator.validate(goodsId);
        Assert.isTrue(goods.getStatus() != SaleGoodsStatusEnum.SALEABLE.getV(), "上架的商品不能进行废弃");

        List<Integer> properStatusList = Arrays.asList(SaleGoodsStatusEnum.UNSALEABLE.getV(), SaleGoodsStatusEnum.AVAILABLE.getV());
        UpdateWrapper<GoodsSaleInfo> wrapper = Wrappers.update();

        wrapper.set(GoodsSaleInfo.STATUS, SaleGoodsStatusEnum.INVALID.getV());
        wrapper.set(GoodsSaleInfo.LAST_MODIFIED_TIME, DateUtils.getCurrentDate());
        wrapper.eq(GoodsSaleInfo.GOODS_ID, goodsId);
        wrapper.in(GoodsSaleInfo.STATUS, properStatusList);
        boolean flag = goodsSaleInfoService.update(wrapper);
        ServiceAssert.isOk(flag, "del saleGoods failed");
    }


    @Transactional(readOnly = true)
    public IPage<GoodsSaleInfo> page(String warehouseCode, String name, Integer fCategoryId, Integer sCategoryId, Integer status, IPage<GoodsSaleInfo> iPage) {
        QueryWrapper<GoodsSaleInfo> wrapper = Wrappers.query();
        if (!StringUtils.isEmpty(warehouseCode)) {
            wrapper.eq(GoodsSaleInfo.WAREHOUSE_CODE, warehouseCode);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(GoodsSaleInfo.GOODS_NAME, name);
        }
        if (fCategoryId != null) {
            wrapper.eq(GoodsSaleInfo.F_CATEGORY_ID, fCategoryId);
        }
        if (sCategoryId != null) {
            wrapper.eq(GoodsSaleInfo.S_CATEGORY_ID, sCategoryId);
        }
        if (status != null) {
            wrapper.eq(GoodsSaleInfo.STATUS, status);
        }
        return this.goodsSaleInfoService.page(iPage, wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void doAdd(List<GoodsSalePic> pics) {
        goodsSalePicService.saveBatch(pics);
        //cache
        String goodsId = pics.get(0).getGoodsId();
        saleGoodsPicCacheService.cache(goodsId, pics);
    }

    @Transactional(rollbackFor = Exception.class)
    public void doUpdate(String goodsId, List<GoodsSalePic> pics) {
        QueryWrapper<GoodsSalePic> wrapper = Wrappers.query();
        wrapper.eq(GoodsSalePic.GOODS_ID, goodsId);
        goodsSalePicService.remove(wrapper);
        goodsSalePicService.saveBatch(pics);
        //upt cache
        saleGoodsPicCacheService.reCache(goodsId, pics);
    }

    public List<GoodsSalePic> findPic(String goodsId) {
        QueryWrapper<GoodsSalePic> wrapper = Wrappers.query();
        wrapper.eq(GoodsSalePic.GOODS_ID, goodsId);
        return this.goodsSalePicService.list(wrapper);
    }
}

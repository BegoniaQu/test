package com.yc.fresh.busi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.fresh.busi.cache.SaleGoodsCacheService;
import com.yc.fresh.busi.cache.SaleGoodsPicCacheService;
import com.yc.fresh.busi.cache.WarehouseCacheService;
import com.yc.fresh.busi.validator.SaleGoodsValidator;
import com.yc.fresh.busi.validator.WarehouseStockValidator;
import com.yc.fresh.common.ServiceAssert;
import com.yc.fresh.common.exception.SCTargetExistsRuntimeException;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.*;
import com.yc.fresh.service.entity.*;
import com.yc.fresh.service.enums.SaleGoodsStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by quy on 2019/12/2.
 * Motto: you can do it
 */
@Slf4j
@Component
public class SaleGoodsManager {

    private final IGoodsSaleInfoService goodsSaleInfoService;
    private final IGoodsSalePicService goodsSalePicService;
    private final IWarehouseStockService warehouseStockService;
    private final SaleGoodsValidator saleGoodsValidator;
    private final SaleGoodsCacheService saleGoodsCacheService;
    private final SaleGoodsPicCacheService saleGoodsPicCacheService;
    private final ISkuInfoService skuInfoService;
    private final WarehouseCacheService warehouseCacheService;

    @Autowired
    public SaleGoodsManager(IGoodsSaleInfoService goodsSaleInfoService, IGoodsSalePicService goodsSalePicService, IWarehouseStockService warehouseStockService, SaleGoodsValidator saleGoodsValidator, SaleGoodsCacheService saleGoodsCacheService, SaleGoodsPicCacheService saleGoodsPicCacheService, ISkuInfoService skuInfoService, WarehouseCacheService warehouseCacheService) {
        this.goodsSaleInfoService = goodsSaleInfoService;
        this.goodsSalePicService = goodsSalePicService;
        this.warehouseStockService = warehouseStockService;
        this.saleGoodsValidator = saleGoodsValidator;
        this.saleGoodsCacheService = saleGoodsCacheService;
        this.saleGoodsPicCacheService = saleGoodsPicCacheService;
        this.skuInfoService = skuInfoService;
        this.warehouseCacheService = warehouseCacheService;
    }

    private ExecutorService asyncExecutor;

    @PostConstruct
    public void init() {
        asyncExecutor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("com.yc.fresh.busi.saleGd.notifier");
            return t;
        });
    }

    @PreDestroy
    public void close() {
        asyncExecutor.shutdown();
        try {
            asyncExecutor.awaitTermination(3, TimeUnit.SECONDS); //等待提交的task完成
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }


    public List<GoodsSaleInfo> findBySkuId(Long skuId) {
        QueryWrapper<GoodsSaleInfo> wrapper = Wrappers.query();
        wrapper.in(GoodsSaleInfo.SKU_ID, skuId);
        return this.goodsSaleInfoService.list(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void doAdd(GoodsSaleInfo t) {
        //WarehouseStock ws = warehouseStockValidator.validate(t.getWarehouseCode(), t.getSkuId());
        checkDuplicate(t);
        SkuInfo sku = skuInfoService.getById(t.getSkuId());
        t.setFCategoryId(sku.getFCategoryId());
        t.setSCategoryId(sku.getSCategoryId());
        t.setUnit(sku.getUnit());
        boolean flag = goodsSaleInfoService.save(t);
        ServiceAssert.isOk(flag, "add saleGoods failed");
        //
        skuInfoService.updateStatusToUsed(sku);
    }

    private void checkDuplicate(GoodsSaleInfo t) {
        List<GoodsSaleInfo> dbList = findBySkuId(t.getSkuId());
        for (GoodsSaleInfo one : dbList) {
            if (one.getStatus() == SaleGoodsStatusEnum.INVALID.getV()) {
                continue;
            }
            if (one.compose().equals(t.compose())) {
                throw new SCTargetExistsRuntimeException("重复添加");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void doTakeOnOrOff(String goodsId, Integer newStatus) {
        Assert.isTrue(newStatus == SaleGoodsStatusEnum.SALEABLE.getV() ||
                newStatus == SaleGoodsStatusEnum.UNSALEABLE.getV(), "illegal request");
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
            //检验仓库商品
            List<WarehouseStock> warehouseStocks = warehouseStockService.findBySkuId(goods.getSkuId());
            Assert.notEmpty(warehouseStocks, String.format("sku: %s 请先入仓", goods.getSkuId()));
            //
            wrapper.in(GoodsSaleInfo.STATUS, properStatusList);
        }else { //下架
            Assert.isTrue(currentStatus == SaleGoodsStatusEnum.SALEABLE.getV(), "illegal operation");
            wrapper.eq(GoodsSaleInfo.STATUS, currentStatus);
        }
        ServiceAssert.isOk(this.goodsSaleInfoService.update(wrapper), "take on or take off failed");

        if (newStatus == SaleGoodsStatusEnum.SALEABLE.getV()) {  //上架
            goods.setStatus(newStatus);//本次状态要带上
            this.saleGoodsCacheService.cache(goods);
        } else { //下架
            this.saleGoodsCacheService.unCache(goods);
        }
        //做一些其他处理
        asyncExecutor.submit(new SaleGdOnOrOffEvent(goods, newStatus));
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
        checkDuplicate(t);
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
    public IPage<GoodsSaleInfo> page(String name, Integer fCategoryId, Integer sCategoryId, Integer status, IPage<GoodsSaleInfo> iPage) {
        QueryWrapper<GoodsSaleInfo> wrapper = Wrappers.query();
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


    private int getRealNum(int stockNum, GoodsSaleInfo goods) {
        int unitOcuppyNum = goods.getBundles()*goods.getSaleCv();
        return stockNum/unitOcuppyNum;
    }



    public class SaleGdOnOrOffEvent implements Runnable{

        private GoodsSaleInfo goods;
        private int action;

        public SaleGdOnOrOffEvent(GoodsSaleInfo saleInfo, int action) {
            this.goods = saleInfo;
            this.action = action;
        }

        @Override
        public void run() {
            List<WarehouseStock> warehouseStocks = warehouseStockService.findBySkuId(goods.getSkuId());
            if (action == SaleGoodsStatusEnum.SALEABLE.getV()) {
                for (WarehouseStock warehouseStock : warehouseStocks) {
                    saleGoodsCacheService.addMapping(warehouseStock.getWarehouseCode(), goods.getFCategoryId(), goods.getGoodsId());
                    saleGoodsCacheService.addNameBasedSearch(warehouseStock.getWarehouseCode(), goods.getGoodsName(), goods.getGoodsId());
                }
            } else {
                List<Warehouse> warehouses = warehouseCacheService.findValidWarehouse();
                for (Warehouse warehouse : warehouses) {
                    saleGoodsCacheService.unMapping(warehouse.getCode(), goods.getFCategoryId(), goods.getGoodsId());
                    saleGoodsCacheService.removeNameBasedSearch(warehouse.getCode(), goods.getGoodsName());
                }
            }
        }
    }
}


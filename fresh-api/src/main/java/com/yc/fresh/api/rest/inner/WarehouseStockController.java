package com.yc.fresh.api.rest.inner;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.fresh.api.builder.LockNameBuilder;
import com.yc.fresh.api.rest.inner.convertor.WarehouseStockConvertor;
import com.yc.fresh.api.rest.inner.req.bean.*;
import com.yc.fresh.api.rest.inner.resp.bean.StockPageRespBean;
import com.yc.fresh.busi.GdCategoryManager;
import com.yc.fresh.busi.WarehouseManager;
import com.yc.fresh.busi.WarehouseStockManager;
import com.yc.fresh.common.PageResult;
import com.yc.fresh.common.cache.lock.impl.LockProxy;
import com.yc.fresh.common.lock.DistributedLock;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.entity.Warehouse;
import com.yc.fresh.service.entity.WarehouseStock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2019/11/26.
 * Motto: you can do it
 * 库存管理目前只是最简单的，后续待完善
 */
@RestController
@RequestMapping("/rest/inner/stock")
@Api(description = "仓库库存管理")
public class WarehouseStockController {

    private final WarehouseStockManager warehouseStockManager;
    private final DistributedLock<LockProxy> distributedLock;
    private final GdCategoryManager gdCategoryManager;
    private final WarehouseManager warehouseManager;

    @Autowired
    public WarehouseStockController(WarehouseStockManager warehouseStockManager, DistributedLock<LockProxy> distributedLock, GdCategoryManager gdCategoryManager, WarehouseManager warehouseManager) {
        this.warehouseStockManager = warehouseStockManager;
        this.distributedLock = distributedLock;
        this.gdCategoryManager = gdCategoryManager;
        this.warehouseManager = warehouseManager;
    }


    @PostMapping(value = "/sku/add")
    @ApiOperation(value="新商品入库", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void add(@Valid @RequestBody StockSkuAddReqBean reqBean) {
        String lockName = LockNameBuilder.buildSku(reqBean.getSkuId());
        LockProxy lock = distributedLock.lock(lockName);
        Assert.notNull(lock, "资源占用中, 请稍后重试");
        WarehouseStock warehouseStock = WarehouseStockConvertor.convert2Entity(reqBean);
        warehouseStockManager.doAdd(warehouseStock);
        lock.release();
    }

    /**
     *此处的库中更新后续应该只用于特殊情况的价格和数量的变动
     * @param reqBean
     */
    @PostMapping(value = "/sku/part/edit")
    @ApiOperation(value="库存更新", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void updateStock(@Valid @RequestBody StockNumUptReqBean reqBean) {
        this.warehouseStockManager.updateStockNum(reqBean.getWarehouseCode(), reqBean.getSkuId(), reqBean.getNum(), reqBean.getCostPrice());
    }

    @PostMapping(value = "/sku/del")
    @ApiOperation(value="删除", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void del(@Valid @RequestBody StockDelReqBean reqBean) {
        //加锁 warehouseCode + skuId  还有就是在添加goodsSaleInfo时，也要加一样的锁 不然无法保证废弃时没有同时添加goodsSaleInfo
        String lockName = LockNameBuilder.buildStock(reqBean.getWarehouseCode(), String.valueOf(reqBean.getSkuId()));
        LockProxy lock = distributedLock.lock(lockName);
        Assert.notNull(lock, "资源占用中, 请稍后重试");
        this.warehouseStockManager.doDel(reqBean.getWarehouseCode(), reqBean.getSkuId());
        lock.release();
    }


    @GetMapping("/list")
    @ApiOperation(value="库存列表", produces=APPLICATION_JSON_VALUE, response = StockPageRespBean.class, httpMethod = "GET")
    public PageResult<StockPageRespBean> list(StockPageQryBean qryBean) {
        IPage<WarehouseStock> iPage = new Page<>(qryBean.getPn(), qryBean.getPs());
        IPage<WarehouseStock> page = this.warehouseStockManager.page(qryBean.getWarehouseCode(), qryBean.getSkuName(), qryBean.getFirstCategoryId(), qryBean.getSecondCategoryId(), iPage);
        //分类
        List<GdCategory> fCategories = gdCategoryManager.query(0, null); //查一级分类
        List<Integer> parentIds = fCategories.stream().map(t->t.getId()).collect(Collectors.toList());
        Map<Integer, List<GdCategory>> subMap = gdCategoryManager.querySubs(parentIds);
        //仓库
        List<Warehouse> warehouses = warehouseManager.query(null);
        return WarehouseStockConvertor.convert2PageBean(page, fCategories, subMap, warehouses);
    }

}

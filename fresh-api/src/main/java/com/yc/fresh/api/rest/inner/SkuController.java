package com.yc.fresh.api.rest.inner;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.fresh.api.rest.inner.builder.LockNameBuilder;
import com.yc.fresh.api.rest.inner.convertor.SkuConvertor;
import com.yc.fresh.api.rest.inner.req.bean.SkuAddReqBean;
import com.yc.fresh.api.rest.inner.req.bean.SkuEditReqBean;
import com.yc.fresh.api.rest.inner.req.bean.SkuPageQryBean;
import com.yc.fresh.api.rest.inner.resp.bean.GoodsPageRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.SkuDetailRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.SkuPageRespBean;
import com.yc.fresh.busi.GdCategoryManager;
import com.yc.fresh.busi.SkuManager;
import com.yc.fresh.common.PageResult;
import com.yc.fresh.common.cache.lock.impl.LockProxy;
import com.yc.fresh.common.lock.DistributedLock;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.SkuInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2019/11/27.
 * Motto: you can do it
 */

@RestController
@RequestMapping("/rest/inner/sku")
@Api(description = "sku管理")
public class SkuController {

    private final SkuManager skuManager;
    private final GdCategoryManager gdCategoryManager;
    private final DistributedLock<LockProxy> distributedLock;


    @Autowired
    public SkuController(SkuManager skuManager, GdCategoryManager gdCategoryManager, DistributedLock<LockProxy> distributedLock) {
        this.skuManager = skuManager;
        this.gdCategoryManager = gdCategoryManager;
        this.distributedLock = distributedLock;
    }

    @PostMapping(value = "/add")
    @ApiOperation(value="增加SKU", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void add(@Valid @RequestBody SkuAddReqBean reqBean) {
        SkuInfo skuInfo = SkuConvertor.convert2Entity(reqBean);
        skuManager.doAdd(skuInfo);
    }

    @GetMapping("/{skuId}/detail")
    @ApiOperation(value="sku信息查看", produces=APPLICATION_JSON_VALUE, response = SkuDetailRespBean.class, httpMethod = "GET")
    public SkuDetailRespBean getById(@ApiParam("skuID") @NotNull @PathVariable Long skuId) {
        SkuInfo sku = skuManager.doGet(skuId);
        return SkuConvertor.convert2Bean(sku);
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value="编辑SKU", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void edit(@Valid @RequestBody SkuEditReqBean reqBean) {
        String lockName = LockNameBuilder.buildSku(reqBean.getSkuId());
        LockProxy lock = distributedLock.lock(lockName);
        Assert.notNull(lock, "资源占用中, 请稍后重试");
        SkuInfo skuInfo = SkuConvertor.convert2Entity(reqBean);
        skuManager.doUpdate(skuInfo);
        lock.release();
    }


    @GetMapping("/list")
    @ApiOperation(value="sku列表", produces=APPLICATION_JSON_VALUE, response = SkuPageRespBean.class, httpMethod = "GET")
    public PageResult<SkuPageRespBean> list(SkuPageQryBean qryBean) {
        IPage<SkuInfo> iPage = new Page<>(qryBean.getPn(), qryBean.getPs());
        IPage<SkuInfo> page = skuManager.page(qryBean.getSkuName(), qryBean.getFirstCategoryId(), qryBean.getSecondCategoryId(), iPage);
        //分类
        List<GdCategory> fCategories = gdCategoryManager.query(0, null); //查一级分类
        List<Integer> parentIds = fCategories.stream().map(t->t.getId()).collect(Collectors.toList());
        Map<Integer, List<GdCategory>> subMap = gdCategoryManager.querySubs(parentIds);
        return SkuConvertor.convert2PageBean(page,fCategories, subMap);
    }

}

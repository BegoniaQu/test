package com.yc.fresh.api.rest.inner;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.fresh.api.builder.LockNameBuilder;
import com.yc.fresh.api.rest.inner.convertor.SaleGoodsConvertor;
import com.yc.fresh.api.rest.inner.req.bean.*;
import com.yc.fresh.api.rest.inner.resp.bean.GoodsDetailRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.GoodsPageRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.GoodsPicRespBean;
import com.yc.fresh.busi.GdCategoryManager;
import com.yc.fresh.busi.SaleGoodsManager;
import com.yc.fresh.busi.id.IdGenerator;
import com.yc.fresh.common.PageResult;
import com.yc.fresh.common.cache.lock.impl.LockProxy;
import com.yc.fresh.common.lock.DistributedLock;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.GoodsSalePic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2019/11/18.
 * Motto: you can do it
 */
@RestController
@RequestMapping("/rest/inner/goods")
@Api(tags = "Admin-售卖商品管理")
public class GoodsSaleController {

    private final SaleGoodsManager saleGoodsManager;
    private final DistributedLock<LockProxy> distributedLock;
    private final IdGenerator idGenerator;
    private final GdCategoryManager gdCategoryManager;

    @Autowired
    public GoodsSaleController(SaleGoodsManager saleGoodsManager, DistributedLock<LockProxy> distributedLock, IdGenerator idGenerator, GdCategoryManager gdCategoryManager) {
        this.saleGoodsManager = saleGoodsManager;
        this.distributedLock = distributedLock;
        this.idGenerator = idGenerator;
        this.gdCategoryManager = gdCategoryManager;
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "增加售卖商品", produces = APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void add(@Valid @RequestBody GoodsAddReqBean reqBean) {
        String lockName = LockNameBuilder.buildSku(reqBean.getSkuId());
        LockProxy lock = distributedLock.lock(lockName);
        Assert.notNull(lock, "资源占用中, 请稍后重试");
        GoodsSaleInfo goodsSaleInfo = SaleGoodsConvertor.convert2Entity(reqBean);
        String goodsId = idGenerator.generate();
        goodsSaleInfo.setGoodsId(goodsId);
        saleGoodsManager.doAdd(goodsSaleInfo);
        lock.release();
    }

    @PostMapping(value = "/switch")
    @ApiOperation(value = "上下架", produces = APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void takeOnOrOff(@Valid @RequestBody GoodsOnOrOffReqBean reqBean) {
        saleGoodsManager.doTakeOnOrOff(reqBean.getGoodsId(), reqBean.getAction());
    }

    @PostMapping(value = "/discard")
    @ApiOperation(value = "废弃", produces = APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void del(@Valid @RequestBody GoodsDelReqBean reqBean) {
        saleGoodsManager.doDel(reqBean.getGoodsId());
    }


    @GetMapping("/{goodsId}/detail")
    @ApiOperation(value="商品信息查看", produces=APPLICATION_JSON_VALUE, response = GoodsDetailRespBean.class, httpMethod = "GET")
    public GoodsDetailRespBean getById(@ApiParam("商品ID") @NotBlank @PathVariable String goodsId) {
        GoodsSaleInfo goods = this.saleGoodsManager.doGet(goodsId);
        //分类
        GdCategory par = gdCategoryManager.getById(goods.getFCategoryId());
        GdCategory sub = gdCategoryManager.getById(goods.getSCategoryId());
        //仓库
        //Warehouse warehouse = warehouseManager.getByCode(goods.getWarehouseCode());
        return SaleGoodsConvertor.convert2DetailBean(goods, par, sub);
    }


    @PostMapping(value = "/edit")
    @ApiOperation(value="编辑售卖商品", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void edit(@Valid @RequestBody GoodsEditReqBean reqBean) {
        GoodsSaleInfo goodsSaleInfo = SaleGoodsConvertor.convert2Entity(reqBean);
        GoodsSaleInfo dbOne = this.saleGoodsManager.doGet(reqBean.getGoodsId());
        Assert.notNull(dbOne, "invalid request");
        /*String lockName = LockNameBuilder.buildSku(dbOne.getSkuId());
        LockProxy lock = distributedLock.lock(lockName);
        Assert.notNull(lock, "资源占用中, 请稍后重试");*/
        saleGoodsManager.doUpdate(goodsSaleInfo);
    }


    @GetMapping("/list")
    @ApiOperation(value="售卖商品列表", produces=APPLICATION_JSON_VALUE, response = GoodsPageRespBean.class, httpMethod = "GET")
    public PageResult<GoodsPageRespBean> list(GoodsPageQryBean qryBean) {
        IPage<GoodsSaleInfo> iPage = new Page<>(qryBean.getPn(), qryBean.getPs());
        IPage<GoodsSaleInfo> page = this.saleGoodsManager.page(qryBean.getGoodsName(), qryBean.getFirstCategoryId(), qryBean.getSecondCategoryId(), qryBean.getStatus(), iPage);
        //分类
        List<GdCategory> fCategories = gdCategoryManager.query(0, null); //查一级分类
        List<Integer> parentIds = fCategories.stream().map(t->t.getId()).collect(Collectors.toList());
        Map<Integer, List<GdCategory>> subMap = gdCategoryManager.querySubs(parentIds);
        //仓库
        //List<Warehouse> warehouses = warehouseManager.query(null);
        return SaleGoodsConvertor.convert2PageBean(page, fCategories, subMap);
    }


    @PostMapping(value = "/pic/add")
    @ApiOperation(value = "增加图片", produces = APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void addPicture(@Valid @RequestBody GoodsPicAddOrEditReqBean reqBean) {
        List<GoodsSalePic> pics = SaleGoodsConvertor.convert2EntityList(reqBean);
        saleGoodsManager.doAdd(pics);
    }

    @PostMapping(value = "/pic/edit")
    @ApiOperation(value = "更新图片", produces = APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void updatePicture(@Valid @RequestBody GoodsPicAddOrEditReqBean reqBean) {
        List<GoodsSalePic> pics = SaleGoodsConvertor.convert2EntityList(reqBean);
        this.saleGoodsManager.doUpdate(reqBean.getGoodsId(), pics);
    }

    @GetMapping(value = "/{goodsId}/pic")
    @ApiOperation(value = "查看商品图片", produces = APPLICATION_JSON_VALUE, httpMethod = "GET")
    public GoodsPicRespBean queryGoodsPic(@PathVariable @NotBlank String goodsId) {
        List<GoodsSalePic> pics = this.saleGoodsManager.findPic(goodsId);
        return SaleGoodsConvertor.convert2Bean(goodsId, pics);
    }
}

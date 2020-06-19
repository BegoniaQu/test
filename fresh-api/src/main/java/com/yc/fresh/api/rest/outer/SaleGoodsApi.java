package com.yc.fresh.api.rest.outer;

import com.google.common.collect.Lists;
import com.yc.fresh.api.rest.outer.convertor.SaleGoodsConvertor;
import com.yc.fresh.api.rest.outer.req.bean.SaleGoodsPageQryBean;
import com.yc.fresh.api.rest.outer.resp.bean.SaleGdBriefRespVO;
import com.yc.fresh.api.rest.outer.resp.bean.SaleGdDetailVO;
import com.yc.fresh.api.rest.outer.resp.bean.SaleGdSearchRespBean;
import com.yc.fresh.busi.outer.GdCategoryQryManager;
import com.yc.fresh.busi.outer.InventoryManger;
import com.yc.fresh.busi.outer.SaleGoodsQryManager;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.GoodsSalePic;
import com.yc.fresh.service.enums.SaleGoodsStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */

@Api(tags = "Open-商品")
@RestController
@RequestMapping("rest/outer/n/gd")
public class SaleGoodsApi {

    private final SaleGoodsQryManager saleGoodsQryManager;
    private final GdCategoryQryManager gdCategoryQryManager;
    private final InventoryManger inventoryManger;

    public SaleGoodsApi(SaleGoodsQryManager saleGoodsQryManager, GdCategoryQryManager gdCategoryQryManager, InventoryManger inventoryManger) {
        this.saleGoodsQryManager = saleGoodsQryManager;
        this.gdCategoryQryManager = gdCategoryQryManager;
        this.inventoryManger = inventoryManger;
    }

    @GetMapping("/search")
    @ApiOperation(value="商品搜索", produces=APPLICATION_JSON_VALUE, responseContainer = "List", response = SaleGdSearchRespBean.class, httpMethod = "GET")
    public List<SaleGdSearchRespBean> search(@ApiParam("名称搜索") @RequestParam String name,
                                             @ApiParam("仓库") @RequestParam String code) {
        List<GoodsSaleInfo> goodsSaleInfos = saleGoodsQryManager.doSearch(name, code);
        //查库存
        Set<Long> skuIdSet = goodsSaleInfos.stream().map(t -> t.getSkuId()).collect(Collectors.toSet());
        Map<Long, Integer> inventoryMap = inventoryManger.findInventory(code, Lists.newArrayList(skuIdSet.toArray(new Long[0])));
        return SaleGoodsConvertor.convert(goodsSaleInfos, inventoryMap);
    }


    @GetMapping("/list")
    @ApiOperation(value="商品列表", produces=APPLICATION_JSON_VALUE, response = SaleGdBriefRespVO.class, httpMethod = "GET")
    public SaleGdBriefRespVO findSaleGd(@Valid SaleGoodsPageQryBean qryBean) {
        List<GoodsSaleInfo> saleGoods = this.saleGoodsQryManager.findSaleGoods(qryBean.getWsCode(), qryBean.getFcId());
        if (CollectionUtils.isEmpty(saleGoods)) {
            SaleGdBriefRespVO vo = new SaleGdBriefRespVO();
            vo.setScMap(Collections.EMPTY_MAP);
            vo.setGdMap(Collections.EMPTY_MAP);
            return vo;
        }
        //查库存
        Set<Long> skuIdSet = saleGoods.stream().map(t -> t.getSkuId()).collect(Collectors.toSet());
        Map<Long, Integer> inventoryMap = inventoryManger.findInventory(qryBean.getWsCode(), Lists.newArrayList(skuIdSet.toArray(new Long[0])));
        List<GdCategory> sCategories = gdCategoryQryManager.findValidCategory(qryBean.getFcId());
        return SaleGoodsConvertor.convert(saleGoods, sCategories, inventoryMap);
    }

    @GetMapping("/{id}/info")
    @ApiOperation(value="商品详情", produces=APPLICATION_JSON_VALUE, response = SaleGdDetailVO.class, httpMethod = "GET")
    public SaleGdDetailVO getSaleGd(@ApiParam(value = "商品ID", required = true) @PathVariable("id") @NotBlank String goodsId,
                                    @ApiParam(value = "仓库编码", required = true) @RequestParam @NotBlank String wsCode) {
        GoodsSaleInfo gd = saleGoodsQryManager.getByGoodsId(goodsId); //详情不用校验商品状态
        Assert.notNull(gd, "illegal request");
        List<GoodsSalePic> pics = saleGoodsQryManager.findSaleGdPics(goodsId);
        int inventory = 0;
        if (gd.getStatus() == SaleGoodsStatusEnum.SALEABLE.getV()) { //售卖中的
            inventory = this.inventoryManger.getInventory(wsCode, gd.getSkuId());
        }
        return SaleGoodsConvertor.convert(gd, inventory, pics);
    }

}

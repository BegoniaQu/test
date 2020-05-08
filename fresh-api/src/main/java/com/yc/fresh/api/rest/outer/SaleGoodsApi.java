package com.yc.fresh.api.rest.outer;

import com.yc.fresh.api.rest.outer.convertor.SaleGoodsConvertor;
import com.yc.fresh.api.rest.outer.req.bean.SaleGoodsPageQryBean;
import com.yc.fresh.api.rest.outer.resp.bean.SaleGdBriefRespVO;
import com.yc.fresh.api.rest.outer.resp.bean.SaleGdDetailVO;
import com.yc.fresh.busi.cache.GdCategoryCacheService;
import com.yc.fresh.busi.outer.SaleGoodsQryManager;
import com.yc.fresh.common.PageResult;
import com.yc.fresh.common.exception.SCApiRuntimeException;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.GoodsSalePic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final GdCategoryCacheService gdCategoryCacheService;

    public SaleGoodsApi(SaleGoodsQryManager saleGoodsQryManager, GdCategoryCacheService gdCategoryCacheService) {
        this.saleGoodsQryManager = saleGoodsQryManager;
        this.gdCategoryCacheService = gdCategoryCacheService;
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
        List<GdCategory> sCategories = gdCategoryCacheService.findCategory(qryBean.getFcId());
        return SaleGoodsConvertor.convert(saleGoods, sCategories);
    }

    @GetMapping("/{id}/info")
    @ApiOperation(value="商品详情", produces=APPLICATION_JSON_VALUE, response = SaleGdDetailVO.class, httpMethod = "GET")
    public SaleGdDetailVO getSaleGd(@ApiParam(value = "商品ID", required = true) @PathVariable("id") @NotBlank String goodsId) {
        GoodsSaleInfo gd = saleGoodsQryManager.getByGoodsId(goodsId);
        List<GoodsSalePic> pics = saleGoodsQryManager.findSaleGdPics(goodsId);
        return SaleGoodsConvertor.convert(gd, pics);
    }

}

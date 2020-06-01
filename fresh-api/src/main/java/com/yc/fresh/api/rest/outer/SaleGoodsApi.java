package com.yc.fresh.api.rest.outer;

import com.yc.fresh.api.rest.outer.convertor.SaleGoodsConvertor;
import com.yc.fresh.api.rest.outer.req.bean.SaleGoodsPageQryBean;
import com.yc.fresh.api.rest.outer.resp.bean.SaleGdBriefRespVO;
import com.yc.fresh.api.rest.outer.resp.bean.SaleGdDetailVO;
import com.yc.fresh.api.rest.outer.resp.bean.SaleGdSearchRespBean;
import com.yc.fresh.busi.outer.GdCategoryQryManager;
import com.yc.fresh.busi.outer.SaleGoodsQryManager;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.GoodsSalePic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.*;

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

    public SaleGoodsApi(SaleGoodsQryManager saleGoodsQryManager, GdCategoryQryManager gdCategoryQryManager) {
        this.saleGoodsQryManager = saleGoodsQryManager;
        this.gdCategoryQryManager = gdCategoryQryManager;
    }

    @GetMapping("/search")
    @ApiOperation(value="商品搜索", produces=APPLICATION_JSON_VALUE, responseContainer = "List", response = SaleGdSearchRespBean.class, httpMethod = "GET")
    public List<SaleGdSearchRespBean> search(@ApiParam("名称搜索") @RequestParam String name,
                                             @ApiParam("仓库") @RequestParam String code) {
        List<GoodsSaleInfo> goodsSaleInfos = saleGoodsQryManager.doSearch(name, code);
        return SaleGoodsConvertor.convert(goodsSaleInfos);
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
        List<GdCategory> sCategories = gdCategoryQryManager.findValidCategory(qryBean.getFcId());
        return SaleGoodsConvertor.convert(saleGoods, sCategories);
    }

    @GetMapping("/{id}/info")
    @ApiOperation(value="商品详情", produces=APPLICATION_JSON_VALUE, response = SaleGdDetailVO.class, httpMethod = "GET")
    public SaleGdDetailVO getSaleGd(@ApiParam(value = "商品ID", required = true) @PathVariable("id") @NotBlank String goodsId) {
        GoodsSaleInfo gd = saleGoodsQryManager.getByGoodsId(goodsId); //详情不用校验商品状态
        Assert.notNull(gd, "illegal request");
        List<GoodsSalePic> pics = saleGoodsQryManager.findSaleGdPics(goodsId);
        return SaleGoodsConvertor.convert(gd, pics);
    }

}

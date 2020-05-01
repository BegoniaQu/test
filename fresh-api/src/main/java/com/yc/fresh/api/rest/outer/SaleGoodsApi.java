package com.yc.fresh.api.rest.outer;

import com.yc.fresh.api.rest.outer.req.bean.SaleGoodsPageQryBean;
import com.yc.fresh.api.rest.outer.resp.bean.SaleGdBriefRespVO;
import com.yc.fresh.busi.outer.SaleGoodsQryManager;
import com.yc.fresh.common.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */

@Api(description = "售卖商品")
@RestController
@RequestMapping("rest/outer/n/gd")
public class SaleGoodsApi {

    private final SaleGoodsQryManager saleGoodsQryManager;

    public SaleGoodsApi(SaleGoodsQryManager saleGoodsQryManager) {
        this.saleGoodsQryManager = saleGoodsQryManager;
    }

    @GetMapping("/list")
    @ApiOperation(value="商品列表", produces=APPLICATION_JSON_VALUE, response = SaleGdBriefRespVO.class, httpMethod = "GET")
    public PageResult<SaleGdBriefRespVO> findSaleGdList(@Valid SaleGoodsPageQryBean qryBean) {




        return null;
    }

}

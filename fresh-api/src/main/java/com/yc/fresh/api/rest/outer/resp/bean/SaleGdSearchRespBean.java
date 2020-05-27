package com.yc.fresh.api.rest.outer.resp.bean;

import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */
@Setter
@Getter
@RespData
@ApiModel(description = "搜索到的信息")
public class SaleGdSearchRespBean {

    @ApiModelProperty("商品ID")
    private String goodsId;
    @ApiModelProperty("商品名称")
    private String goodsName;
    @ApiModelProperty("主图")
    private String mPicPath;
    @ApiModelProperty("原价")
    private BigDecimal rawPrice;
    @ApiModelProperty("现价")
    private BigDecimal salePrice;
}


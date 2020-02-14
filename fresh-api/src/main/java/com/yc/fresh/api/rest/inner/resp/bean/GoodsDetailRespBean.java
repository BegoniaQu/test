package com.yc.fresh.api.rest.inner.resp.bean;

import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by quy on 2019/12/12.
 * Motto: you can do it
 */
@Getter
@Setter
@RespData
@ApiModel(description = "商品信息")
public class GoodsDetailRespBean {

    @ApiModelProperty("商品ID")
    private String goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("仓库")
    private String warehouseName;

    @ApiModelProperty("一级分类")
    private String fCategory;

    @ApiModelProperty("二级分类")
    private String sCategory;

    @ApiModelProperty("主图")
    private String mPicPath;

    @ApiModelProperty("原价")
    private BigDecimal rawPrice;

    @ApiModelProperty("现价")
    private BigDecimal salePrice;

    @ApiModelProperty("捆绑数量")
    private Integer bundles;

    @ApiModelProperty("计量值")
    private Integer saleCv;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("详细文案")
    private String descrPath;
}

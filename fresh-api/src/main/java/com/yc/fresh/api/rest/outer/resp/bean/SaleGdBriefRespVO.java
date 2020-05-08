package com.yc.fresh.api.rest.outer.resp.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */
@Setter
@Getter
@RespData
@ApiModel(description = "商品列表信息")
public class SaleGdBriefRespVO {
    private Map<Integer, String> scMap;
    private Map<Integer, List<SaleGd>> gdMap;

    @Setter
    @Getter
    @ApiModel(description = "售卖商品信息")
    public static class SaleGd {
        @JsonIgnore
        private Integer scId;
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
        @ApiModelProperty("描述")
        private String description;
    }
}


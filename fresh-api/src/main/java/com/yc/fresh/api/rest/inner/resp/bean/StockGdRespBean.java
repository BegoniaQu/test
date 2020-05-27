package com.yc.fresh.api.rest.inner.resp.bean;

import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by quy on 2019/12/10.
 * Motto: you can do it
 */
@Getter
@Setter
@RespData
@ApiModel(description = "库存商品信息(添加售卖商品用)")
public class StockGdRespBean {


    @ApiModelProperty("货物ID")
    private Long skuId;

    @ApiModelProperty("货物名称")
    private String skuName;

    @ApiModelProperty("单位类型")
    private Integer unitType;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("成本价格")
    private BigDecimal costPrice;



}

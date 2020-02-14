package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by quy on 2019/12/1.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "库存数量及进价更新请求")
public class StockNumUptReqBean {

    @ApiModelProperty(value = "仓库编码", required = true)
    @NotBlank
    private String warehouseCode;

    @ApiModelProperty(value = "SKU编码", required = true)
    @NotNull
    private Long skuId;

    @ApiModelProperty(value = "增加或者扣减的数量")
    private Integer num;

    @ApiModelProperty(value = "新进价")
    private BigDecimal costPrice;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank
    private String operator;
}

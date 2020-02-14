package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by quy on 2019/12/1.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "仓库商品删除请求")
public class StockDelReqBean {

    @ApiModelProperty(value = "仓库编码", required = true)
    @NotBlank
    private String warehouseCode;

    @ApiModelProperty(value = "SKU编码", required = true)
    @NotNull
    private Long skuId;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank
    private String operator;

}

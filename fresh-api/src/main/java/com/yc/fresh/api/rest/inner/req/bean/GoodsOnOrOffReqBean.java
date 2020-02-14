package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by quy on 2019/12/9.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "售卖商品上下架请求")
public class GoodsOnOrOffReqBean {

    @ApiModelProperty(value = "商品ID", required = true)
    @NotBlank
    private String goodsId;

    @ApiModelProperty(value = "上架2/下架3", required = true)
    @NotNull
    private Integer action;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank
    private String operator;
}

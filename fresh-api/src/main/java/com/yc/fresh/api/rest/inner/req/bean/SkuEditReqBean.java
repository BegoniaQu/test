package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by quy on 2019/11/27.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "sku编辑请求")
public class SkuEditReqBean {

    @ApiModelProperty(value = "skuID", required = true)
    @NotNull
    private Long skuId;

    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    private String skuName;

    @ApiModelProperty(value = "1级分类ID", required = true)
    @NotNull
    private Integer fCategoryId;

    @ApiModelProperty(value = "2级分类ID", required = true)
    @NotNull
    private Integer sCategoryId;

    @ApiModelProperty(value = "单位类型，1-包装单位 2-重量单位g", required = true)
    @NotNull
    private Integer unitType;

    @ApiModelProperty(value = "单位(如包装单位：瓶、袋、盒、g)", required = true)
    @NotBlank
    private String unit;

    @ApiModelProperty(value = "规格，如12*1.5L, 320g, 200ml")
    private String spec;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank
    private String operator;
}

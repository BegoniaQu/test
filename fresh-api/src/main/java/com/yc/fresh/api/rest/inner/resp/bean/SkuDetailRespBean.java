package com.yc.fresh.api.rest.inner.resp.bean;

import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2019/12/12.
 * Motto: you can do it
 */
@Getter
@Setter
@RespData
@ApiModel(description = "sku信息")
public class SkuDetailRespBean {

    @ApiModelProperty(value = "skuID")
    private Long skuId;
    @ApiModelProperty(value = "sku名称")
    private String skuName;
    @ApiModelProperty(value = "1级分类ID")
    private Integer fCategoryId;
    @ApiModelProperty(value = "2级分类ID")
    private Integer sCategoryId;
    @ApiModelProperty(value = "单位类型(返回的是Id,页面匹配到自己的下拉列表中)")
    private Integer unitType;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "规格")
    private String spec;

}

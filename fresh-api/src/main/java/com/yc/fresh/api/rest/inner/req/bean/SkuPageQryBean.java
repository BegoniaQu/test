package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yc.fresh.common.Page;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2019/12/16.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkuPageQryBean extends Page {

    @ApiParam("sku名称")
    private String skuName;

    @ApiParam("一级分类id")
    private Integer firstCategoryId;

    @ApiParam("二级分类id")
    private Integer secondCategoryId;

}

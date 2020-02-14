package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yc.fresh.common.Page;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2019/12/11.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoodsPageQryBean extends Page {

    @ApiParam("仓库编码")
    private String warehouseCode;

    @ApiParam("商品名称")
    private String goodsName;

    @ApiParam("一级分类id")
    private Integer firstCategoryId;

    @ApiParam("二级分类id")
    private Integer secondCategoryId;

    @ApiParam("状态[0-废弃，1-新增待上架的，2-已上架的 3-已下架的]")
    private Integer status;




}

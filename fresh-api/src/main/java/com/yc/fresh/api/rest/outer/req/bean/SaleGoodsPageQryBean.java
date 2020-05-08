package com.yc.fresh.api.rest.outer.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yc.fresh.common.Page;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleGoodsPageQryBean {

    @ApiParam(value = "仓库编码", required = true)
    @NotBlank
    private String wsCode;

    @ApiParam(value = "一级分类ID", required = true)
    @NotNull
    private Integer fcId;

}

package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created by quy on 2019/12/16.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "商品图片信息")
public class Picture {

    @ApiModelProperty(value = "地址", required = true)
    @NotBlank
    private String url;

    @ApiModelProperty(value = "排序", required = true)
    private int sort;
}

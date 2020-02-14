package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by quy on 2019/11/24.
 * Motto: you can do it
 */
@Getter
@Setter
@ApiModel(description = "废弃分类请求")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GdCategoryDelReqBean {

    @ApiModelProperty(value = "分类ID", required = true)
    @NotNull
    private Integer categoryId;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank
    private String operator;
}

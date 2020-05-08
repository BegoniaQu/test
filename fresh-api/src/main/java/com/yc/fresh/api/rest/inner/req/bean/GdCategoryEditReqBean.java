package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by quy on 2019/11/22.
 * Motto: you can do it
 */
@Getter
@Setter
@ApiModel(description = "编辑分类请求")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GdCategoryEditReqBean {

    @ApiModelProperty(value = "分类ID", required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "分类名", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "展示顺序：正整数", required = true)
    @NotNull
    private Integer sort;

   /* @ApiModelProperty(value = "所属父类ID,本身就是一级分类时请忽略此字段")
    private Integer parentId;*/ //考虑到缓存的复杂性,编辑时不能改父分类,可直接废弃分类

    @ApiModelProperty(value = "图片路径(相对路径)", required = true)
    @NotBlank
    private String picPath;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank
    private String operator;
}

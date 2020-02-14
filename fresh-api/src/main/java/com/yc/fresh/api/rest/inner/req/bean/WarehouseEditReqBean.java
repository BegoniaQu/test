package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by quy on 2019/11/25.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "编辑仓库请求")
public class WarehouseEditReqBean {

    @ApiModelProperty(value = "仓库编码", required = true)
    @NotBlank
    private String code;

    @ApiModelProperty(value = "仓库名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "所属省份", required = true)
    @NotBlank
    private String province;

    @ApiModelProperty(value = "所属市", required = true)
    @NotBlank
    private String city;

    @ApiModelProperty(value = "仓库具体地址", required = true)
    @NotBlank
    private String address;

    @ApiModelProperty(value = "联系人", required = true)
    @NotBlank
    private String contact;

    @ApiModelProperty(value = "电话", required = true)
    @NotBlank
    private String mobile;

    @ApiModelProperty(value = "仓库起送金额", required = true)
    @NotNull
    private BigDecimal thresholdAmount;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank
    private String operator;
}

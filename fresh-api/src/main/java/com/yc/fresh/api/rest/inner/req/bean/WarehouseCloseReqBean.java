package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created by quy on 2019/11/25.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "关闭仓库请求")
public class WarehouseCloseReqBean {

    @ApiModelProperty(value = "仓库编码", required = true)
    @NotBlank
    private String code;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank
    private String operator;
}

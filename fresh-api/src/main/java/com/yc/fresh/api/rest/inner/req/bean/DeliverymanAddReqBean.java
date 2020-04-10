package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Created by quy on 2020/4/10.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "配送人员增加")
public class DeliverymanAddReqBean {

    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank
    @Pattern(regexp = "[0-9]+")
    private String mobile;

    @ApiModelProperty(value = "仓库编码", required = true)
    @NotBlank
    private String warehouseCode;
}

package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by quy on 2020/4/10.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "解除绑定")
public class DeliverymanUnBindReqBean {

    @ApiModelProperty(value = "Id", required = true)
    @NotNull
    private Integer bindId;

    @ApiModelProperty(value = "操作人员", required = true)
    @NotBlank
    private String operator;


}

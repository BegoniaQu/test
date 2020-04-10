package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by quy on 2020/4/10.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "绑定仓库")
public class DeliverymanBindReqBean {

    @ApiModelProperty(value = "Id", required = true)
    @NotNull
    private Long dmId;

    @ApiModelProperty(value = "仓库编码", required = true)
    @NotBlank
    private String warehouseCode;


}

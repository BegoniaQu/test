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
@ApiModel(description = "配送人员编辑")
public class DeliverymanMobileEditReqBean {

    @ApiModelProperty(value = "Id", required = true)
    @NotNull
    private Long dmId;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank
    @Pattern(regexp = "[0-9]+")
    private String mobile;

}

package com.yc.fresh.api.rest.inner.resp.bean;

import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by quy on 2019/11/25.
 * Motto: you can do it
 */

@Getter
@Setter
@RespData
@ApiModel(description = "仓库详情")
public class WarehouseDetailRespBean {

    @ApiModelProperty(value = "仓库编码")
    private String code;

    @ApiModelProperty(value = "仓库名称")
    private String name;

    @ApiModelProperty(value = "所属省份")
    private String province;

    @ApiModelProperty(value = "所属市")
    private String city;

    @ApiModelProperty(value = "仓库具体地址")
    private String address;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "仓库起送金额")
    private BigDecimal thresholdAmount;

    @ApiModelProperty(value = "纬度")
    private String locationX;

    @ApiModelProperty(value = "经度")
    private String locationY;

    @ApiModelProperty(value = "配送费")
    private BigDecimal deliveryFee;
}

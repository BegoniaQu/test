package com.yc.fresh.api.rest.outer.resp.bean;

import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by quy on 2019/12/11.
 * Motto: you can do it
 */
@Getter
@Setter
@RespData
@ApiModel(description = "仓库地址信息")
public class WhInfoRespBean {

    @ApiModelProperty("编码")
    private String code;
    @ApiModelProperty("纬度")
    private String x;
    @ApiModelProperty("经度")
    private String y;
    @ApiModelProperty("起送金额,达到起送金额免配送费")
    private BigDecimal thresholdAmt;
    @ApiModelProperty("未到起送金额需支付的配送费")
    private BigDecimal deliveryFee;

}

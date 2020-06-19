package com.yc.fresh.api.rest.outer.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by quy on 2020/6/15.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "提交的订单信息")
public class OrderAddReqBean {

    /*@ApiModelProperty(value = "仓库编码", required = true)
    private String whCode;*/

    @NotEmpty
    private @Valid List<OrderGd> gds;
    @NotNull
    private @Valid Addr addr;

    @ApiModelProperty(value = "订单备注说明")
    private String comment;

    @ApiModelProperty(value = "配送日期,如2020-06-15", required = true)
    @NotBlank
    private String deliveryDate;

    @ApiModelProperty(value = "开始配送时间点,如：17:00")
    private String deliveryStartTime;

    @ApiModelProperty(value = "最迟送达时间点,如：17:30", required = true)
    @NotBlank
    private String deliveryEndTime;

    @ApiModelProperty(value = "配送费,如果达到减免金额, 则此处填0", required = true)
    @NotNull
    private BigDecimal deliveryFee;

    @ApiModelProperty(value = "订单商品总金额", required = true)
    @NotNull
    private BigDecimal orderAmt;

    @ApiModelProperty(value = "优惠券ID, 后续使用")
    private Long couponId;

    @ApiModelProperty(value = "订单应付金额", required = true)
    @NotNull
    private BigDecimal orderRealAmt;

    @ApiModelProperty(value = "支付方式,1-微信, 2-支付宝", required = true)
    @NotNull
    private Integer payWay;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ApiModel(description = "提交的订单收货信息")
    public static class Addr {

        @ApiModelProperty(value = "姓名", required = true)
        @NotBlank
        private String name;
        @ApiModelProperty(value = "手机号", required = true)
        @NotBlank
        private String mobile;
        @ApiModelProperty(value = "地址", required = true)
        @NotBlank
        private String place;
    }


    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ApiModel(description = "提交的订单商品信息")
    public static class OrderGd {
        @ApiModelProperty(value = "商品ID", required = true)
        @NotBlank
        private String goodsId;

        @ApiModelProperty(value = "购买数量", required = true)
        @NotNull
        @Min(value = 1)
        private Integer num;

        @ApiModelProperty(value = "商品处理信息")
        private String remark;
    }

}


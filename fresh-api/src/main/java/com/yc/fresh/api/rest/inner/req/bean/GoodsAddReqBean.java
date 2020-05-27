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
@ApiModel(description = "售卖商品增加")
public class GoodsAddReqBean {

    @ApiModelProperty(value = "仓库编码", required = true)
    @NotBlank
    private String warehouseCode;

    @ApiModelProperty(value = "SKU编码", required = true)
    @NotNull
    private Long skuId;

    @ApiModelProperty(value = "商品名称", required = true)
    @NotBlank
    private String goodsName;

    @ApiModelProperty(value = "商品主图", required = true)
    @NotBlank
    private String mainPicPath;

    @ApiModelProperty(value = "原价", required = true)
    @NotNull
    private BigDecimal rawPrice;

    @ApiModelProperty(value = "本次售价", required = true)
    @NotNull
    private BigDecimal salePrice;

    @ApiModelProperty(value = "起售数量(如火龙果2个一卖)", required = true)
    @NotNull
    private Integer bundles;

    @ApiModelProperty(value = "计量值(主要是针对蔬菜和水果类用,和单位一起使用才有意义)", required = true)
    private Integer saleCv;

    @ApiModelProperty(value = "描述", required = true)
    private String description;

    @ApiModelProperty(value = "商品详细文案")
    private String descrPath;



}

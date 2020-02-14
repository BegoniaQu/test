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
 * Created by quy on 2019/11/26.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "库存商品增加请求")
public class StockSkuAddReqBean {

    @ApiModelProperty(value = "仓库编码", required = true)
    @NotBlank
    private String warehouseCode;

    @ApiModelProperty(value = "SKU编码", required = true)
    @NotNull
    private Long skuId;

    @ApiModelProperty(value = "入货数量", required = true)
    @NotNull
    private Integer num;

    @ApiModelProperty(value = " 货物进价", required = true)
    @NotNull
    private BigDecimal costPrice;

    @ApiModelProperty(value = "创建人", required = true)
    @NotBlank
    private String createUser;

}

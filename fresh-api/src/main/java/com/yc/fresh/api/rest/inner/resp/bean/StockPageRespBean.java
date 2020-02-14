package com.yc.fresh.api.rest.inner.resp.bean;

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
@ApiModel(description = "库存列表信息")
public class StockPageRespBean {

    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("仓库编码")
    private String warehouseCode;
    @ApiModelProperty("仓库名称")
    private String warehouseName;
    @ApiModelProperty("货物ID")
    private Long skuId;
    @ApiModelProperty("货物名称")
    private String skuName;
    @ApiModelProperty("一级分类")
    private String fCategory;
    @ApiModelProperty("二级分类")
    private String sCategory;
    @ApiModelProperty("数量")
    private Integer num;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("规格")
    private String spec;
    @ApiModelProperty("成本价格")
    private BigDecimal costPrice;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("最后更新时间")
    private String lastModifiedTime;

}

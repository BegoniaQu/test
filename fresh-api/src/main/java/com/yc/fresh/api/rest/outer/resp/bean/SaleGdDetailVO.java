package com.yc.fresh.api.rest.outer.resp.bean;

import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by quy on 2020/5/8.
 * Motto: you can do it
 */
@RespData
@Getter
@Setter
@ApiModel("商品详情")
public class SaleGdDetailVO {

    @ApiModelProperty("商品ID")
    private String goodsId;

    @ApiModelProperty("图片")
    private List<String> pics;
    @ApiModelProperty("文案")
    private String descrPath;
    @ApiModelProperty("商品状况")
    private int state;
}

package com.yc.fresh.api.rest.outer.resp.bean;

import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2020/6/1.
 * Motto: you can do it
 */
@RespData
@ApiModel(description = "购物车操作结果")
@Getter
@Setter
public class ShopCarOperationRespBean {

    @ApiModelProperty(value = "操作结果 [0-正常, 1-卖完, 2-下架]")
    private int result;

}

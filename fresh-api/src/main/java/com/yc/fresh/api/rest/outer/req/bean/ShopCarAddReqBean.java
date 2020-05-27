package com.yc.fresh.api.rest.outer.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created by quy on 2020/5/21.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "填充购物车请求信息")
public class ShopCarAddReqBean {

    @ApiModelProperty(value = "商品ID", required = true)
    @NotBlank
    private String goodsId;

    @ApiModelProperty(value = "数量,理论上应该是固定值1,如果用户点减号,则是-1", required = true)
    private int num;
}

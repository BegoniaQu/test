package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by quy on 2019/12/16.
 * Motto: you can do it
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "商品图片增加")
public class GoodsPicAddOrEditReqBean {

    @ApiModelProperty(value = "商品ID")
    @NotBlank
    private String goodsId;

    @ApiModelProperty(value = "图片信息")
    private @Valid @NotEmpty List<Picture> pics;


}


package com.yc.fresh.api.rest.inner.resp.bean;

import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2019/11/24.
 * Motto: you can do it
 */
@Getter
@Setter
@RespData
@ApiModel(description = "分类简要信息")
public class GdCategoryBriefRespBean {

    @ApiModelProperty("分类ID")
    private Integer id;
    @ApiModelProperty("名称")
    private String name;
}

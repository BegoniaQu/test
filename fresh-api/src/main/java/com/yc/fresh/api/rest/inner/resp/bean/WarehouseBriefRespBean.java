package com.yc.fresh.api.rest.inner.resp.bean;

import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2019/12/11.
 * Motto: you can do it
 */
@Getter
@Setter
@RespData
@ApiModel(description = "仓库简要信息")
public class WarehouseBriefRespBean {

    @ApiModelProperty("编码")
    private String code;
    @ApiModelProperty("名称")
    private String name;

}

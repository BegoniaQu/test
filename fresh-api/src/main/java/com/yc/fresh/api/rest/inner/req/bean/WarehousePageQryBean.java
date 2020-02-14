package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yc.fresh.common.Page;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2019/11/25.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarehousePageQryBean extends Page {

    @ApiParam("仓库名称")
    private String name;
    @ApiParam("城市")
    private String city;
    @ApiParam("状态")
    private Integer status;
}

package com.yc.fresh.api.rest.inner.req.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yc.fresh.common.Page;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2019/12/11.
 * Motto: you can do it
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DmBindingPageQryBean extends Page {

    @ApiParam("仓库编码")
    private String warehouseCode;

    @ApiParam("配送人姓名")
    private String dmName;


}

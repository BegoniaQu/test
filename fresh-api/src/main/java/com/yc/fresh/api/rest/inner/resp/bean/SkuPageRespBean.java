package com.yc.fresh.api.rest.inner.resp.bean;

import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2019/12/16.
 * Motto: you can do it
 */
@Getter
@Setter
@RespData
@ApiModel(description = "SKU列表信息")
public class SkuPageRespBean {

    private Long skuId;

    private String skuName;

    private String fCategory;

    private String sCategory;

    private String unit;

    private String spec;

    private String createTime;

    private String lastModifiedTime;

}

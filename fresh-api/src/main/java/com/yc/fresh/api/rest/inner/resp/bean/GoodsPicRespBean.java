package com.yc.fresh.api.rest.inner.resp.bean;

import com.yc.fresh.api.rest.inner.req.bean.Picture;
import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by quy on 2019/12/16.
 * Motto: you can do it
 */
@ApiModel(description = "商品附图信息")
@Getter
@Setter
@RespData
public class GoodsPicRespBean {


    private String goodsId;
    private List<Picture> pics;
}

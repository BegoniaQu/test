package com.yc.fresh.api.rest.outer.resp.bean;

import com.yc.fresh.api.rest.outer.resp.SuperRespBean;
import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2020/1/8.
 * Motto: you can do it
 */
@Getter
@Setter
@RespData
@ApiModel(description = "授权查询结果")
public class AuthenRespBean extends SuperRespBean {

    @ApiModelProperty("登录状态，0-未注册，1-已注册")
    private int login;

    @ApiModelProperty("注册时用")
    private String spot;
}

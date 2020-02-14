package com.yc.fresh.api.rest.outer.resp;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by quy on 2020/1/10.
 * Motto: you can do it
 */

public class SuperRespBean {

    @ApiModelProperty("tk,当用户是未注册状态时，后台返回的tk是前端调用注册接口时需要传给后端的")
    protected String tk;


    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }
}

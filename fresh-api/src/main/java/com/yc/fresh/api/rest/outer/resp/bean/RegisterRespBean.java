package com.yc.fresh.api.rest.outer.resp.bean;

import com.yc.fresh.api.rest.outer.resp.SuperRespBean;
import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2020/1/10.
 * Motto: you can do it
 */
@Getter
@Setter
@RespData
@ApiModel(description = "注册成功返回信息")
public class RegisterRespBean extends SuperRespBean {

}

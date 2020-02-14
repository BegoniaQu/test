package com.yc.fresh.api.rest.outer.resp.bean;

import com.yc.fresh.api.rest.outer.resp.SuperRespBean;
import com.yc.fresh.common.annotation.RespData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2020/1/10.
 * Motto: you can do it
 */
@Getter
@Setter
@RespData
@ApiModel(description = "个人信息")
public class PersonalRespBean extends SuperRespBean {

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("用户类型")
    private int userType;

    @ApiModelProperty("手机号,脱敏数据,如：134****1025")
    private String phone;

}

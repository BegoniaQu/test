package com.yc.fresh.common.tp.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quy on 2020/1/8.
 * Motto: you can do it
 */
@Getter
@Setter
public class WxAuthInfo {
    @JsonProperty("session_key")
    private String sessionKey;
    private String openid;

}

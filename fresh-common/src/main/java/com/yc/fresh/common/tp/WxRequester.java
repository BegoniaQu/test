package com.yc.fresh.common.tp;

import com.yc.fresh.common.exception.SCApiRuntimeException;
import com.yc.fresh.common.http.HttpUtils;
import com.yc.fresh.common.tp.bean.WxAuthInfo;
import com.yc.fresh.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by quy on 2020/1/8.
 * Motto: you can do it
 * 微信
 */
@Slf4j
public class WxRequester {


    private static final String PROP_APPID = "appid";
    private static final String PROP_SECRET = "secret";
    private static final String PROP_JSCODE = "js_code";
    private static final String PROP_GRANTTYPE = "grant_type";


    /**
     * appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
     * @param appid
     * @return
     */
    public static Map<String, String> wrapAuthParam(String appid, String secret, String code) {
        Map<String, String> map  = new HashMap<>();
        Assert.hasText(appid, "appid must be not blank");
        Assert.hasText(secret, "secret must be not blank");
        Assert.hasText(code, "code must be not blank");
        map.put(PROP_APPID, appid);
        map.put(PROP_SECRET, secret);
        map.put(PROP_JSCODE, code);
        map.put(PROP_GRANTTYPE, "authorization_code");
        return map;
    }

    /**
     * eg : {"session_key":"khwV6P7sYIvd0KXVR72OEA==","openid":"ozxYF5nGlRP2lixbMeqw1JaxNRVw"}
     * @param url
     * @param params
     * @return
     */
    public static WxAuthInfo get(String url, Map<String,String> params) {
        String str = HttpUtils.getInstance().doGet(url, params);
        if (Objects.isNull(str)) {
            throw new SCApiRuntimeException();
        }
        log.info(str);
        WxAuthInfo wxAuthInfo = JsonUtil.getObjectFromJson(str, WxAuthInfo.class);
        if (wxAuthInfo == null || StringUtils.isEmpty(wxAuthInfo.getOpenid())) {
            throw new SCApiRuntimeException("illegal operation");
        }
        return wxAuthInfo;
    }
}

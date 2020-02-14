package com.yc.fresh.common.http;

import lombok.Getter;

/**
 * Created by quy on 2019/9/5.
 * Motto: you can do it
 */
@Getter
public class FailedContent {


    FailedContent(String url, String jsonParam, String reason) {
        this.jsonParam = jsonParam;
        this.url = url;
        this.reason = reason;
    }

    private String url;
    private String jsonParam;
    private String reason;

}

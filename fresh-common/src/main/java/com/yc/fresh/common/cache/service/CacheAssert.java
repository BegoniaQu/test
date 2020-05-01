package com.yc.fresh.common.cache.service;

import com.yc.fresh.common.exception.SCRedisRuntimeException;

/**
 * Created by quy on 2020/4/22.
 * Motto: you can do it
 */
public class CacheAssert {

    private static final String defaultErrorMsg = "cache failed";

    public static void isOk(boolean result, String errMsg) {
        if (!result) {
            throw new SCRedisRuntimeException(errMsg);
        }
    }

    public static void isOk(boolean result) {
        if (!result) {
            throw new SCRedisRuntimeException(defaultErrorMsg);
        }
    }
}

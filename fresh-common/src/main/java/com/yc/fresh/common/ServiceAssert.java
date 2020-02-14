package com.yc.fresh.common;

import com.yc.fresh.common.exception.SCServiceRuntimeException;

/**
 * Created by quy on 2019/11/24.
 * Motto: you can do it
 */
public class ServiceAssert {

    public static void isOk(boolean result, String errMsg) {
        if (!result) {
            throw new SCServiceRuntimeException(errMsg);
        }
    }
}

package com.yc.fresh.common.exception;

/**
 * Created by quy on 2019/11/30.
 * Motto: you can do it
 */
public class SCApiRuntimeException extends RuntimeException {

    public SCApiRuntimeException() {
        super("网络异常, 请稍后重试");
    }

    public SCApiRuntimeException(String message) {
        super(message);
    }

    public SCApiRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}

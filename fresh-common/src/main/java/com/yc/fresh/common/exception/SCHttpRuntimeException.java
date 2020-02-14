package com.yc.fresh.common.exception;

/**
 * Created by quy on 2019/8/16.
 * Motto: you can do it
 */
public class SCHttpRuntimeException extends RuntimeException{



    public SCHttpRuntimeException() {
        super();
    }

    public SCHttpRuntimeException(String message) {
        super(message);
    }

    public SCHttpRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}

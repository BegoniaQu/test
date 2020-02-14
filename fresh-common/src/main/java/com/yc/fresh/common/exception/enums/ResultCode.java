package com.yc.fresh.common.exception.enums;



/**
 * Created by quyang on 2018/4/2.
 */
public enum ResultCode {

    DAO_EXCEPTION("SCDaoRuntimeException", 1000),
    SERVICE_EXCEPTION("SCServiceRuntimeException", 1001),
    HTTP_EXCEPTION("SCHttpRuntimeException", 1002),
    REDIS_EXCEPTION("SCRedisRuntimeException", 1003),
    TARGET_EXISTED("SCTargetExistsRuntimeException", 1004),
    TARGET_NOT_FOUND("SCTargetNotFoundRuntimeException", 1005),
    UNAUTHORIZED("SCUnAuthorizedRuntimeException", 1006),
    SYSTEM_ERROR("", -1),
    CUSTOM_EXCEPTION("SCCustomRuntimeException", 10000),
    TOKEN_EXPIRED("SCTokenExpiredRuntimeException", 1007),
    ARG_EXCEPTION("IllegalArgumentException", 1008),
    //spring valid exception
    SPRING_BIND_EXCEPTION(
            "MethodArgumentNotValidException," +
            "MissingServletRequestParameterException," +
            "HttpMessageNotReadableException", 1009),
    API_EXCEPTION("SCApiRuntimeException", 2000);

    private String name;
    private int code;

    ResultCode(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static ResultCode search(Exception e) {
        for (ResultCode one : ResultCode.values()) {
            if (one.getName().equals("")) {
                continue;
            }
            if (one.getName().contains(e.getClass().getSimpleName())) {
                return one;
            }
        }
        return ResultCode.SYSTEM_ERROR;
    }


    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

}

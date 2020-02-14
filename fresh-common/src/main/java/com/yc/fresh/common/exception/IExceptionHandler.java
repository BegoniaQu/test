package com.yc.fresh.common.exception;


import com.yc.fresh.common.ResultBean;
import com.yc.fresh.common.exception.enums.ResultCode;
import com.yc.fresh.common.utils.ResultUtils;

/**
 * Created by quyang on 2018/4/2.
 */
public interface IExceptionHandler {

    default ResultBean handle(Exception e){
        ResultCode resultCode = ResultCode.search(e);
        String msg;
        if (resultCode.name().equals(ResultCode.SPRING_BIND_EXCEPTION.name())) {
            msg = "please provide appropriate parameter";
        } else {
            msg = e.getMessage();
        }
        return ResultUtils.fail(resultCode.getCode(), msg);
    }
}

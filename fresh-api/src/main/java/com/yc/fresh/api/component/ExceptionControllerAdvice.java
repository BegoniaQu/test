package com.yc.fresh.api.component;

import com.yc.fresh.common.ResultBean;
import com.yc.fresh.common.exception.IExceptionHandler;
import com.yc.fresh.common.exception.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Created by quyang on 2018/4/2.
 */
@RestControllerAdvice("com.yc.fresh.api.rest")
@Slf4j
public class ExceptionControllerAdvice implements IExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResultBean errorHandler(Exception e) {
        ResultBean resultBean = handle(e);
        if(resultBean.getCode() == ResultCode.SYSTEM_ERROR.getCode()){
            resultBean.setMsg("服务异常,请稍后重试");
            log.error("", e);
        }
        return resultBean;
    }

}

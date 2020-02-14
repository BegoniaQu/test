package com.yc.fresh.api.component;

import com.yc.fresh.common.ResultBean;
import com.yc.fresh.common.annotation.RespData;
import com.yc.fresh.common.utils.ResultUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Created by quy on 2019/7/17.
 * Motto: you can do it
 *  <h3>Type的组成部分说明：</h3>
 *      <p>原始类型：一般意义上的java类，由class类实现</p>
 *      <p>参数化类型：ParameterizedType接口的实现类</p>
 *      <p>数组类型：GenericArrayType接口的实现类</p>
 */
@ControllerAdvice("com.yc.fresh.api.rest")
public class ResultWrapperAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {

        Type type = returnType.getGenericParameterType();
        if (Void.TYPE.equals(type)) {
            return true;
        }

        if (type instanceof ParameterizedType) {
            Type [] argTypes = ((ParameterizedType) type).getActualTypeArguments();
            type = argTypes[0];
        }
        Class cls = (Class) type;
        if (cls.isAnnotationPresent(RespData.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (Objects.isNull(body)) {
            body = "";
        }
        ResultBean resultBean = ResultUtils.ok(body);
        return resultBean;
    }
}

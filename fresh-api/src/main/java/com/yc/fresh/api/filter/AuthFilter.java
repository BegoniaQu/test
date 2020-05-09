package com.yc.fresh.api.filter;

import com.yc.fresh.common.ResultBean;
import com.yc.fresh.common.exception.enums.ResultCode;
import com.yc.fresh.common.utils.ResultUtils;
import com.yc.fresh.common.utils.KeyUtils;
import org.springframework.core.Ordered;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by quy on 2020/1/9.
 * Motto: you can do it
 */
public class AuthFilter extends AbstractFilter implements Ordered {

    private static final String authKw = "/outer/a/"; //需要校验身份
    private static final String wxReg = "/outer/wx/c/reg"; //微信小程序用户注册时专用

    private static final Set<String> nonAuthKws;

    static {
        nonAuthKws = new HashSet<>();
        nonAuthKws.add("/outer/n/");//无需校验身份
        nonAuthKws.add("/inner/"); //内部访问通道
        nonAuthKws.add("/outer/wx/c/auth");//微信小程序用户登录时专用
        nonAuthKws.add("swagger"); //swagger
        nonAuthKws.add("v2/api-docs");//swagger
       /* nonAuthKws.add("/doc.html");//swagger
        nonAuthKws.add("/favicon.ico");//swagger
        nonAuthKws.add("/service-worker");//swagger*/
    }

    private boolean isFreeVisit(String uri) {
        for (String kw : nonAuthKws) {
            if (uri.contains(kw)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (isFreeVisit(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        else if (uri.contains(wxReg) || uri.contains(authKw)) {
            String core;
            String token = request.getHeader("tk");
            if (token == null) {
                ResultBean resultBean = ResultUtils.fail(ResultCode.UNAUTHORIZED.getCode(), "request forbidden");
                handle(response, resultBean);
                return;
            }
            core = KeyUtils.parse(token);
            if (core == null) {
                ResultBean resultBean = ResultUtils.fail(ResultCode.UNAUTHORIZED.getCode(), "invalid auth param");
                handle(response, resultBean);
                return;
            }
            if (uri.contains(wxReg)) {
                request.setAttribute("openid", core);
            } else if (uri.contains(authKw)) {
                request.setAttribute("user", core);
            }
            filterChain.doFilter(request, response);
        } else {
            ResultBean resultBean = ResultUtils.fail(ResultCode.TARGET_NOT_FOUND.getCode(), "Illegal Request");
            handle(response, resultBean);
            return;
        }


    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1;
    }
}

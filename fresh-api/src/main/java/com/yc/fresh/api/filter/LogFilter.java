package com.yc.fresh.api.filter;

import com.yc.fresh.common.ResultBean;
import com.yc.fresh.common.exception.enums.ResultCode;
import com.yc.fresh.common.utils.JsonUtil;
import com.yc.fresh.common.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by quy on 2019/9/10.
 * Motto: you can do it
 */
public class LogFilter extends AbstractFilter implements Ordered {

    private static final Logger log = LoggerFactory.getLogger(LogFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        MyRequestWrapper myRequestWrapper = null;
        StringBuilder sb = new StringBuilder();
        String reqMethod = request.getMethod();
        String uri = request.getRequestURI();
        String contentType = request.getHeader("Content-Type");

        sb.append("#Request ").append(reqMethod);
        sb.append(",url:").append(uri).append(",content-type:").append(contentType);
        if(reqMethod.equals(HttpMethod.GET.name())) { //Get 日志在logFilter中处理
            sb.append(" ,params=[");
            Enumeration<String> enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String k = enumeration.nextElement();
                String v = request.getParameter(k);
                sb.append(k).append("=").append(v).append("&");
            }
            int index = sb.lastIndexOf("&");
            if (index >= 0) {
                sb.deleteCharAt(index);
            }
            sb.append("]");
        } else if (reqMethod.equals(HttpMethod.POST.name())) {
            if (StringUtils.isEmpty(contentType)) {
                ResultBean resultBean = ResultUtils.fail(ResultCode.ARG_EXCEPTION.getCode(), "please specify right content-type");
                handle(response, resultBean);
                return;
            }
            if (contentType.equals(CONTENT_TYPE)) {
                myRequestWrapper = new MyRequestWrapper(request, getBody(request));
                sb.append(" ,body: ");
                sb.append(myRequestWrapper.getBody());
                sb.append(".");
            }

        }
        log.info(sb.toString());

        if (myRequestWrapper != null) {
            filterChain.doFilter(myRequestWrapper, response);
        } else {
            filterChain.doFilter(request, response);
        }
        log.info("request cost time : " + (System.currentTimeMillis()-start) + "ms");
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private String getBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            String str;
            while ((str = reader.readLine()) != null) {
                //sb.append(str.replaceAll("\n", "").replaceAll("\\s", ""));
                sb.append(str.replaceAll("\n", ""));
            }
        } catch (IOException e) {
           throw e;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return sb.toString();
    }



}

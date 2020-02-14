package com.yc.fresh.api.filter;

import com.yc.fresh.common.ResultBean;
import com.yc.fresh.common.utils.JsonUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by quy on 2020/1/9.
 * Motto: you can do it
 */
public abstract class AbstractFilter extends OncePerRequestFilter {

    protected static final String CONTENT_TYPE = "application/json";

    protected void handle(HttpServletResponse response, ResultBean rb) throws IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter pw = response.getWriter();
        pw.write(JsonUtil.getJsonFromObject(rb));
        pw.flush();
        pw.close();
    }

}

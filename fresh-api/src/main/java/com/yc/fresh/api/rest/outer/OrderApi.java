package com.yc.fresh.api.rest.outer;

import com.yc.fresh.api.component.UserVerifier;
import com.yc.fresh.api.rest.outer.req.bean.OrderAddReqBean;
import com.yc.fresh.busi.outer.OrderManager;
import com.yc.fresh.service.entity.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * Created by quy on 2019/11/18.
 * Motto: you can do it
 */
@Api(tags = "Open-订单")
@RestController
@RequestMapping("rest/outer/a/o")
public class OrderApi {

    private final OrderManager orderManager;
    private final UserVerifier userVerifier;

    public OrderApi(OrderManager orderManager, UserVerifier userVerifier) {
        this.orderManager = orderManager;
        this.userVerifier = userVerifier;
    }

    @ApiOperation(value="订单创建", produces="application/json", httpMethod = "POST")
    @PostMapping("/crt")
    public void add(@Valid @RequestBody OrderAddReqBean orderAddReqBean, HttpServletRequest request) {
        UserInfo user = userVerifier.verify(request);

    }



    @ApiOperation(value="订单列表", produces="application/json", httpMethod = "GET")
    @GetMapping("/list")
    public void test()  {

    }
}

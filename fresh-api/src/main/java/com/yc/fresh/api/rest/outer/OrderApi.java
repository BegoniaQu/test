package com.yc.fresh.api.rest.outer;

import com.yc.fresh.service.IUserOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by quy on 2019/11/18.
 * Motto: you can do it
 */
@Api(tags = "Open-订单")
@RestController
@RequestMapping("rest/outer/a/o")
public class OrderApi {

    private final IUserOrderService userOrderService;


    @Autowired
    public OrderApi(IUserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }


    @ApiOperation(value="订单列表", produces="application/json", httpMethod = "GET")
    @GetMapping("/list")
    public void test() throws InterruptedException {

    }
}

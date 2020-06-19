package com.yc.fresh.api.rest.outer.convertor;

import com.yc.fresh.api.rest.outer.req.bean.OrderAddReqBean;
import com.yc.fresh.service.entity.UserInfo;
import com.yc.fresh.service.entity.UserOrder;
import com.yc.fresh.service.entity.UserOrderGd;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quy on 2020/6/15.
 * Motto: you can do it
 */
@Component
public class OrderConvertor {


    public UserOrder convert2Order(UserInfo userInfo, OrderAddReqBean reqBean) {
        //check data

        UserOrder userOrder = new UserOrder();



        return userOrder;
    }

    public List<UserOrderGd> convert2OrderGd(OrderAddReqBean reqBean) {
        List<UserOrderGd> userOrderGdList = new ArrayList<>();


        return userOrderGdList;
     }
}

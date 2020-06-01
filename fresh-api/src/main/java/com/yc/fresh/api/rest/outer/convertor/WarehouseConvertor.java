package com.yc.fresh.api.rest.outer.convertor;

import com.yc.fresh.api.rest.outer.resp.bean.WhInfoRespBean;
import com.yc.fresh.service.entity.Warehouse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quy on 2020/6/1.
 * Motto: you can do it
 */
public class WarehouseConvertor {


    public static List<WhInfoRespBean> convert2BeanList(List<Warehouse> warehouses) {
        List<WhInfoRespBean> respBeans = new ArrayList<>();
        for (Warehouse one : warehouses) {
            WhInfoRespBean whInfoRespBean = new WhInfoRespBean();
            whInfoRespBean.setCode(one.getCode());
            whInfoRespBean.setDeliveryFee(one.getDeliveryFee());
            whInfoRespBean.setThresholdAmt(one.getThresholdAmount());
            whInfoRespBean.setX(one.getLocationX());
            whInfoRespBean.setY(one.getLocationY());
            respBeans.add(whInfoRespBean);
        }
        return respBeans;
    }
}

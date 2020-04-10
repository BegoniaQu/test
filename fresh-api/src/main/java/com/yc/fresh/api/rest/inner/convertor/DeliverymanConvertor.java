package com.yc.fresh.api.rest.inner.convertor;

import com.yc.fresh.api.rest.inner.req.bean.DeliverymanAddReqBean;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.entity.DeliverymanInfo;
import com.yc.fresh.service.enums.DeliverymanStatusEnum;

import java.time.LocalDateTime;

/**
 * Created by quy on 2020/4/10.
 * Motto: you can do it
 */
public class DeliverymanConvertor {

    public static DeliverymanInfo convert2Entity(DeliverymanAddReqBean bean) {
        LocalDateTime currentDate = DateUtils.getCurrentDate();
        DeliverymanInfo one = new DeliverymanInfo();
        one.setDmName(bean.getName());
        one.setMobile(bean.getMobile());
        one.setStatus(DeliverymanStatusEnum.ok.getV());
        one.setCreateTime(currentDate);
        one.setLastModifiedTime(currentDate);
        return one;
    }


}

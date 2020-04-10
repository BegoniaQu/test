package com.yc.fresh.api.rest.inner.convertor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yc.fresh.api.rest.inner.req.bean.DeliverymanAddReqBean;
import com.yc.fresh.api.rest.inner.resp.bean.DeliverymanBriefRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.DmBindingPageRespBean;
import com.yc.fresh.common.PageResult;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.dto.DmBindingDTO;
import com.yc.fresh.service.entity.DeliverymanInfo;
import com.yc.fresh.service.enums.DeliverymanStatusEnum;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static PageResult<DmBindingPageRespBean> convert2PageBean(IPage<DmBindingDTO> page) {
        List<DmBindingDTO> records = page.getRecords();
        List<DmBindingPageRespBean> respBeans = records.stream().map(t -> {
            DmBindingPageRespBean respBean = new DmBindingPageRespBean();
            BeanUtils.copyProperties(t, respBean);
            return respBean;
        }).collect(Collectors.toList());
        return new PageResult(respBeans, page.getCurrent(), page.getSize(), page.getTotal());
    }

    public static List<DeliverymanBriefRespBean> convert2BeanList(List<DeliverymanInfo> lst) {
        return lst.stream().map(t->{
            DeliverymanBriefRespBean respBean = new DeliverymanBriefRespBean();
            respBean.setDmId(t.getDmId());
            respBean.setDmName(t.getDmName());
            respBean.setMobile(t.getMobile());
            return respBean;
        }).collect(Collectors.toList());
    }
}

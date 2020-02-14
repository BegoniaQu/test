package com.yc.fresh.api.rest.inner.convertor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yc.fresh.api.rest.inner.req.bean.WarehouseAddReqBean;
import com.yc.fresh.api.rest.inner.req.bean.WarehouseEditReqBean;
import com.yc.fresh.api.rest.inner.resp.bean.WarehouseBriefRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.WarehouseDetailRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.WarehousePageRespBean;
import com.yc.fresh.common.PageResult;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.entity.Warehouse;
import com.yc.fresh.service.enums.WarehouseStatusEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by quy on 2019/11/25.
 * Motto: you can do it
 */
public class WarehouseConvertor {

    public static Warehouse convert2Entity(WarehouseAddReqBean reqBean) {
        LocalDateTime currentDate = DateUtils.getCurrentDate();
        Warehouse t = new Warehouse();
        BeanUtils.copyProperties(reqBean,t);
        t.setStatus(WarehouseStatusEnum.AVAILABLE.getV());
        t.setCreateTime(currentDate);
        t.setLastModifiedTime(currentDate);
        return t;
    }

    public static Warehouse convert2Entity(WarehouseEditReqBean reqBean) {
        LocalDateTime currentDate = DateUtils.getCurrentDate();
        Warehouse t = new Warehouse();
        BeanUtils.copyProperties(reqBean,t);
        t.setLastModifiedTime(currentDate);
        return t;
    }

    public static WarehouseDetailRespBean convert2Bean(Warehouse entity) {
        WarehouseDetailRespBean respBean = new WarehouseDetailRespBean();
        Assert.notNull(entity, "invalid param");
        BeanUtils.copyProperties(entity, respBean, "status");
        //respBean.setStatus(WarehouseStatusEnum.getName(entity.getStatus()));
        return respBean;
    }

    public static PageResult<WarehousePageRespBean> convert2PageBean(IPage<Warehouse> page) {
        List<WarehousePageRespBean> respBeans = new ArrayList<>();
        for (Warehouse one : page.getRecords()) {
            WarehousePageRespBean respBean = new WarehousePageRespBean();
            BeanUtils.copyProperties(one, respBean, "status", "createTime");
            respBean.setStatus(WarehouseStatusEnum.getName(one.getStatus()));
            respBean.setCreateTime(DateUtils.convert2Str(one.getCreateTime()));
            respBeans.add(respBean);
        }
        return new PageResult(respBeans, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     *
     * @param list
     * @return
     */
    public static List<WarehouseBriefRespBean> convert2Bean(List<Warehouse> list) {
        List<WarehouseBriefRespBean> respBeans = new ArrayList<>();
        for (Warehouse one : list) {

            WarehouseBriefRespBean respBean = new WarehouseBriefRespBean();
            respBean.setCode(one.getCode());
            respBean.setName(one.getName());
            respBeans.add(respBean);
        }
        return respBeans;
    }

    public static Map<String, Warehouse> convert2Map(List<Warehouse> list) {
        Map<String, Warehouse> map = new HashMap<>();
        for (Warehouse one : list) {
            map.put(one.getCode(), one);
        }
        return map;
    }
}

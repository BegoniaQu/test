package com.yc.fresh.api.rest.inner.convertor;

import com.yc.fresh.api.rest.inner.req.bean.GdCategoryAddReqBean;
import com.yc.fresh.api.rest.inner.req.bean.GdCategoryEditReqBean;
import com.yc.fresh.api.rest.inner.resp.bean.GdCategoryBriefRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.GdCategoryRespBean;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.enums.GdCategoryStatusEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by quy on 2019/11/22.
 * Motto: you can do it
 */
public class GdCategoryConvertor {


    public static GdCategory convert2Entity(GdCategoryAddReqBean reqBean) {
        GdCategory gdCategory = new GdCategory();
        gdCategory.setName(reqBean.getName());
        gdCategory.setParentId(reqBean.getParentId()== null ? 0 : reqBean.getParentId());
        gdCategory.setStatus(GdCategoryStatusEnum.AVAILABLE.getV());
        gdCategory.setSort(reqBean.getSort());
        gdCategory.setPicPath(reqBean.getPicPath());
        return gdCategory;
    }

    public static GdCategory convert2Entity(GdCategoryEditReqBean reqBean) {
        GdCategory gdCategory = new GdCategory();
        gdCategory.setId(reqBean.getId());
        gdCategory.setName(reqBean.getName());
        gdCategory.setParentId(reqBean.getParentId() == null ? 0 : reqBean.getParentId());
        gdCategory.setSort(reqBean.getSort());
        gdCategory.setPicPath(reqBean.getPicPath());
        return gdCategory;
    }

    public static List<GdCategoryRespBean> convert2BeanList(List<GdCategory> lst) {
        return lst.stream().map(t->{
            GdCategoryRespBean respBean = new GdCategoryRespBean();
            respBean.setId(t.getId());
            respBean.setName(t.getName());
            respBean.setSort(t.getSort());
            respBean.setPath(t.getPicPath());
            return respBean;
        }).collect(Collectors.toList());
    }

    public static Map<Integer, GdCategory> convert2Map(List<GdCategory> list) {
        Map<Integer, GdCategory> map = new HashMap<>();
        for (GdCategory one : list) {
            map.put(one.getId(), one);
        }
        return map;
    }

    public static GdCategory getFromList(Integer id, List<GdCategory> list) {
        for (GdCategory one : list) {
            if (one.getId() == id.intValue()) {
                return one;
            }
        }
        return null;
    }

    public static List<GdCategoryBriefRespBean> convert2BriefBeanList(List<GdCategory> lst) {
        List<GdCategoryBriefRespBean> respBeans = new ArrayList<>();
        for (GdCategory one : lst) {
            GdCategoryBriefRespBean respBean = new GdCategoryBriefRespBean();
            respBean.setId(one.getId());
            respBean.setName(one.getName());
            respBeans.add(respBean);
        }
        return respBeans;
    }
}

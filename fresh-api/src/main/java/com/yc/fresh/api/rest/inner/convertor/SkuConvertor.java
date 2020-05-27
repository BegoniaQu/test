package com.yc.fresh.api.rest.inner.convertor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yc.fresh.api.rest.inner.req.bean.SkuAddReqBean;
import com.yc.fresh.api.rest.inner.req.bean.SkuEditReqBean;
import com.yc.fresh.api.rest.inner.resp.bean.SkuDetailRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.SkuPageRespBean;
import com.yc.fresh.common.PageResult;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.entity.SkuInfo;
import com.yc.fresh.service.enums.UnitTypeEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by quy on 2019/11/27.
 * Motto: you can do it
 */
public class SkuConvertor {

    public static SkuInfo convert2Entity(SkuAddReqBean reqBean) {
        LocalDateTime curDate = DateUtils.getCurrentDate();
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setFCategoryId(reqBean.getFCategoryId());
        skuInfo.setSCategoryId(reqBean.getSCategoryId());
        skuInfo.setSkuName(dealName(reqBean.getSkuName()));
        skuInfo.setSpec(reqBean.getSpec());
        skuInfo.setUnit(reqBean.getUnit());
        skuInfo.setUnitType(reqBean.getUnitType());
        skuInfo.setCreateTime(curDate);
        skuInfo.setLastModifiedTime(curDate);
        return skuInfo;
    }

    private static String dealName(String name) {
        return name.replaceAll("（", "(").
                replaceAll("）", ")");
    }

    public static SkuInfo convert2Entity(SkuEditReqBean reqBean) {
        LocalDateTime curDate = DateUtils.getCurrentDate();
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSkuId(reqBean.getSkuId());
        skuInfo.setFCategoryId(reqBean.getFCategoryId());
        skuInfo.setSCategoryId(reqBean.getSCategoryId());
        skuInfo.setSkuName(dealName(reqBean.getSkuName()));
        skuInfo.setSpec(reqBean.getSpec());
        skuInfo.setUnit(reqBean.getUnit());
        skuInfo.setUnitType(reqBean.getUnitType());
        skuInfo.setLastModifiedTime(curDate);
        return skuInfo;
    }

    public static SkuDetailRespBean convert2Bean(SkuInfo skuInfo) {
        SkuDetailRespBean respBean = new SkuDetailRespBean();
        respBean.setSkuId(skuInfo.getSkuId());
        respBean.setSkuName(skuInfo.getSkuName());
        respBean.setFCategoryId(skuInfo.getFCategoryId());
        respBean.setSCategoryId(skuInfo.getSCategoryId());
        respBean.setSpec(skuInfo.getSpec());
        respBean.setUnit(skuInfo.getUnit());
        respBean.setUnitType(skuInfo.getUnitType());
        return respBean;
    }


    public static PageResult<SkuPageRespBean> convert2PageBean(IPage<SkuInfo> page, List<GdCategory> fCategories, Map<Integer, List<GdCategory>> sCategoryMap) {
        Map<Integer, GdCategory> fCategoryMap = GdCategoryConvertor.convert2Map(fCategories);
        List<SkuPageRespBean> respBeans = new ArrayList<>();
        for (SkuInfo one : page.getRecords()) {
            SkuPageRespBean respBean = new SkuPageRespBean();
            respBean.setSkuId(one.getSkuId());
            respBean.setSkuName(one.getSkuName());
            respBean.setCreateTime(DateUtils.convert2Str(one.getCreateTime()));
            respBean.setLastModifiedTime(DateUtils.convert2Str(one.getLastModifiedTime()));
            //一级
            Integer fCategoryId = one.getFCategoryId();
            respBean.setFCategory(fCategoryMap.get(fCategoryId).getName());
            //二级
            List<GdCategory> sCategories = sCategoryMap.get(fCategoryId);
            GdCategory sCategory = GdCategoryConvertor.getFromList(one.getSCategoryId(), sCategories);
            respBean.setSCategory(sCategory.getName());
            respBean.setUnit(one.getUnit());
            respBean.setSpec(one.getSpec());
            respBeans.add(respBean);
        }
        return new PageResult(respBeans, page.getCurrent(), page.getSize(), page.getTotal());
    }

}

package com.yc.fresh.api.rest.inner.convertor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yc.fresh.api.rest.inner.req.bean.StockSkuAddReqBean;
import com.yc.fresh.api.rest.inner.resp.bean.StockGdRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.StockPageRespBean;
import com.yc.fresh.common.PageResult;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.entity.Warehouse;
import com.yc.fresh.service.entity.WarehouseStock;
import com.yc.fresh.service.enums.UnitTypeEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by quy on 2019/12/1.
 * Motto: you can do it
 */
public class WarehouseStockConvertor {

    public static WarehouseStock convert2Entity(StockSkuAddReqBean reqBean) {
        LocalDateTime currentDate = DateUtils.getCurrentDate();
        WarehouseStock ws = new WarehouseStock();
        ws.setWarehouseCode(reqBean.getWarehouseCode());
        ws.setSkuId(reqBean.getSkuId());
        ws.setNum(reqBean.getNum());
        ws.setCostPrice(reqBean.getCostPrice());
        ws.setCreateTime(currentDate);
        ws.setLastModifiedTime(currentDate);
        return ws;
    }


    public static PageResult<StockPageRespBean> convert2PageBean(IPage<WarehouseStock> page, List<GdCategory> fCategories, Map<Integer, List<GdCategory>> sCategoryMap, List<Warehouse> warehouses) {
        Map<String, Warehouse> warehouseMap = WarehouseConvertor.convert2Map(warehouses);
        Map<Integer, GdCategory> fCategoryMap = GdCategoryConvertor.convert2Map(fCategories);

        List<StockPageRespBean> respBeans = new ArrayList<>();
        for (WarehouseStock one : page.getRecords()) {
            StockPageRespBean respBean = new StockPageRespBean();
            respBean.setId(one.getId());
            respBean.setWarehouseCode(one.getWarehouseCode());
            respBean.setWarehouseName(warehouseMap.get(one.getWarehouseCode()).getName());
            respBean.setSkuId(one.getSkuId());
            respBean.setSkuName(one.getSkuName());
            //一级
            Integer fCategoryId = one.getFCategoryId();
            respBean.setFCategory(fCategoryMap.get(fCategoryId).getName());
            //二级
            List<GdCategory> sCategories = sCategoryMap.get(fCategoryId);
            GdCategory sCategory = GdCategoryConvertor.getFromList(one.getSCategoryId(), sCategories);
            respBean.setSCategory(sCategory.getName());
            respBean.setNum(one.getNum());
            respBean.setCostPrice(one.getCostPrice());
            respBean.setUnit(one.getUnit());
            respBean.setSpec(one.getSpec());
            respBean.setCreateTime(DateUtils.convert2Str(one.getCreateTime()));
            respBean.setLastModifiedTime(DateUtils.convert2Str(one.getLastModifiedTime()));
            respBeans.add(respBean);
        }
        return new PageResult(respBeans, page.getCurrent(), page.getSize(), page.getTotal());
    }


    public static List<StockGdRespBean> convert(List<WarehouseStock> warehouseStocks) {
        List<StockGdRespBean> respBeans = new ArrayList<>();
        for (WarehouseStock one : warehouseStocks) {
            StockGdRespBean respBean = new StockGdRespBean();
            respBean.setSkuId(one.getSkuId());
            respBean.setSkuName(one.getSkuName());
            respBean.setUnitType(one.getUnitType());
            respBean.setUnit(one.getUnit());
            respBean.setSpec(one.getSpec());
            respBean.setCostPrice(one.getCostPrice());
            respBeans.add(respBean);
        }
        return respBeans;
    }
}

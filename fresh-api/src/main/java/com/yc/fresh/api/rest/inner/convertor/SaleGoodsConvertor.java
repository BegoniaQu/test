package com.yc.fresh.api.rest.inner.convertor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yc.fresh.api.rest.inner.req.bean.*;
import com.yc.fresh.api.rest.inner.resp.bean.GoodsDetailRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.GoodsPageRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.GoodsPicRespBean;
import com.yc.fresh.common.PageResult;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.GoodsSalePic;
import com.yc.fresh.service.entity.Warehouse;
import com.yc.fresh.service.enums.SaleGoodsStatusEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by quy on 2019/12/8.
 * Motto: you can do it
 */
public class SaleGoodsConvertor {


    public static GoodsSaleInfo convert2Entity(GoodsAddReqBean reqBean) {
        LocalDateTime currentDate = DateUtils.getCurrentDate();
        GoodsSaleInfo goods = new GoodsSaleInfo();
        //goods.setWarehouseCode(reqBean.getWarehouseCode());
        goods.setSkuId(reqBean.getSkuId());
        goods.setGoodsName(reqBean.getGoodsName());
        goods.setMPicPath(reqBean.getMainPicPath());
        goods.setRawPrice(reqBean.getRawPrice());
        goods.setSalePrice(reqBean.getSalePrice());
        goods.setBundles(reqBean.getBundles());
        goods.setSaleCv(reqBean.getSaleCv());
        goods.setDescription(reqBean.getDescription());
        goods.setDescrPath(reqBean.getDescrPath());
        goods.setCreateTime(currentDate);
        goods.setLastModifiedTime(currentDate);
        return goods;
    }


    public static GoodsSaleInfo convert2Entity(GoodsEditReqBean reqBean) {
        LocalDateTime currentDate = DateUtils.getCurrentDate();
        GoodsSaleInfo goods = new GoodsSaleInfo();
        goods.setGoodsId(reqBean.getGoodsId());
        goods.setGoodsName(reqBean.getGoodsName());
        goods.setMPicPath(reqBean.getMainPicPath());
        goods.setRawPrice(reqBean.getRawPrice());
        goods.setSalePrice(reqBean.getSalePrice());
        goods.setBundles(reqBean.getBundles());
        goods.setSaleCv(reqBean.getSaleCv());
        goods.setDescription(reqBean.getDescription());
        goods.setDescrPath(reqBean.getDescrPath());
        goods.setLastModifiedTime(currentDate);
        return goods;
    }


    public static PageResult<GoodsPageRespBean> convert2PageBean(IPage<GoodsSaleInfo> page, List<GdCategory> fCategories, Map<Integer, List<GdCategory>> sCategoryMap) {
        //Map<String, Warehouse> warehouseMap = WarehouseConvertor.convert2Map(warehouses);
        Map<Integer, GdCategory> fCategoryMap = GdCategoryConvertor.convert2Map(fCategories);
        List<GoodsPageRespBean> respBeans = new ArrayList<>();
        String[] ignoreCol = new String[]{"status", "category", "createTime", "lastModifiedTime"};
        for (GoodsSaleInfo one : page.getRecords()) {
            GoodsPageRespBean respBean = new GoodsPageRespBean();
            BeanUtils.copyProperties(one, respBean, ignoreCol);
            //respBean.setWarehouseName(warehouseMap.get(one.getWarehouseCode()).getName());
            respBean.setStatus(SaleGoodsStatusEnum.getName(one.getStatus()));
            //一级
            Integer fCategoryId = one.getFCategoryId();
            respBean.setFCategory(fCategoryMap.get(fCategoryId).getName());
            //二级
            List<GdCategory> sCategories = sCategoryMap.get(fCategoryId);
            GdCategory sCategory = GdCategoryConvertor.getFromList(one.getSCategoryId(), sCategories);
            respBean.setSCategory(sCategory.getName());
            respBean.setCreateTime(DateUtils.convert2Str(one.getCreateTime()));
            respBean.setLastModifiedTime(DateUtils.convert2Str(one.getLastModifiedTime()));
            respBeans.add(respBean);
        }
        return new PageResult(respBeans, page.getCurrent(), page.getSize(), page.getTotal());
    }


    public static GoodsDetailRespBean convert2DetailBean(GoodsSaleInfo goods, GdCategory parent, GdCategory sub) {
        GoodsDetailRespBean respBean = new GoodsDetailRespBean();
        BeanUtils.copyProperties(goods, respBean);
        //respBean.setWarehouseName(warehouse.getName());
        respBean.setFCategory(parent.getName());
        respBean.setSCategory(sub.getName());
        return respBean;
    }

    public static List<GoodsSalePic> convert2EntityList(GoodsPicAddOrEditReqBean reqBean) {
        List<GoodsSalePic> pics = new ArrayList<>();
        for (Picture pic : reqBean.getPics()) {
            GoodsSalePic t = new GoodsSalePic();
            t.setGoodsId(reqBean.getGoodsId());
            t.setSort(pic.getSort());
            t.setSPicPath(pic.getUrl());
            pics.add(t);
        }
        return pics;
    }


    public static GoodsPicRespBean convert2Bean(String goodsId, List<GoodsSalePic> pics) {
        GoodsPicRespBean respBean = new GoodsPicRespBean();
        List<Picture> list = new ArrayList<>();
        respBean.setGoodsId(goodsId);
        for (GoodsSalePic one : pics) {
            Picture picture = new Picture();
            picture.setSort(one.getSort());
            picture.setUrl(one.getSPicPath());
            list.add(picture);
        }
        if (!CollectionUtils.isEmpty(list)) {
            respBean.setPics(list);
        } else {
            respBean.setPics(Collections.EMPTY_LIST);
        }
        return respBean;
    }

}

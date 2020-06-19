package com.yc.fresh.api.rest.outer.convertor;

import com.yc.fresh.api.rest.outer.resp.bean.SaleGdBriefRespVO;
import com.yc.fresh.api.rest.outer.resp.bean.SaleGdDetailVO;
import com.yc.fresh.api.rest.outer.resp.bean.SaleGdSearchRespBean;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.GoodsSalePic;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by quy on 2020/5/8.
 * Motto: you can do it
 */
public class SaleGoodsConvertor {


    public static SaleGdBriefRespVO convert(List<GoodsSaleInfo> saleGoods, List<GdCategory> sCategories, Map<Long, Integer> inventoryMap) {
        SaleGdBriefRespVO vo = new SaleGdBriefRespVO();
        Map<Integer, String> sCategoryMap = Collections.emptyMap();
        if (!CollectionUtils.isEmpty(sCategories)) {
            sCategoryMap = sCategories.stream().collect(Collectors.toMap(t -> t.getId(), t -> t.getName()));
        }
        Map<Integer, List<SaleGdBriefRespVO.SaleGd>> saleGdMap = saleGoods.stream().map(t->{
            SaleGdBriefRespVO.SaleGd saleGd = new SaleGdBriefRespVO.SaleGd();
            saleGd.setScId(t.getSCategoryId());
            saleGd.setGoodsId(t.getGoodsId());
            saleGd.setGoodsName(t.getGoodsName());
            saleGd.setRawPrice(t.getRawPrice());
            saleGd.setSalePrice(t.getSalePrice());
            saleGd.setDescription(t.getDescription());
            saleGd.setMPicPath(t.getMPicPath());
            Integer inventory = inventoryMap.get(t.getSkuId());
            saleGd.setStockNum(inventory == null ? 0 : inventory);
            return saleGd;
        }).collect(Collectors.groupingBy(m->m.getScId()));
        vo.setGdMap(saleGdMap);
        vo.setScMap(sCategoryMap);
        return vo;
    }

    public static SaleGdDetailVO convert(GoodsSaleInfo goodsSaleInfo, int inventory, List<GoodsSalePic> pics) {
        SaleGdDetailVO vo = new SaleGdDetailVO();
        vo.setGoodsId(goodsSaleInfo.getGoodsId());
        vo.setStockNum(inventory);
        vo.setDescrPath(goodsSaleInfo.getDescrPath());
        List<String> urls = new ArrayList<>();
        vo.setPics(urls);
        if (CollectionUtils.isEmpty(pics)) {
            return vo;
        }
        pics.sort(Comparator.comparing(GoodsSalePic::getSort));
        for (GoodsSalePic pic : pics) {
            urls.add(pic.getSPicPath());
        }
        return vo;
    }

    public static List<SaleGdSearchRespBean> convert(List<GoodsSaleInfo> goodsSaleInfos, Map<Long, Integer> inventoryMap) {
        List<SaleGdSearchRespBean> respBeans = new ArrayList<>();
        for (GoodsSaleInfo one : goodsSaleInfos) {
            SaleGdSearchRespBean respBean = new SaleGdSearchRespBean();
            respBean.setGoodsId(one.getGoodsId());
            respBean.setGoodsName(one.getGoodsName());
            respBean.setMPicPath(one.getMPicPath());
            respBean.setRawPrice(one.getRawPrice());
            respBean.setSalePrice(one.getSalePrice());
            Integer inventory = inventoryMap.get(one.getSkuId());
            respBean.setStockNum(inventory == null ? 0 : inventory);
            respBeans.add(respBean);
        }
        return respBeans;
    }
}

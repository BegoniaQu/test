package com.yc.fresh.api.rest.outer.convertor;

import com.yc.fresh.api.rest.outer.resp.bean.GdCategoryRespVO;
import com.yc.fresh.service.entity.GdCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */
public class GdCategoryConvertor {

    public static List<GdCategoryRespVO> convert2List(List<GdCategory> list) {
        list.sort(Comparator.comparing(GdCategory::getSort));
        List<GdCategoryRespVO> vos = new ArrayList<>();
        for (GdCategory f : list) {
            GdCategoryRespVO respVO = new GdCategoryRespVO();
            respVO.setId(f.getId());
            respVO.setName(f.getName());
            respVO.setPicUrl(f.getPicPath());
            respVO.setPId(f.getParentId());
            vos.add(respVO);
        }
        return vos;
    }
}

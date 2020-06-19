package com.yc.fresh.api.rest.outer.convertor;

import com.yc.fresh.api.rest.outer.req.bean.ShopCarAddReqBean;
import com.yc.fresh.api.rest.outer.resp.bean.ShopCarRespBean;
import com.yc.fresh.busi.enums.GoodsStateEnum;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.ShoppingCar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by quy on 2020/5/21.
 * Motto: you can do it
 */
public class ShopCarConvertor {


    public static ShoppingCar convert2Entity(Long userId, ShopCarAddReqBean reqBean) {
        ShoppingCar t = new ShoppingCar();
        t.setUserId(userId);
        t.setGoodsId(reqBean.getGoodsId());
        t.setNum(reqBean.getNum());
        return t;
    }

    public static List<ShopCarRespBean> convert2BeanList(List<ShoppingCar> shoppingCars, List<GoodsSaleInfo> goodsSaleInfos, Map<Long, Integer> inventoryMap) {
        List<ShopCarRespBean> respBeans = new ArrayList<>();
        Map<String, GoodsSaleInfo> goodsMap = goodsSaleInfos.stream().collect(Collectors.toMap(t -> t.getGoodsId(), t -> t));
        for (ShoppingCar shoppingCar : shoppingCars) {
            ShopCarRespBean respBean = new ShopCarRespBean();
            GoodsSaleInfo goodsSaleInfo = goodsMap.get(shoppingCar.getGoodsId());
            if (goodsSaleInfo == null) {
                continue;
            }
            respBean.setGoodsId(shoppingCar.getGoodsId());
            respBean.setNum(shoppingCar.getNum());
            respBean.setGoodsName(goodsSaleInfo.getGoodsName());
            respBean.setMPicPath(goodsSaleInfo.getMPicPath());
            respBean.setRawPrice(goodsSaleInfo.getRawPrice());
            respBean.setSalePrice(goodsSaleInfo.getSalePrice());
            //库存
            Integer inventory = inventoryMap.get(goodsSaleInfo.getSkuId());
            respBean.setStockNum(inventory == null ? 0 : inventory);
            respBeans.add(respBean);
        }
        return respBeans;
    }
}

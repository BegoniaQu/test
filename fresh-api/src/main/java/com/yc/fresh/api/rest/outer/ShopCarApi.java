package com.yc.fresh.api.rest.outer;

import com.google.common.collect.Lists;
import com.yc.fresh.api.component.UserVerifier;
import com.yc.fresh.api.rest.outer.convertor.ShopCarConvertor;
import com.yc.fresh.api.rest.outer.req.bean.ShopCarAddReqBean;
import com.yc.fresh.api.rest.outer.resp.bean.ShopCarOperationRespBean;
import com.yc.fresh.api.rest.outer.resp.bean.ShopCarRespBean;
import com.yc.fresh.busi.outer.InventoryManger;
import com.yc.fresh.busi.outer.SaleGoodsQryManager;
import com.yc.fresh.busi.outer.ShopCarManager;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.ShoppingCar;
import com.yc.fresh.service.entity.UserInfo;
import com.yc.fresh.service.enums.SaleGoodsStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2020/5/21.
 * Motto: you can do it
 */
@Api(tags = "Open-购物车")
@RestController
@RequestMapping("rest/outer/a/sc")
public class ShopCarApi {

    private final UserVerifier userVerifier;
    private final ShopCarManager shopCarManager;
    private final SaleGoodsQryManager saleGoodsQryManager;
    private final InventoryManger inventoryManger;

    public ShopCarApi(UserVerifier userVerifier, ShopCarManager shopCarManager, SaleGoodsQryManager saleGoodsQryManager, InventoryManger inventoryManger) {
        this.userVerifier = userVerifier;
        this.shopCarManager = shopCarManager;
        this.saleGoodsQryManager = saleGoodsQryManager;
        this.inventoryManger = inventoryManger;
    }


    @GetMapping("list")
    @ApiOperation(value = "我的购物车", produces = APPLICATION_JSON_VALUE,response = ShopCarRespBean.class, responseContainer = "List", httpMethod = "GET")
    public List<ShopCarRespBean> list(HttpServletRequest request) {
        UserInfo user = userVerifier.verify(request);
        List<ShoppingCar> shoppingCars = this.shopCarManager.find(user.getUserId());
        if (CollectionUtils.isEmpty(shoppingCars)) {
            return Collections.emptyList();
        }
        List<String> goodsIdList = shoppingCars.stream().map(t -> t.getGoodsId()).collect(Collectors.toList());
        List<GoodsSaleInfo> goodsSaleInfos = this.saleGoodsQryManager.findSaleGoods(goodsIdList);
        //查库存
        String wsCode = request.getParameter("wsCode");
        Assert.hasText(wsCode, "missing parameter");
        Set<Long> skuIdSet = goodsSaleInfos.stream().
                filter(t -> t.getStatus() == SaleGoodsStatusEnum.SALEABLE.getV()).
                map(t -> t.getSkuId()).
                collect(Collectors.toSet());
        Map<Long, Integer> inventoryMap = inventoryManger.findInventory(wsCode, Lists.newArrayList(skuIdSet.toArray(new Long[0])));
        return ShopCarConvertor.convert2BeanList(shoppingCars, goodsSaleInfos, inventoryMap);
    }


    @PostMapping("/fill")
    @ApiOperation(value = "购物车数量增减", produces = APPLICATION_JSON_VALUE, response = ShopCarOperationRespBean.class, httpMethod = "POST")
    public ShopCarOperationRespBean fill(@Valid @RequestBody ShopCarAddReqBean reqBean, HttpServletRequest request) {
        UserInfo user = userVerifier.verify(request);
        Assert.isTrue(reqBean.getNum() != 0, "invalid request");
        ShoppingCar t = ShopCarConvertor.convert2Entity(user.getUserId(), reqBean);
        String wsCode = request.getParameter("wsCode");
        Assert.hasText(wsCode, "missing parameter");
        int result = this.shopCarManager.populate(t, wsCode);
        ShopCarOperationRespBean respBean = new ShopCarOperationRespBean();
        respBean.setResult(result);
        return respBean;
    }

    @DeleteMapping("/{goodsId}/rm")
    @ApiOperation(value = "从购物车删除商品", produces = APPLICATION_JSON_VALUE, httpMethod = "DELETE")
    public void remove(@PathVariable String goodsId, HttpServletRequest request) {
        UserInfo user = userVerifier.verify(request);
        ShoppingCar t = new ShoppingCar();
        t.setUserId(user.getUserId());
        t.setGoodsId(goodsId);
        this.shopCarManager.remove(t);
    }

    @DeleteMapping("/cln")
    @ApiOperation(value = "清空购物车", produces = APPLICATION_JSON_VALUE, httpMethod = "DELETE")
    public void clean(HttpServletRequest request) {
        UserInfo user = userVerifier.verify(request);
        this.shopCarManager.clean(user.getUserId());
    }
}

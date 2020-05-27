package com.yc.fresh.api.rest.outer;

import com.yc.fresh.api.component.UserVerifier;
import com.yc.fresh.api.rest.inner.convertor.ShopCarConvertor;
import com.yc.fresh.api.rest.outer.req.bean.ShopCarAddReqBean;
import com.yc.fresh.api.rest.outer.resp.bean.ShopCarRespBean;
import com.yc.fresh.busi.outer.SaleGoodsQryManager;
import com.yc.fresh.busi.outer.ShopCarManager;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.entity.ShoppingCar;
import com.yc.fresh.service.entity.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.Collections;
import java.util.List;
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

    public ShopCarApi(UserVerifier userVerifier, ShopCarManager shopCarManager, SaleGoodsQryManager saleGoodsQryManager) {
        this.userVerifier = userVerifier;
        this.shopCarManager = shopCarManager;
        this.saleGoodsQryManager = saleGoodsQryManager;
    }


    @GetMapping("list")
    @ApiOperation(value = "我的购物车", produces = APPLICATION_JSON_VALUE, httpMethod = "GET")
    public List<ShopCarRespBean> list(HttpServletRequest request) {
        UserInfo user = userVerifier.verify(request);
        List<ShoppingCar> shoppingCars = this.shopCarManager.find(user.getUserId());
        if (CollectionUtils.isEmpty(shoppingCars)) {
            return Collections.emptyList();
        }
        List<String> goodsIdList = shoppingCars.stream().map(t -> t.getGoodsId()).collect(Collectors.toList());
        List<GoodsSaleInfo> goodsSaleInfos = this.saleGoodsQryManager.findSaleGoods(goodsIdList);
        return ShopCarConvertor.convert2BeanList(shoppingCars, goodsSaleInfos);
    }


    @PostMapping("/fill")
    @ApiOperation(value = "加入购物车", produces = APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void populate(@Valid @RequestBody ShopCarAddReqBean reqBean, HttpServletRequest request) {
        UserInfo user = userVerifier.verify(request);
        Assert.isTrue(reqBean.getNum() != 0, "invalid request");
        ShoppingCar t = ShopCarConvertor.convert2Entity(user.getUserId(), reqBean);
        this.shopCarManager.populate(t);
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

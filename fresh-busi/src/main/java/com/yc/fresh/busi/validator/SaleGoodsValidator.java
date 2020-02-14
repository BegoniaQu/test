package com.yc.fresh.busi.validator;

import com.yc.fresh.service.IGoodsSaleInfoService;
import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.enums.SaleGoodsStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by quy on 2019/12/10.
 * Motto: you can do it
 */
@Component
public class SaleGoodsValidator implements Validator {

    private final IGoodsSaleInfoService goodsSaleInfoService;

    @Autowired
    public SaleGoodsValidator(IGoodsSaleInfoService goodsSaleInfoService) {
        this.goodsSaleInfoService = goodsSaleInfoService;
    }

    public GoodsSaleInfo validate(String goodsId) {
        Assert.hasText(goodsId, getErrorMsg("null(goodsId) found"));
        GoodsSaleInfo goods = goodsSaleInfoService.getById(goodsId);
        Assert.notNull(goods, getErrorMsg("unknown(goodsId)"));
        Assert.isTrue(goods.getStatus() != SaleGoodsStatusEnum.INVALID.getV(), getErrorMsg("invalid(goodsId)"));
        return goods;
    }


}

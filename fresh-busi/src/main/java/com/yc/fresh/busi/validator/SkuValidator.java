package com.yc.fresh.busi.validator;

import com.yc.fresh.service.ISkuInfoService;
import com.yc.fresh.service.entity.SkuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by quy on 2019/12/1.
 * Motto: you can do it
 */
@Component
public class SkuValidator implements Validator{

    private final ISkuInfoService skuInfoService;

    @Autowired
    public SkuValidator(ISkuInfoService skuInfoService) {
        this.skuInfoService = skuInfoService;
    }

    public SkuInfo validateSkuId(Long skuId) {
        Assert.notNull(skuId, nullMsg("skuId"));
        SkuInfo skuInfo = skuInfoService.getById(skuId);
        Assert.notNull(skuId, unknownMsg("skuId"));
        return skuInfo;
    }
}

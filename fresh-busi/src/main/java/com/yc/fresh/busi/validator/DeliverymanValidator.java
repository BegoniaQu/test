package com.yc.fresh.busi.validator;

import com.yc.fresh.service.IDeliverymanInfoService;
import com.yc.fresh.service.entity.DeliverymanInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * Created by quy on 2020/4/10.
 * Motto: you can do it
 */
@Component
public class DeliverymanValidator implements Validator{


    private final IDeliverymanInfoService deliverymanInfoService;

    public DeliverymanValidator(IDeliverymanInfoService deliverymanInfoService) {
        this.deliverymanInfoService = deliverymanInfoService;
    }

    /**
     * @param dmId
     * @return
     */
    public DeliverymanInfo validate(Long dmId, Set<Integer> statusSet) {
        DeliverymanInfo one = this.deliverymanInfoService.getById(dmId);
        Assert.notNull(one, nullMsg("deliverymanId"));
        if (!CollectionUtils.isEmpty(statusSet)) {
            Assert.isTrue(statusSet.contains(one.getStatus()), "无效操作");
        }
        return one;
    }
}

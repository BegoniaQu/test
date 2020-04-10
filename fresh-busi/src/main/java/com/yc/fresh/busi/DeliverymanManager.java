package com.yc.fresh.busi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.fresh.busi.validator.DeliverymanValidator;
import com.yc.fresh.busi.validator.WarehouseValidator;
import com.yc.fresh.common.ServiceAssert;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.IDeliverymanInfoService;
import com.yc.fresh.service.IWarehouseDeliveryService;
import com.yc.fresh.service.dto.DmBindingDTO;
import com.yc.fresh.service.entity.DeliverymanInfo;
import com.yc.fresh.service.entity.WarehouseDelivery;
import com.yc.fresh.service.enums.DeliverymanStatusEnum;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by quy on 2020/4/10.
 * Motto: you can do it
 */

@Component
public class DeliverymanManager {

    private final IDeliverymanInfoService deliverymanInfoService;
    private final DeliverymanValidator deliverymanValidator;
    private final IWarehouseDeliveryService warehouseDeliveryService;
    private final WarehouseValidator warehouseValidator;

    public DeliverymanManager(IDeliverymanInfoService deliverymanInfoService, DeliverymanValidator deliverymanValidator, IWarehouseDeliveryService warehouseDeliveryService, WarehouseValidator warehouseValidator) {
        this.deliverymanInfoService = deliverymanInfoService;
        this.deliverymanValidator = deliverymanValidator;
        this.warehouseDeliveryService = warehouseDeliveryService;
        this.warehouseValidator = warehouseValidator;
    }


    public void doAdd(DeliverymanInfo t) {
        boolean flag = deliverymanInfoService.save(t);
        ServiceAssert.isOk(flag, "add deliveryman failed");
    }

    @Transactional(rollbackFor = Exception.class)
    public void doAdd(DeliverymanInfo t, String warehouseCode) {
        warehouseValidator.validateWarehouseCode(warehouseCode);
        boolean flag = deliverymanInfoService.save(t);
        ServiceAssert.isOk(flag, "add deliveryman failed");
        //绑定仓库
        WarehouseDelivery wd = new WarehouseDelivery();
        wd.setDmId(t.getDmId());
        wd.setDmName(t.getDmName());
        wd.setWarehouseCode(warehouseCode);
        flag = warehouseDeliveryService.save(wd);
        ServiceAssert.isOk(flag, "bind deliveryman to warehouse failed");
    }


    @Transactional(rollbackFor = Exception.class)
    public void bindWarehouse(Long dmId, String warehouseCode) {
        warehouseValidator.validateWarehouseCode(warehouseCode);
        DeliverymanInfo dm = deliverymanValidator.validate(dmId, null);
        //
        WarehouseDelivery wd = new WarehouseDelivery();
        wd.setDmId(dmId);
        wd.setDmName(dm.getDmName());
        wd.setWarehouseCode(warehouseCode);
        boolean flag = warehouseDeliveryService.save(wd);
        ServiceAssert.isOk(flag, "bind deliveryman to warehouse failed");
    }


    public void unbindWarehouse(Integer bindId) {
        warehouseDeliveryService.removeById(bindId);
    }


    @Transactional(rollbackFor = Exception.class)
    public void updateMobile(Long dmId, String mobile) {
        DeliverymanInfo one = deliverymanValidator.validate(dmId, null);
        Assert.isTrue(!one.getMobile().equals(mobile), "检测到手机号码未改动");
        UpdateWrapper<DeliverymanInfo> uw = Wrappers.update();
        uw.set(DeliverymanInfo.LAST_MODIFIED_TIME, DateUtils.getCurrentDate());
        uw.set(DeliverymanInfo.MOBILE, mobile);
        uw.eq(DeliverymanInfo.DM_ID, dmId);
        boolean flag = deliverymanInfoService.update(uw);
        ServiceAssert.isOk(flag, "update deliveryman mobile failed");
    }



    @Transactional(readOnly = true)
    public IPage<DmBindingDTO> doListBinding(String warehouseCode, String dmName, int pn, int ps) {
        Map<String, Object> qryMap = new HashMap<>();
        qryMap.put("pn", pn);
        qryMap.put("ps", ps);
        qryMap.put("warehouseCode", StringUtils.isEmpty(warehouseCode) ? null : warehouseCode);
        qryMap.put("dmName", StringUtils.isEmpty(dmName) ? null : dmName);
        return this.warehouseDeliveryService.doListBinding(qryMap);
    }


    public List<DeliverymanInfo> findDm() {
        QueryWrapper<DeliverymanInfo> queryWrapper = Wrappers.query();
        queryWrapper.eq(DeliverymanInfo.STATUS, DeliverymanStatusEnum.ok.getV());
        return this.deliverymanInfoService.list(queryWrapper);
    }



    /*@Transactional(rollbackFor = Exception.class)
    public void doDismiss(Long dmId) {
        Set<Integer> statusSet = composeProperStatus();
        deliverymanValidator.validate(dmId, statusSet);
        UpdateWrapper<DeliverymanInfo> uw = Wrappers.update();
        uw.set(DeliverymanInfo.LAST_MODIFIED_TIME, DateUtils.getCurrentDate());
        uw.set(DeliverymanInfo.STATUS, DeliverymanStatusEnum.gone.getV());
        uw.eq(DeliverymanInfo.DM_ID, dmId);
        uw.in(DeliverymanInfo.STATUS, statusSet);
        boolean flag = deliverymanInfoService.update(uw);
        ServiceAssert.isOk(flag, "dismiss deliveryman failed");
    }*/
}

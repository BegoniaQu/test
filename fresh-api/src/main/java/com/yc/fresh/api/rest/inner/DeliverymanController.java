package com.yc.fresh.api.rest.inner;

import com.yc.fresh.api.rest.inner.convertor.DeliverymanConvertor;
import com.yc.fresh.api.rest.inner.req.bean.DeliverymanAddReqBean;
import com.yc.fresh.api.rest.inner.req.bean.DeliverymanBindReqBean;
import com.yc.fresh.api.rest.inner.req.bean.DeliverymanMobileEditReqBean;
import com.yc.fresh.api.rest.inner.req.bean.DeliverymanUnBindReqBean;
import com.yc.fresh.busi.DeliverymanManager;
import com.yc.fresh.service.entity.DeliverymanInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2020/4/10.
 * Motto: you can do it
 * 初始版
 */
@RestController
@RequestMapping("/rest/inner/dm")
@Api(description = "配送人员管理")
public class DeliverymanController {


    private final DeliverymanManager deliverymanManager;

    public DeliverymanController(DeliverymanManager deliverymanManager) {
        this.deliverymanManager = deliverymanManager;
    }

    @PostMapping(value = "/add")
    @ApiOperation(value="新增配送人员", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void add(@Valid @RequestBody DeliverymanAddReqBean reqBean) {
        DeliverymanInfo one = DeliverymanConvertor.convert2Entity(reqBean);
        deliverymanManager.doAdd(one, reqBean.getWarehouseCode());
    }

    @PostMapping(value = "/mb/edit")
    @ApiOperation(value="修改手机号", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void edit(@Valid @RequestBody DeliverymanMobileEditReqBean reqBean) {
        deliverymanManager.updateMobile(reqBean.getDmId(), reqBean.getMobile());
    }

    @PostMapping(value = "/bind")
    @ApiOperation(value="新增绑定关系", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void addBinding(@Valid @RequestBody DeliverymanBindReqBean reqBean) {
        deliverymanManager.bindWarehouse(reqBean.getDmId(), reqBean.getWarehouseCode());
    }

    @PostMapping(value = "/unbind")
    @ApiOperation(value="解除绑定关系", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void cancelBinding(@Valid @RequestBody DeliverymanUnBindReqBean reqBean) {
        deliverymanManager.unbindWarehouse(reqBean.getBindId());
    }

}

package com.yc.fresh.api.rest.inner;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yc.fresh.api.rest.inner.convertor.DeliverymanConvertor;
import com.yc.fresh.api.rest.inner.req.bean.*;
import com.yc.fresh.api.rest.inner.resp.bean.DeliverymanBriefRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.DmBindingPageRespBean;
import com.yc.fresh.busi.DeliverymanManager;
import com.yc.fresh.common.PageResult;
import com.yc.fresh.service.dto.DmBindingDTO;
import com.yc.fresh.service.entity.DeliverymanInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2020/4/10.
 * Motto: you can do it
 * 初始版
 */
@RestController
@RequestMapping("/rest/inner/dm")
@Api(tags = "Admin-配送人员管理")
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


    @GetMapping("/binding/list")
    @ApiOperation(value="配送人员绑定列表", produces=APPLICATION_JSON_VALUE, response = DmBindingPageRespBean.class, httpMethod = "GET")
    public PageResult<DmBindingPageRespBean> listBinding(DmBindingPageQryBean qryBean) {
        IPage<DmBindingDTO> pge =  deliverymanManager.doListBinding(qryBean.getWarehouseCode(), qryBean.getDmName(), qryBean.getPn(), qryBean.getPs());
        return DeliverymanConvertor.convert2PageBean(pge);
    }

    @GetMapping("/brief/list")
    @ApiOperation(value="配送人员简要列表", produces=APPLICATION_JSON_VALUE, responseContainer = "List", response = DeliverymanBriefRespBean.class, httpMethod = "GET")
    public List<DeliverymanBriefRespBean> findDeliveryman() {
        List<DeliverymanInfo> dms = deliverymanManager.findDm();
        return DeliverymanConvertor.convert2BeanList(dms);
    }

}

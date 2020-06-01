package com.yc.fresh.api.rest.outer;

import com.yc.fresh.api.rest.outer.convertor.WarehouseConvertor;
import com.yc.fresh.api.rest.outer.resp.bean.WhInfoRespBean;
import com.yc.fresh.busi.outer.WarehouseQryManager;
import com.yc.fresh.service.entity.Warehouse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2020/5/27.
 * Motto: you can do it
 */
@Api(tags = "Open-配货仓库")
@RestController
@RequestMapping("rest/outer/n/wh")
public class WarehouseApi {

    private final WarehouseQryManager warehouseQryManager;

    public WarehouseApi(WarehouseQryManager warehouseQryManager) {
        this.warehouseQryManager = warehouseQryManager;
    }

    @GetMapping("/list")
    @ApiOperation(value="门店信息", produces=APPLICATION_JSON_VALUE, responseContainer = "List", response = WhInfoRespBean.class, httpMethod = "GET")
    public List<WhInfoRespBean> findWarehouse() {
        List<Warehouse> warehouseList = warehouseQryManager.findValid();
        return WarehouseConvertor.convert2BeanList(warehouseList);
    }

}

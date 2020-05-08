package com.yc.fresh.api.rest.inner;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.fresh.api.rest.inner.convertor.WarehouseConvertor;
import com.yc.fresh.api.rest.inner.req.bean.WarehouseAddReqBean;
import com.yc.fresh.api.rest.inner.req.bean.WarehouseCloseReqBean;
import com.yc.fresh.api.rest.inner.req.bean.WarehouseEditReqBean;
import com.yc.fresh.api.rest.inner.req.bean.WarehousePageQryBean;
import com.yc.fresh.api.rest.inner.resp.bean.WarehouseBriefRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.WarehouseDetailRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.WarehousePageRespBean;
import com.yc.fresh.busi.WarehouseManager;
import com.yc.fresh.common.PageResult;
import com.yc.fresh.service.entity.Warehouse;
import com.yc.fresh.service.enums.WarehouseStatusEnum;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2019/11/25.
 * Motto: you can do it
 */
@RestController
@RequestMapping("/rest/inner/warehouse")
@Api(tags = "Admin-仓库管理")
public class WarehouseController {

    private final WarehouseManager warehouseManager;

    @Autowired
    public WarehouseController(WarehouseManager warehouseManager) {
        this.warehouseManager = warehouseManager;
    }

    @PostMapping(value = "/add")
    @ApiOperation(value="增加仓库", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void add(@Valid @RequestBody WarehouseAddReqBean reqBean) {
        Warehouse warehouse = WarehouseConvertor.convert2Entity(reqBean);
        warehouseManager.doAdd(warehouse);
    }

    @GetMapping("/{code}/detail")
    @ApiOperation(value="查看仓库详细信息", produces=APPLICATION_JSON_VALUE, httpMethod = "GET")
    public WarehouseDetailRespBean getByCode(@ApiParam("仓库编码") @NotNull @PathVariable String code) {
        Warehouse warehouse = warehouseManager.getByCode(code);
        return WarehouseConvertor.convert2Bean(warehouse);
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value="编辑仓库", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void edit(@Valid @RequestBody WarehouseEditReqBean reqBean) {
        Warehouse warehouse = WarehouseConvertor.convert2Entity(reqBean);
        warehouseManager.doUpdateByCode(warehouse);
    }

    @PostMapping(value = "/close")
    @ApiOperation(value="关闭仓库", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void close(@Valid @RequestBody WarehouseCloseReqBean reqBean) {
        warehouseManager.doClose(reqBean.getCode());
    }


    @GetMapping("/list")
    @ApiOperation(value="仓库列表", produces=APPLICATION_JSON_VALUE, response = WarehousePageRespBean.class, httpMethod = "GET")
    public PageResult<WarehousePageRespBean> list(WarehousePageQryBean qryBean) {
        IPage<Warehouse> iPage = new Page<>(qryBean.getPn(), qryBean.getPs());
        IPage<Warehouse> page = warehouseManager.page(qryBean.getName(), qryBean.getCity(), qryBean.getStatus(), iPage);
        return WarehouseConvertor.convert2PageBean(page);
    }

    @GetMapping("/brief/list")
    @ApiOperation(value="仓库简要信息列表(下拉列表用)", produces=APPLICATION_JSON_VALUE, responseContainer = "List", response = WarehouseBriefRespBean.class, httpMethod = "GET")
    public List<WarehouseBriefRespBean> list() {
        List<Warehouse> warehouses = warehouseManager.query(WarehouseStatusEnum.AVAILABLE.getV());
        return WarehouseConvertor.convert2Bean(warehouses);
    }

}

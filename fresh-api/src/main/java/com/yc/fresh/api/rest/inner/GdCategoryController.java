package com.yc.fresh.api.rest.inner;

import com.yc.fresh.api.rest.inner.convertor.GdCategoryConvertor;
import com.yc.fresh.api.rest.inner.req.bean.GdCategoryAddReqBean;
import com.yc.fresh.api.rest.inner.req.bean.GdCategoryDelReqBean;
import com.yc.fresh.api.rest.inner.req.bean.GdCategoryEditReqBean;
import com.yc.fresh.api.rest.inner.resp.bean.GdCategoryBriefRespBean;
import com.yc.fresh.api.rest.inner.resp.bean.GdCategoryRespBean;
import com.yc.fresh.busi.GdCategoryManager;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.enums.GdCategoryStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2019/11/22.
 * Motto: you can do it
 */
@RestController
@RequestMapping("/rest/inner/category")
@Api(description = "分类管理")
public class GdCategoryController {

    private final GdCategoryManager gdCategoryManager;

    @Autowired
    public GdCategoryController(GdCategoryManager gdCategoryManager) {
        this.gdCategoryManager = gdCategoryManager;
    }


    @PostMapping(value = "/add")
    @ApiOperation(value="增加分类", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void add(@Valid @RequestBody GdCategoryAddReqBean reqBean) {
        GdCategory t = GdCategoryConvertor.convert2Entity(reqBean);
        gdCategoryManager.doAdd(t);
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value="编辑分类", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void edit(@Valid @RequestBody GdCategoryEditReqBean reqBean) {
        GdCategory gdCategory = GdCategoryConvertor.convert2Entity(reqBean);
        this.gdCategoryManager.doUpdate(gdCategory);
    }

    @PostMapping("/del")
    @ApiOperation(value="废弃分类", produces=APPLICATION_JSON_VALUE, httpMethod = "POST")
    public void del(@Valid @RequestBody GdCategoryDelReqBean reqBean) {
        gdCategoryManager.doDel(reqBean.getCategoryId());
    }


    @GetMapping("/{parentId}/list")
    @ApiOperation(value="根据父类查子类", produces=APPLICATION_JSON_VALUE, responseContainer = "List", response = GdCategoryBriefRespBean.class, httpMethod = "GET")
    public List<GdCategoryBriefRespBean> findByParentId(@ApiParam("父分类ID") @PathVariable @NotNull Integer parentId,
                                                   @ApiParam("状态[0-废弃,1-可用]") @RequestParam Integer status) {
        List<GdCategory> gdCategories = this.gdCategoryManager.query(parentId, status);
        return GdCategoryConvertor.convert2BriefBeanList(gdCategories);
    }

    @GetMapping("/first/list")
    @ApiOperation(value="查询一级分类列表", produces=APPLICATION_JSON_VALUE, responseContainer = "List", response = GdCategoryRespBean.class, httpMethod = "GET")
    public List<GdCategoryRespBean> findFirst() {
        List<GdCategory> gdCategories = this.gdCategoryManager.query(0, GdCategoryStatusEnum.AVAILABLE.getV());
        return GdCategoryConvertor.convert2BeanList(gdCategories);
    }
}

package com.yc.fresh.api.rest.outer;

import com.yc.fresh.api.rest.outer.convertor.GdCategoryConvertor;
import com.yc.fresh.api.rest.outer.resp.bean.GdCategoryRespVO;
import com.yc.fresh.busi.outer.GdCategoryQryManager;
import com.yc.fresh.service.entity.GdCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */
@RestController
@RequestMapping("/rest/outer/n/gc")
@Api(description = "商品分类")
public class GdCategoryApi {

    private final GdCategoryQryManager gdCategoryQryManager;

    public GdCategoryApi(GdCategoryQryManager gdCategoryQryManager) {
        this.gdCategoryQryManager = gdCategoryQryManager;
    }

    @GetMapping("/f/list")
    @ApiOperation(value="查询一级分类列表", produces=APPLICATION_JSON_VALUE, responseContainer = "List", response = GdCategoryRespVO.class, httpMethod = "GET")
    public List<GdCategoryRespVO> findFList() {
        List<GdCategory> gdCategories = this.gdCategoryQryManager.findValidCategory(0);
        //List<Integer> fIds = gdCategories.stream().map(t -> t.getId()).collect(Collectors.toList());
        return GdCategoryConvertor.convert2List(gdCategories);
    }

    @GetMapping("/{fId}/s/list")
    @ApiOperation(value="查询二级分类列表", produces=APPLICATION_JSON_VALUE, responseContainer = "List", response = GdCategoryRespVO.class, httpMethod = "GET")
    public List<GdCategoryRespVO> findSList(@ApiParam("一级ID") @NotBlank @PathVariable("fId") Integer fId) {
        List<GdCategory> gdCategories = this.gdCategoryQryManager.findValidCategory(fId);
        return GdCategoryConvertor.convert2List(gdCategories);
    }
}

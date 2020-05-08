package com.yc.fresh.api.rest.inner;

import com.yc.fresh.busi.GdCategoryManager;
import com.yc.fresh.busi.cache.GdCategoryCacheService;
import com.yc.fresh.service.entity.GdCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by quy on 2020/5/8.
 * Motto: you can do it
 */
@RestController
@RequestMapping("/rest/inner/expt")
@Api(tags = "Admin-异常处理")
public class ExceptionController {

    private final GdCategoryCacheService gdCategoryCacheService;
    private final GdCategoryManager gdCategoryManager;

    public ExceptionController(GdCategoryCacheService gdCategoryCacheService, GdCategoryManager gdCategoryManager) {
        this.gdCategoryCacheService = gdCategoryCacheService;
        this.gdCategoryManager = gdCategoryManager;
    }

    @GetMapping("/category/{id}/cache")
    @ApiOperation(value="指定分类加入缓存", produces=APPLICATION_JSON_VALUE,  httpMethod = "GET")
    public void addCategoryToCache(@PathVariable Integer id) {
        GdCategory gdCategory = gdCategoryManager.getById(id);
        gdCategoryCacheService.add(gdCategory);
    }
}

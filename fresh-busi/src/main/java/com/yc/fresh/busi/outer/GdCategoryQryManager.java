package com.yc.fresh.busi.outer;

import com.yc.fresh.busi.cache.GdCategoryCacheService;
import com.yc.fresh.service.entity.GdCategory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */
@Component
public class GdCategoryQryManager {

    private final GdCategoryCacheService gdCategoryCacheService;

    public GdCategoryQryManager(GdCategoryCacheService gdCategoryCacheService) {
        this.gdCategoryCacheService = gdCategoryCacheService;
    }

    public List<GdCategory> findValidCategory(Integer parentId) {
        return gdCategoryCacheService.findCategory(parentId);
    }

}

package com.yc.fresh.busi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yc.fresh.busi.validator.GdCategoryValidator;
import com.yc.fresh.common.ServiceAssert;
import com.yc.fresh.service.IGdCategoryService;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.enums.GdCategoryStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by quy on 2019/11/22.
 * Motto: you can do it
 */
@Component
public class GdCategoryManager {

    private final IGdCategoryService gdCategoryService;
    private final GdCategoryValidator gdCategoryValidator;

    @Autowired
    public GdCategoryManager(IGdCategoryService gdCategoryService, GdCategoryValidator gdCategoryValidator) {
        this.gdCategoryService = gdCategoryService;
        this.gdCategoryValidator = gdCategoryValidator;
    }



    @Transactional(rollbackFor = Exception.class)
    public void doAdd(GdCategory t) {
        Integer parentId = t.getParentId();
        if (parentId != null && parentId > 0) {
            gdCategoryValidator.validateId(parentId);
        }
        boolean isOk = this.gdCategoryService.save(t);
        ServiceAssert.isOk(isOk, "add category failed");
    }

    @Transactional(rollbackFor = Exception.class)
    public void doUpdate(GdCategory t) {
        Integer parentId = t.getParentId();
        if (parentId != null) {
            gdCategoryValidator.validateId(parentId);
        }
        boolean isOk = this.gdCategoryService.updateById(t);
        ServiceAssert.isOk(isOk, "update category failed");
    }


    @Transactional(rollbackFor = Exception.class)
    public void doDel(Integer categoryId) {
        UpdateWrapper<GdCategory> wrapper = new UpdateWrapper<>();
        wrapper.set(GdCategory.STATUS, GdCategoryStatusEnum.INVALID.getV());
        wrapper.eq(GdCategory.ID, categoryId);
        wrapper.eq(GdCategory.STATUS, GdCategoryStatusEnum.AVAILABLE.getV());
        boolean isOk = this.gdCategoryService.update(wrapper);
        ServiceAssert.isOk(isOk, "revoke category failed");
    }

    public List<GdCategory> query(Integer parentId, Integer status) {
        Assert.notNull(parentId, "null(parentId) found");
        QueryWrapper<GdCategory> qryWrapper = new QueryWrapper<>();
        if (status != null) {
            GdCategoryStatusEnum.verify(status);
            qryWrapper.eq(GdCategory.STATUS, status);
        }
        qryWrapper.eq(GdCategory.PARENT_ID, parentId);
        return this.gdCategoryService.list(qryWrapper);
    }

    public Map<Integer, List<GdCategory>> querySubs(List<Integer> parentIds) {
        QueryWrapper<GdCategory> qryWrapper = new QueryWrapper<>();
        qryWrapper.in(GdCategory.PARENT_ID, parentIds);
        List<GdCategory> list = this.gdCategoryService.list(qryWrapper);
        return list.stream().collect(Collectors.groupingBy(t->t.getParentId()));
    }

    public GdCategory getById(Integer id) {
        return this.gdCategoryService.getById(id);
    }
}

package com.yc.fresh.busi.validator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.fresh.service.IGdCategoryService;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.enums.GdCategoryStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by quy on 2019/11/25.
 * Motto: you can do it
 */
@Component
public class GdCategoryValidator implements Validator{

    private final IGdCategoryService gdCategoryService;

    @Autowired
    public GdCategoryValidator(IGdCategoryService gdCategoryService) {
        this.gdCategoryService = gdCategoryService;
    }


    /**
     * 验证ID
     * @param categoryId
     * @return
     */
    public GdCategory validateId(Integer categoryId) {
        Assert.notNull(categoryId, getErrorMsg("null(categoryId) found"));
        GdCategory gdCategory = this.gdCategoryService.getById(categoryId);
        Assert.notNull(gdCategory, getErrorMsg("unknown(categoryId)"));
        Assert.isTrue(gdCategory.getStatus() == GdCategoryStatusEnum.AVAILABLE.getV(), getErrorMsg("invalid(categoryId)"));
        return gdCategory;
    }

    public GdCategory validate(Integer fCategoryId, Integer sCategoryId) {
        Assert.notNull(fCategoryId, getErrorMsg("null(categoryId) found"));
        Assert.notNull(sCategoryId, getErrorMsg("null(categoryId) found"));
        QueryWrapper<GdCategory> queryWrapper = Wrappers.query();
        queryWrapper.eq(GdCategory.ID, sCategoryId);
        queryWrapper.eq(GdCategory.PARENT_ID, fCategoryId);
        queryWrapper.eq(GdCategory.STATUS, GdCategoryStatusEnum.AVAILABLE.getV());
        GdCategory one = this.gdCategoryService.getOne(queryWrapper);
        Assert.notNull(one, getErrorMsg("illegal request"));
        return one;
    }


    /*public Map<Integer, GdCategory> validateSubId(Integer parentId) {

    }

       public  validateId() {
        Assert.notNull(fCategoryId, getErrorMsg("null(categoryId) found"));
        QueryWrapper<GdCategory> queryWrapper = Wrappers.query();
        queryWrapper.eq(GdCategory.PARENT_ID, fCategoryId);
        queryWrapper.eq(GdCategory.STATUS, GdCategoryStatusEnum.AVAILABLE.getV());
        List<GdCategory> list = this.gdCategoryService.list(queryWrapper);
        Assert.notEmpty(list, getErrorMsg("unknown(categoryId)"));
        return list.stream().collect(Collectors.toMap(t->t.getId(), t->t));
    }

*/

}

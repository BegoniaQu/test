package com.yc.fresh.busi.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.fresh.busi.cache.key.RedisKeyUtils;
import com.yc.fresh.common.cache.service.impl.AbstractCacheServiceImpl;
import com.yc.fresh.common.cache.template.RedissonTemplate;
import com.yc.fresh.service.IGdCategoryService;
import com.yc.fresh.service.entity.GdCategory;
import com.yc.fresh.service.enums.GdCategoryStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */
@Component
public class GdCategoryCacheService extends AbstractCacheServiceImpl<GdCategory> {

    @Autowired
    public GdCategoryCacheService(RedissonTemplate redissonTemplate, IGdCategoryService gdCategoryService) {
        super(redissonTemplate, gdCategoryService);
        //super.defaultLiveSecond =
    }

    public List<GdCategory> findCategory(Integer parentId) {
        int pid = parentId == null ? 0 : parentId;
        String key = this.keyPrefix + pid;
        List<GdCategory> list = this.redissonTemplate.findList(key);
        if (CollectionUtils.isEmpty(list)) {
            QueryWrapper<GdCategory> qryWrapper = new QueryWrapper<>();
            qryWrapper.eq(GdCategory.STATUS, GdCategoryStatusEnum.AVAILABLE.getV());
            qryWrapper.eq(GdCategory.PARENT_ID, pid);
            list =  this.dbService.list(qryWrapper);
        }
        if (!CollectionUtils.isEmpty(list)) {
            redissonTemplate.addList(key, list, defaultLiveSecond);
        }
        return list;
    }


    public void listAdd(GdCategory t) {
        String key = RedisKeyUtils.getFirstCategory2List(t.getParentId());
        this.listAdd(key, t);
    }


    public void listDel(GdCategory t, Integer oldParentId) {
        Integer newParentId = t.getParentId();
        String key;
        if (newParentId == oldParentId.intValue()) { //父分类没有变
            key = RedisKeyUtils.getFirstCategory2List(t.getParentId());
        } else { //父分类改变
            key = RedisKeyUtils.getFirstCategory2List(oldParentId);
        }
        this.listDel(key);
    }


    public void listDel(Integer parentId) {
        String key = RedisKeyUtils.getFirstCategory2List(parentId);
        this.listDel(key);
    }
}

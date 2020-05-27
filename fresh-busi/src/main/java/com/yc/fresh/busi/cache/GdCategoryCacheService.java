package com.yc.fresh.busi.cache;

import com.yc.fresh.busi.cache.key.RedisKeyUtils;
import com.yc.fresh.common.cache.service.impl.AbstractCacheServiceImpl;
import com.yc.fresh.common.cache.template.RedisTemplate;
import com.yc.fresh.service.IGdCategoryService;
import com.yc.fresh.service.entity.GdCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */
@Component
public class GdCategoryCacheService extends AbstractCacheServiceImpl<GdCategory, Integer> {

    private static final long categoryLiveSecond = 86400*30;

    @Autowired
    public GdCategoryCacheService(RedisTemplate redisTemplate, IGdCategoryService gdCategoryService) {
        super(redisTemplate, gdCategoryService);
        //super.defaultLiveSecond =
    }

    public List<GdCategory> findCategory(Integer parentId) {
        int pid = parentId == null ? 0 : parentId;
        String key = RedisKeyUtils.getFirstCategory2List(pid);
        return this.fromMap(key, 30);
    }



    public void add(GdCategory t) {
        //super.add(t, categoryLiveSecond);
        String key = RedisKeyUtils.getFirstCategory2List(t.getParentId());
        this.mapPut(key, t, 0);
    }

    public void update(GdCategory t) {
        String key = RedisKeyUtils.getFirstCategory2List(t.getParentId());
        this.mapUpt(key, t);
    }


    public void del(GdCategory t) {
        String key = RedisKeyUtils.getFirstCategory2List(t.getParentId());
        this.mapRmv(key, String.valueOf(t.getId()));
        if (t.getParentId() == 0) { //本身是parent
            String parent2sonKey = RedisKeyUtils.getFirstCategory2List(t.getId());
            this.mapDel(parent2sonKey);
        }
    }


   /* public void listAdd(GdCategory t) {
        String key = RedisKeyUtils.getFirstCategory2List(t.getParentId());
        this.listAdd(key, t);
    }


    public void listDel(Integer parentId) {
        String key = RedisKeyUtils.getFirstCategory2List(parentId);
        this.listDel(key);
    }*/
}

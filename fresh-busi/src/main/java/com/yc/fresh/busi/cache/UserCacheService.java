package com.yc.fresh.busi.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.fresh.common.cache.service.impl.AbstractCacheServiceImpl;
import com.yc.fresh.common.cache.template.RedissonTemplate;
import com.yc.fresh.service.IUserInfoService;
import com.yc.fresh.service.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */
@Component
public class UserCacheService extends AbstractCacheServiceImpl<UserInfo, Long> {


    @Autowired
    public UserCacheService(RedissonTemplate redissonTemplate, IUserInfoService userInfoIService) {
        super(redissonTemplate, userInfoIService);
    }


    public UserInfo getByOpenId(String openid) {
        String key = "openid:" + openid;
        UserInfo u = this.redissonTemplate.getEntity(key, null);
        if (u == null) { //cache 未命中
            QueryWrapper<UserInfo> queryWrapper = Wrappers.query();
            queryWrapper.eq(UserInfo.WX_OPEN_ID, openid);
            u = this.dbService.getOne(queryWrapper);
        }
        if (u != null) {
            this.redissonTemplate.set(key, u, defaultLiveSecond);
        }
        return u;
    }

}

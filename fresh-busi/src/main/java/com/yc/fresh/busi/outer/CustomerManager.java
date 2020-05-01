package com.yc.fresh.busi.outer;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.fresh.busi.cache.UserCacheService;
import com.yc.fresh.busi.validator.CustomerValidator;
import com.yc.fresh.common.exception.SCApiRuntimeException;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.service.IUserInfoService;
import com.yc.fresh.service.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by quy on 2019/11/28.
 * Motto: you can do it
 */
@Component
@Slf4j
public class CustomerManager {

    private final IUserInfoService userInfoService;
    private final CustomerValidator customerValidator;
    private final UserCacheService userCacheService;


    @Autowired
    public CustomerManager(IUserInfoService userInfoService, CustomerValidator customerValidator, UserCacheService userCacheService) {
        this.userInfoService = userInfoService;
        this.customerValidator = customerValidator;
        this.userCacheService = userCacheService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void doRegister(UserInfo t) {
        customerValidator.validateMobileForRegister(t.getMobile());
        boolean isOk = userInfoService.save(t);
        if (!isOk) {
            throw new SCApiRuntimeException();
        }
    }

    public UserInfo get(String openid) {
       return this.userCacheService.getByOpenId(openid);
    }

    public UserInfo get(Long userId) {
        return this.userCacheService.getByPid(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTk(Long userId, String tk) {
        LocalDateTime curdate = DateUtils.getCurrentDate();
        UpdateWrapper<UserInfo> wrapper = Wrappers.update();
        wrapper.set(UserInfo.TK, tk);
        wrapper.set(UserInfo.LAST_MODIFIED_TIME, curdate);
        //wrapper.set(UserInfo.TK_EXPIRED_TIME, curdate.plusMinutes(30));
        wrapper.eq(UserInfo.USER_ID, userId);
        boolean isOk = this.userInfoService.update(wrapper);
        if (!isOk) {
            throw new SCApiRuntimeException();
        }
        this.userCacheService.removeByPid(userId);
    }
}

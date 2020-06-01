package com.yc.fresh.busi.outer;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.fresh.busi.cache.UserCacheService;
import com.yc.fresh.busi.validator.CustomerValidator;
import com.yc.fresh.common.exception.SCApiRuntimeException;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.common.utils.KeyUtils;
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
    public String doRegister(UserInfo t) {
        customerValidator.validateMobileForRegister(t.getMobile());//TODO 此处也要缓存,不然容易被恶意搞
        boolean isOk = userInfoService.save(t);
        if (!isOk) {
            throw new SCApiRuntimeException();
        }
        LocalDateTime curdate = DateUtils.getCurrentDate();
        UpdateWrapper<UserInfo> wrapper = Wrappers.update();
        String tk = KeyUtils.createToken(t.getUserId());
        wrapper.set(UserInfo.TK, tk);
        wrapper.set(UserInfo.LAST_MODIFIED_TIME, curdate);
        //wrapper.set(UserInfo.TK_EXPIRED_TIME, curdate.plusMinutes(30));
        wrapper.eq(UserInfo.USER_ID, t.getUserId());
        isOk = this.userInfoService.update(wrapper);
        if (!isOk) {
            throw new SCApiRuntimeException();
        }
        return tk;
    }

    public UserInfo get(String openid) {
       return this.userCacheService.getByOpenId(openid);
    }

    public UserInfo get(Long userId) {
        return this.userCacheService.getT(userId);
    }



}

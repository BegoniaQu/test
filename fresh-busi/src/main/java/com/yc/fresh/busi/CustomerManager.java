package com.yc.fresh.busi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.fresh.busi.validator.CustomerValidator;
import com.yc.fresh.common.ServiceAssert;
import com.yc.fresh.common.exception.SCApiRuntimeException;
import com.yc.fresh.service.IUserInfoService;
import com.yc.fresh.service.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by quy on 2019/11/28.
 * Motto: you can do it
 */
@Component
@Slf4j
public class CustomerManager {

    private final IUserInfoService userInfoService;
    private final CustomerValidator customerValidator;

    @Autowired
    public CustomerManager(IUserInfoService userInfoService, CustomerValidator customerValidator) {
        this.userInfoService = userInfoService;
        this.customerValidator = customerValidator;
    }

    @Transactional(rollbackFor = Exception.class)
    public void doRegister(UserInfo t) {
        customerValidator.validateMobileForRegister(t.getMobile());
        boolean isOk = userInfoService.save(t);
        if (!isOk) {
            throw new SCApiRuntimeException();
        }
    }

    @Transactional(readOnly = true)
    public UserInfo get(String openid) {
        QueryWrapper<UserInfo> queryWrapper = Wrappers.query();
        queryWrapper.eq(UserInfo.WX_OPEN_ID, openid);
        return this.userInfoService.getOne(queryWrapper);
    }

    @Transactional(readOnly = true)
    public UserInfo get(Long userId) {
       return this.userInfoService.getById(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTk(Long userId, String tk) {
        UpdateWrapper<UserInfo> wrapper = Wrappers.update();
        wrapper.set(UserInfo.TK, tk);
        wrapper.eq(UserInfo.USER_ID, userId);
        boolean isOk = this.userInfoService.update(wrapper);
        if (!isOk) {
            throw new SCApiRuntimeException();
        }
    }
}

package com.yc.fresh.busi.validator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.fresh.service.IUserInfoService;
import com.yc.fresh.service.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by quy on 2019/11/30.
 * Motto: you can do it
 */
@Component
public class CustomerValidator implements Validator{

    private final IUserInfoService userInfoService;

    @Autowired
    public CustomerValidator(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public void validateMobileForRegister(String mobile) {
        QueryWrapper<UserInfo> qryWrapper = new QueryWrapper<>();
        qryWrapper.eq(UserInfo.MOBILE, mobile);
        UserInfo one = userInfoService.getOne(qryWrapper);
        Assert.isNull(one, "号码已被注册");
    }
}

package com.yc.fresh.api.rest.outer.convertor;

import com.yc.fresh.api.rest.outer.req.bean.RegisterReqBean;
import com.yc.fresh.api.rest.outer.resp.bean.AuthenRespBean;
import com.yc.fresh.api.rest.outer.resp.bean.PersonalRespBean;
import com.yc.fresh.api.rest.outer.resp.bean.RegisterRespBean;
import com.yc.fresh.busi.enums.LoginEnum;
import com.yc.fresh.common.utils.DateUtils;
import com.yc.fresh.common.utils.KeyUtils;
import com.yc.fresh.service.entity.UserInfo;
import org.springframework.util.StringUtils;

/**
 * Created by quy on 2019/11/28.
 * Motto: you can do it
 */
public class CustomerConvertor {


    public static AuthenRespBean convert2Bean(UserInfo userInfo, String openid) {
        AuthenRespBean respBean = new AuthenRespBean();
        if (userInfo == null) {
            respBean.setLogin(LoginEnum.unregistered.getT());
            respBean.setSpot(KeyUtils.encrypt(openid)); //在注册时传递给后端
        } else {
            respBean.setTk(KeyUtils.createToken(userInfo.getUserId()));
            respBean.setLogin(LoginEnum.registered.getT());
        }
        return respBean;
    }

    public static RegisterRespBean convert2Bean(Long userId) {
        RegisterRespBean respBean = new RegisterRespBean();
        respBean.setTk(KeyUtils.createToken(userId));
        return respBean;
    }

    public static UserInfo convert2Entity(RegisterReqBean reqBean, String openid) {
        UserInfo t = new UserInfo();
        t.setMobile(reqBean.getMobile());
        t.setWxOpenId(openid);
        t.setCreateTime(DateUtils.getCurrentDate());
        return t;
    }

    public static PersonalRespBean convert2Bean(UserInfo t) {
        PersonalRespBean respBean = new PersonalRespBean();
        respBean.setNickname(t.getNickName());
        respBean.setUserType(t.getUserType());
        String mobile = t.getMobile();
        if (!StringUtils.isEmpty(mobile)) {
            String front = mobile.substring(0, 3); //13452241205
            String tail = mobile.substring(7, 11);
            mobile = front + "****" + tail;
            respBean.setPhone(mobile);
        }
        return respBean;
    }
}

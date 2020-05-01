package com.yc.fresh.api.component;

import com.yc.fresh.busi.outer.CustomerManager;
import com.yc.fresh.busi.enums.PersonStatusEnum;
import com.yc.fresh.common.exception.SCTokenExpiredRuntimeException;
import com.yc.fresh.common.exception.SCUnAuthorizedRuntimeException;
import com.yc.fresh.common.utils.KeyUtils;
import com.yc.fresh.service.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by quy on 2020/1/10.
 * Motto: you can do it
 */
@Component
public class UserVerifier {

    private final CustomerManager customerManager;

    @Autowired
    public UserVerifier(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    /**
     * str: fresh_userId_time
     * @param request
     * @return
     */
    public UserInfo verify(HttpServletRequest request) {
        String token = request.getHeader("tk");
        String str = (String)request.getAttribute("user");
        request.removeAttribute("user"); //获取后移除
        Long userId = KeyUtils.getUserId(str);
        UserInfo userInfo = customerManager.get(userId);
        if(userInfo.getStatus() == PersonStatusEnum.close.getV()){
            throw new SCUnAuthorizedRuntimeException("您的账号已被封禁,如有疑问,请联系客服");
        }
        if(!token.equals(userInfo.getTk())){//token不对
            throw new SCTokenExpiredRuntimeException("request forbidden");
        }
        /*Date tkExpiredTime = userInfo.getTkExpiredTime();
        if(tkExpiredTime.before(new Date())){ //过期了
            throw new SCTokenExpiredRuntimeException("身份识别失败，过期口令");
        }*/
        return userInfo;
    }

}

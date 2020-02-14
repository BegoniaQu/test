package com.yc.fresh.api.test;

import com.yc.fresh.api.rest.outer.resp.SuperRespBean;
import com.yc.fresh.api.rest.outer.resp.bean.AuthenRespBean;

/**
 * Created by quy on 2020/1/14.
 * Motto: you can do it
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(SuperRespBean.class.isAssignableFrom(AuthenRespBean.class)); //true

        SuperRespBean authenRespBean = new AuthenRespBean();
        System.out.println(authenRespBean instanceof SuperRespBean);

    }
}

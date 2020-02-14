package com.yc.fresh.busi.enums;

/**
 * Created by quy on 2020/1/9.
 * Motto: you can do it
 */
public enum LoginEnum {
    unregistered(0),
    registered(1);

    private int t;

    LoginEnum(int t) {
        this.t = t;
    }

    public int getT() {
        return t;
    }
}

package com.yc.fresh.busi.enums;

/**
 * Created by quy on 2020/1/10.
 * Motto: you can do it
 */
public enum PersonStatusEnum {

    ok(1),
    close(0);


    private int v;

    PersonStatusEnum(int v) {
        this.v = v;
    }

    public int getV() {
        return v;
    }
}

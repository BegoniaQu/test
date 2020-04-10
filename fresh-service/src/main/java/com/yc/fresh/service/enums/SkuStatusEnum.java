package com.yc.fresh.service.enums;

/**
 * Created by quy on 2020/4/9.
 * Motto: you can do it
 */
public enum SkuStatusEnum {

    unuse(0),
    used(1);


    private int v;

    SkuStatusEnum(int v) {
        this.v = v;
    }

    public int getV() {
        return v;
    }
}

package com.yc.fresh.service.enums;

import org.springframework.util.Assert;

/**
 * Created by quy on 2019/11/26.
 * Motto: you can do it
 */
public enum UnitTypeEnum {

    capacity(1), //包装单位
    weight(2);  //重量单位

    private int v;

    UnitTypeEnum(int value) {
        this.v = value;
    }

    public static void verify(int v) {
        for (UnitTypeEnum typeEnum : UnitTypeEnum.values()) {
            if (typeEnum.v == v) {
                return;
            }
        }
        Assert.isTrue(false, "invalid unit_type");
    }

    public int getV() {
        return v;
    }
}

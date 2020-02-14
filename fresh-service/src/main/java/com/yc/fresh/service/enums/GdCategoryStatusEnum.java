package com.yc.fresh.service.enums;

import org.springframework.util.Assert;

/**
 * Created by quy on 2019/11/22.
 * Motto: you can do it
 */
public enum GdCategoryStatusEnum {
    INVALID(0, "废弃"),
    AVAILABLE(1, "可用");

    private int v;
    private String name;

    GdCategoryStatusEnum(int v, String name) {
        this.v = v;
        this.name = name;
    }


    public static void verify(int v) {
        for (GdCategoryStatusEnum statusEnum : GdCategoryStatusEnum.values()) {
            if (statusEnum.v == v) {
                return;
            }
        }
        Assert.isTrue(false, "invalid status");
    }


    public int getV() {
        return v;
    }

    public String getName() {
        return name;
    }
}

package com.yc.fresh.service.enums;

import org.springframework.util.Assert;

/**
 * Created by quy on 2019/11/25.
 * Motto: you can do it
 */
public enum  WarehouseStatusEnum {

    INVALID(0, "废弃"),
    AVAILABLE(1, "正常");

    private int v;
    private String name;

    WarehouseStatusEnum(int v, String name) {
        this.v = v;
        this.name = name;
    }


    public static void verify(int v) {
        for (WarehouseStatusEnum statusEnum : WarehouseStatusEnum.values()) {
            if (statusEnum.v == v) {
                return;
            }
        }
        Assert.isTrue(false, "invalid status");
    }

    public static String getName(int v) {
        for (WarehouseStatusEnum statusEnum : WarehouseStatusEnum.values()) {
            if (statusEnum.v == v) {
                return statusEnum.name;
            }
        }
        return "";
    }


    public int getV() {
        return v;
    }

    public String getName() {
        return name;
    }
}

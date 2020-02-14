package com.yc.fresh.service.enums;

import org.springframework.util.Assert;

/**
 * Created by quy on 2019/11/25.
 * Motto: you can do it
 */
public enum SaleGoodsStatusEnum {

    INVALID(0, "已废弃"),
    AVAILABLE(1, "待上架"),
    SALEABLE(2, "已上架"),
    UNSALEABLE(3, "已下架");


    private int v;
    private String name;

    SaleGoodsStatusEnum(int v, String name) {
        this.v = v;
        this.name = name;
    }


    public static void verify(int v) {
        for (SaleGoodsStatusEnum statusEnum : SaleGoodsStatusEnum.values()) {
            if (statusEnum.v == v) {
                return;
            }
        }
        Assert.isTrue(false, "invalid status");
    }

    public static String getName(int v) {
        for (SaleGoodsStatusEnum statusEnum : SaleGoodsStatusEnum.values()) {
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

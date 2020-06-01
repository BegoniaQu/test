package com.yc.fresh.busi.enums;

/**
 * Created by quy on 2020/6/1.
 * Motto: you can do it
 */
public enum GoodsStateEnum {
    ok(0),
    saleOut(1),
    gdDown(2);

    private int state;


    GoodsStateEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}

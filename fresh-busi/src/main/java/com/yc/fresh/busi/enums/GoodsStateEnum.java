package com.yc.fresh.busi.enums;

import com.yc.fresh.service.entity.GoodsSaleInfo;
import com.yc.fresh.service.enums.SaleGoodsStatusEnum;

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

    public static int check(GoodsSaleInfo t) {
        if (t.getStatus() != SaleGoodsStatusEnum.SALEABLE.getV()) {
            return gdDown.getState();
        }
        if (t.getInventory() <= 0) {
            return saleOut.getState();
        }
        return ok.getState();
    }
}

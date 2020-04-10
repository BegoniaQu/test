package com.yc.fresh.service.enums;

/**
 * Created by quy on 2020/4/10.
 * Motto: you can do it
 */
public enum DeliverymanStatusEnum {

    wait_audit(0), //待审核
    auditing(1), //审核中
    ok(2), //正常
    refuse(3); //审核不通过

    private int v;

    DeliverymanStatusEnum(int v) {
        this.v = v;
    }

    public int getV() {
        return v;
    }
}

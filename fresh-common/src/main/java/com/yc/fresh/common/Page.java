package com.yc.fresh.common;

/**
 * Created by quy on 2019/8/7.
 * Motto: you can do it
 */
public class Page {

    protected Integer ps = 10;
    protected Integer pn = 1;


    public static final int maxSize = 2000;


    public Integer getPs() {
        return ps;
    }

    public void setPs(Integer ps) {
        if (ps >= maxSize) {
            ps = maxSize;
        }
        this.ps = ps;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }
}

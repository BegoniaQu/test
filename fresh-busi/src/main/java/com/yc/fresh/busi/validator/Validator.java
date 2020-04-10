package com.yc.fresh.busi.validator;

/**
 * Created by quy on 2019/12/1.
 * Motto: you can do it
 */
public interface Validator {

    /*default String getErrorMsg(String msg) {
        return "verify failed: " + msg;
    }*/

    default String nullMsg(String col) {
        return "null(" + col + ") found";
    }
    default String unknownMsg(String col) {
        return "unknown(" + col + ")";
    }

    default String invalidMsg(String col) {
        return "invalid(" + col + ")";
    }


}

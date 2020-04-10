package com.yc.fresh.api.builder;

/**
 * Created by quy on 2019/12/2.
 * Motto: you can do it
 */
public class LockNameBuilder {

    public static final String storeMutexLock = "storeLock:";
    public static final String skuMutexLock = "skuLock:";
    public static final String delimiter = ":";
    public static final String openidLock = "openidLock:";

    public static String buildStock(String... args) {
        StringBuilder sb = new StringBuilder(storeMutexLock);
        for (String arg : args) {
            sb.append(arg).append(delimiter);
        }
        return sb.substring(0, sb.lastIndexOf(delimiter));
    }

    public static String buildSku(Long skuId) {
        return skuMutexLock + skuId;
    }

    public static String buildOpenid(String openid) {
        return openidLock + openid;
    }

    public static void main(String[] args) {
        System.out.println(LockNameBuilder.buildStock("hmd-021-01", "11002"));
    }
}

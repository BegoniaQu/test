package com.yc.fresh.busi.cache.key;

/**
 * Created by quy on 2019/11/28.
 * Motto: you can do it
 */
public class RedisKeyUtils {

    private static final String lock = "lock:";
    private static final String bizCodeCounter = "bizCodeCounter:";
    public static final String firstCategory2List = "firstCategory2List:";

    public static String lockKey(String name){
        return lock + name;
    }

    public static String bizCodeCounterKey(String key) {
        return bizCodeCounter + key;
    }


    public static String getFirstCategory2List(Integer parentId) {
        return firstCategory2List + parentId;
    }
}

package com.yc.fresh.common.lock;

/**
 * Created by quy on 2019/11/27.
 * Motto: you can do it
 */
public interface DistributedLock<T> {

    T lock(String lockName, Long waitSecond, Long expiredSecond);

    T lock(String lockName);

}

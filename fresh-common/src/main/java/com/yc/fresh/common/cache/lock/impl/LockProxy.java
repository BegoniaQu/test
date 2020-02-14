package com.yc.fresh.common.cache.lock.impl;

import org.redisson.api.RLock;

/**
 * Created by quy on 2019/11/27.
 * Motto: you can do it
 */
public class LockProxy {

    private final RLock rLock;

    LockProxy(RLock rLock) {
        this.rLock = rLock;
    }

    public void release() {
        if(rLock != null){
            rLock.unlock();
        }
    }
}

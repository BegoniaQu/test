package com.yc.fresh.common.cache.lock.impl;

import com.yc.fresh.common.cache.template.RedissonTemplate;
import com.yc.fresh.common.lock.DistributedLock;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by quy on 2019/11/27.
 * Motto: you can do it
 */
public class Lock implements DistributedLock<LockProxy> {

    private static final Logger log = LoggerFactory.getLogger("Lock");

    private static final long defaultWaitLockTimeOUt = 2L;
    private static final long defaultExpired = 3L;

    private final RedissonTemplate redissonTemplate;

    public Lock(RedissonTemplate redissonTemplate) {
        this.redissonTemplate = redissonTemplate;
    }

    @Override
    public LockProxy lock(String lockName) {
       return lock(lockName, defaultWaitLockTimeOUt, defaultExpired);
    }

    @Override
    public LockProxy lock(String lockName, Long waitSecond, Long expiredSecond) {
        Assert.hasText(lockName, "lockName must be not null");
        Assert.isTrue(waitSecond != null && waitSecond > 0, "waitSecond must be greater than 0");
        Assert.isTrue(expiredSecond != null && expiredSecond > 0, "expiredSecond must be greater than 0");
        try {
            RLock rLock = redissonTemplate.acquireDistLock(lockName);
            boolean locked = rLock.tryLock(waitSecond, expiredSecond, TimeUnit.SECONDS);
            if(locked){
                return new LockProxy(rLock);
            }
        } catch (Exception e) {
            log.error(String.format("get lock error,lockName=%s", lockName), e);
        }
        return null;
    }
}

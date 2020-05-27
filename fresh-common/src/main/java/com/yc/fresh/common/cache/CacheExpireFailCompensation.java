package com.yc.fresh.common.cache;

/**
 * Created by quy on 2020/5/22.
 * Motto: you can do it
 * <p>失败补偿</p>
 */
public interface CacheExpireFailCompensation {

    void compensate(String key);
}

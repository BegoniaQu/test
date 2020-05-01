package com.yc.fresh.common.cache.service;


import java.io.Serializable;

/**
 * Created by quy on 2020/4/10.
 * Motto: you can do it
 */
public interface ICacheService<T> {

    void add(T t);
    T getByPid(Serializable id);
    void removeByPid(Serializable id);

    void listAdd(String key, T t);
    void listDel(String key);
}

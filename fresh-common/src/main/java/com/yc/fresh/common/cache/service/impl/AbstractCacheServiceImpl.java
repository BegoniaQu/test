package com.yc.fresh.common.cache.service.impl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yc.fresh.common.cache.annotation.CacheId;
import com.yc.fresh.common.cache.service.CacheAssert;
import com.yc.fresh.common.cache.service.ICacheService;
import com.yc.fresh.common.cache.template.Nil;
import com.yc.fresh.common.cache.template.RedissonTemplate;
import com.yc.fresh.common.exception.SCRedisRuntimeException;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Random;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */
public abstract class AbstractCacheServiceImpl<T> implements ICacheService<T> {

    protected Class<T> entityClass;
    protected String keyPrefix;

    protected Field idField;

    protected RedissonTemplate redissonTemplate;
    protected IService<T> dbService;

    protected long defaultLiveSecond = 86400*7;
    private long nilLiveSecond = 30;// 30s



    private void parseEntityClass(Class<T> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        int counter = 0;
        for (Field one : fields) {
            CacheId cacheId = one.getAnnotation(CacheId.class); //优先找cacheId注解的
            if (cacheId != null) {
                idField = one;
                counter++;
            }
        }
        if (counter > 1) {
            throw new RuntimeException("Annotation [CacheId] found on Field more than 1");
        }
        if (idField != null) {
            return;
        }
        ////用mybatisplus的
        for (Field one : fields) {
            TableId tableId = one.getAnnotation(TableId.class);
            if (tableId != null) {
                idField = one;
                break;
            }
        }
        if (idField == null) {
            throw new RuntimeException("initialize the pro(idField) failed");
        }
    }

    public AbstractCacheServiceImpl(RedissonTemplate redissonTemplate, IService<T> dbService) {
        Class<? extends AbstractCacheServiceImpl> aClass = getClass();
        Type type = aClass.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
        Assert.notNull(entityClass, "you must tell the correct ParameterizedType");
        keyPrefix = entityClass.getName().toLowerCase() + ":";
        System.out.println("-------------" + keyPrefix);
        parseEntityClass(entityClass);
        this.redissonTemplate = redissonTemplate;
        this.dbService = dbService;
    }

    private int getANumber() {
        return new Random().nextInt(30);
    }



    @Override
    public void add(T t) {
        Object v;
        try {
            v = idField.get(t);
        } catch (IllegalAccessException e) {
            throw new SCRedisRuntimeException(e);
        }
        String key = keyPrefix + String.valueOf(v);
        boolean result = redissonTemplate.set(key, t, defaultLiveSecond);
        CacheAssert.isOk(result);
    }

    @Override
    public T getByPid(Serializable id) {
        Nil nil = new Nil();
        String key = keyPrefix + id;
        T t = redissonTemplate.getEntity(key, nil);
        if (t == null) {
            t = this.dbService.getById(id);
        }else if (t instanceof Nil) {//防止穿透
            return null;
        }
        if (t == null && !nil.isE()) { //数据库中也没有,则设置一个nil到redis
            redissonTemplate.set(key, nil, nilLiveSecond);
        }
        if (t != null && !nil.isE()) { //redis此刻没问题 and user from db is not null
            long liveSecond = defaultLiveSecond + getANumber();
            redissonTemplate.set(key, t, liveSecond);
        }
        return t;
    }

    @Override
    public void removeByPid(Serializable id) {
        String key = keyPrefix + id;
        boolean result = redissonTemplate.delEntity(key);
        CacheAssert.isOk(result);
    }



    @Override
    public void listAdd(String key, T t) {
        boolean result = this.redissonTemplate.appendList(key, t, defaultLiveSecond);
        CacheAssert.isOk(result);
    }


    @Override
    public void listDel(String key) {
        boolean result = this.redissonTemplate.delList(key);
        CacheAssert.isOk(result);
    }

}

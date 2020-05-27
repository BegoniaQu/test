package com.yc.fresh.common.cache.service.impl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yc.fresh.common.cache.annotation.CacheId;
import com.yc.fresh.common.cache.service.CacheAssert;
import com.yc.fresh.common.cache.service.ICacheService;
import com.yc.fresh.common.cache.template.Nil;
import com.yc.fresh.common.cache.template.RedisTemplate;
import com.yc.fresh.common.exception.SCRedisRuntimeException;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by quy on 2020/4/20.
 * Motto: you can do it
 */
public abstract class AbstractCacheServiceImpl<T,S extends Serializable> implements ICacheService<T,S> {

    protected Class<T> entityClass;
    protected String keyPrefix;

    protected Field idField;

    protected RedisTemplate redisTemplate;
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

    public AbstractCacheServiceImpl(RedisTemplate redisTemplate, IService<T> dbService) {
        Class<? extends AbstractCacheServiceImpl> aClass = getClass();
        Type type = aClass.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
        Assert.notNull(entityClass, "you must tell the correct ParameterizedType");
        keyPrefix = entityClass.getSimpleName().toLowerCase() + ":";
        //System.out.println("-------------" + keyPrefix);
        parseEntityClass(entityClass);
        this.redisTemplate = redisTemplate;
        this.dbService = dbService;
    }

    private int getANumber() {
        return new Random().nextInt(100);
    }


    @Override
    public void set(String key, S id) {
        boolean result = this.redisTemplate.set(key, id, null);
        CacheAssert.isOk(result);
    }

    @Override
    public void del(String key) {
        boolean result = this.redisTemplate.delEntity(key);
        CacheAssert.isOk(result);
    }

    @Override
    public void add(T t, long second) {
        String id = getId(t);
        String key = keyPrefix + id;
        boolean result = redisTemplate.set(key, t, second > 0 ? second : null);
        CacheAssert.isOk(result);
    }

    public void add(T t) {
        add(t, 0);
    }

    private String getId(T t) {
        try {
            idField.setAccessible(true);
            Object v = idField.get(t);
            return String.valueOf(v);
        } catch (IllegalAccessException e) {
            throw new SCRedisRuntimeException(e);
        }
    }

    @Override
    public T getByPid(S id) {
        Nil nil = new Nil();
        String key = keyPrefix + id;
        T t = redisTemplate.getEntity(key, nil);
        if (t == null) {
            t = this.dbService.getById(id);
        }else if (t instanceof Nil) {//防止穿透
            return null;
        }
        if (t == null && !nil.isE()) { //数据库中也没有,则设置一个nil到redis
            redisTemplate.set(key, nil, nilLiveSecond);
        }
        if (t != null && !nil.isE()) { //redis此刻没问题 and user from db is not null
            long liveSecond = defaultLiveSecond + getANumber();
            redisTemplate.set(key, t, liveSecond);
        }
        return t;
    }

    @Override
    public void removeByPid(S id) {
        String key = keyPrefix + id;
        boolean result = redisTemplate.delEntity(key);
        CacheAssert.isOk(result);
    }

    @Override
    public List<T> findT(List<S> ids) {
        List<String> keys = ids.stream().map(t-> keyPrefix + t).collect(Collectors.toList());
        return this.redisTemplate.getEntitys(keys);
    }


    //--------------------list-------------------

    @Override
    public void listAdd(String key, S id, long second) {
        boolean result = this.redisTemplate.appendList(key, id, second > 0 ? second : null);
        CacheAssert.isOk(result);
    }

    public void listAdd(String key, S id) {
        listAdd(key, id, 0);
    }


    @Override
    public void listAdd(String key, List<T> list, long second) {
        boolean result = this.redisTemplate.addList(key, list, second > 0 ? second : null);
        CacheAssert.isOk(result);
    }

    @Override
    public void listAdd(String key, List<T> list) {
        listAdd(key, list, 0);
    }

    @Override
    public void listDel(String key) {
        boolean result = this.redisTemplate.cleanList(key);
        CacheAssert.isOk(result);
    }

    @Override
    public void listRmv(String key, S id) {
        boolean result = this.redisTemplate.rmvList(key, id);
        CacheAssert.isOk(result);
    }

    @Override
    public List<T> findT(String key) {
        return this.redisTemplate.findList(key);
    }

    @Override
    public List<S> findS(String key) {
        return this.redisTemplate.findList(key);
    }

    //----------------map------------------------

    @Override
    public void mapPut(String key, T t, long second) {
        boolean result = this.redisTemplate.mapAdd(key, getId(t), t, second > 0 ? second : null);
        CacheAssert.isOk(result);
    }

    @Override
    public void mapUpt(String key, T t) {
        boolean result = this.redisTemplate.mapUpt(key, getId(t), t);
        CacheAssert.isOk(result);
    }

    @Override
    public void mapRmv(String key, Serializable id) {
        boolean result = this.redisTemplate.mapRmv(key, String.valueOf(id));
        CacheAssert.isOk(result);
    }

    @Override
    public void mapDel(String key) {
        boolean result = this.redisTemplate.mapClean(key);
        CacheAssert.isOk(result);
    }

    @Override
    public List<T> fromMap(String key, int batchSize) {
        return this.redisTemplate.findFromMap(key, batchSize);
    }

    @Override
    public void increment(String key, long second) {
        boolean result = this.redisTemplate.incrementLong(key, second > 0 ? second : null);
        CacheAssert.isOk(result);
    }

    @Override
    public void decrement(String key) {
        boolean result = this.redisTemplate.decrementLong(key);
        CacheAssert.isOk(result);
    }

    @Override
    public Long getNum(String key) {
        return this.redisTemplate.getNum(key);
    }
}

package com.yc.fresh.common.cache.template;

import io.netty.channel.EventLoopGroup;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by quyang on 2017/6/9.
 *
 */
public class RedissonTemplate {

    private static final Logger log = LoggerFactory.getLogger("RedissonTemplate");

    private RedissonClient redissonClient;

    public RedissonTemplate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }


    public <T> boolean set(String key, T value, Long second) {
        boolean flag = true;
        try {
            RBucket<T> rBucket = redissonClient.getBucket(key);
            if(second != null){
                rBucket.set(value, second, TimeUnit.SECONDS);
            }else{
                rBucket.set(value);
            }
        } catch (Exception e) {
            flag = false;
            log.error("redisson: set operation error",e);
        }
        return flag;
    }


    public Integer getInteger(String key){
        try {
            RBucket<Integer> rBucket = redissonClient.getBucket(key);
            return rBucket.get();
        } catch (Exception e) {
            log.error("redisson: getInteger error",e);
        }
        return null;
    }


    public <T> T getEntity(String key, Nil nil){
        try {
            RBucket<T> rBucket = redissonClient.getBucket(key);
            return rBucket.get();
        } catch (Exception e) {
            log.error("redisson: getEntity error",e);
            if (Objects.nonNull(nil)) {
                nil.setE(true);
            }
        }
        return null;
    }

    public <T> boolean delEntity(String key){
        boolean flag = true;
        try {
            RBucket<T> rBucket = redissonClient.getBucket(key);
            rBucket.delete();
        } catch (Exception e) {
            flag = false;
            log.error("redisson: delEntity error",e);
        }
        return flag;
    }


    public <T> List<T> getEntitys(List<String> keys) {
        List<T> list = new ArrayList<>();
        try {
            RBuckets buckets = redissonClient.getBuckets();
            Map<String, T> map = buckets.get(keys.toArray(new String[0]));
            for (Map.Entry<String, T> stringTEntry : map.entrySet()) {
                list.add(stringTEntry.getValue());
            }
        } catch (Exception e) {
            log.error("redisson: getEntitys error",e);
        }
        return list;
    }

    public <T> T getEntity(String key) {
        try {
            RBucket<T> rBucket = redissonClient.getBucket(key);
            return rBucket.get();
        } catch (Exception e) {
            log.error("redisson: getEntity error",e);
        }
        return null;
    }



    public <T> boolean appendList(String key, T t, Long second){
        boolean flag = true;
        try{
            RList<T> rList = redissonClient.getList(key);
            rList.add(t);
            if(second != null){
                rList.expire(second, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            flag = false;
            log.error("redisson: appendList error",e);
        }
        return flag;
    }

    public <T> List<T> findList(String key){
        try {
            RList<T> rList = redissonClient.getList(key);
            return rList.readAll();
        } catch (Exception e) {
            log.error("redisson: findList error",e);
        }
        return null;
    }

    public <T> boolean addList(String key, List<T> list, Long second){
        boolean flag = true;
        try{
            RList<T> rList = redissonClient.getList(key);
            rList.addAll(list);
            if(second != null){
                rList.expire(second, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            flag = false;
            log.error("redisson: addList error",e);
        }
        return flag;
    }


    public boolean cleanList(String key) {
        boolean flag = true;
        try {
            RList rList = redissonClient.getList(key);
            rList.delete();
        }catch (Exception e) {
            flag = false;
            log.error("redisson: cleanList error", e);
        }
        return flag;
    }

    public boolean rmvList(String key, Object o) {
        boolean flag = true;
        try {
            RList rList = redissonClient.getList(key);
            rList.remove(o);
        }catch (Exception e) {
            flag = false;
            log.error("redisson: rmvList error", e);
        }
        return flag;
    }



    public  <T> boolean mapAdd(String key, String id, T t, Long second) {
        boolean flag = true;
        try {
            RMap<String, T> map = redissonClient.getMap(key);
            map.fastPut(id, t);
            if(second != null){
                map.expire(second, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            flag = false;
            log.error("redisson: mapAdd error", e);
        }
        return flag;
    }

    public  <T> boolean mapUpt(String key, String id, T t) {
        boolean flag = true;
        try {
            RMap<String, T> map = redissonClient.getMap(key);
            map.fastPut(id, t);
        } catch (Exception e) {
            flag = false;
            log.error("redisson: mapUpt error", e);
        }
        return flag;

    }

    public boolean mapRmv(String key, String... id) {
        boolean flag = true;
        try {
            RMap<String, Object> map = redissonClient.getMap(key);
            map.fastRemove(id);
        } catch (Exception e) {
            flag = false;
            log.error("redisson: mapRmv error", e);
        }
        return flag;
    }

    public boolean mapClean(String key) {
        boolean flag = true;
        try {
            RMap<String, Object> map = redissonClient.getMap(key);
            map.delete();
        } catch (Exception e) {
            flag = false;
            log.error("redisson: mapClean error", e);
        }
        return flag;
    }

    public <T> List<T> findFromMap(String key, int batchSize) {
        try {
            List<T> list = new ArrayList<>();
            RMap<String, T> map = redissonClient.getMap(key);
            //Collection<T> values = map.values(batchSize);
            for (Map.Entry<String, T> stringTEntry : map.entrySet(batchSize)) {
                list.add(stringTEntry.getValue());
            }
            return list;
        } catch (Exception e) {
            log.error("redisson: findFromMap error",e);
        }
        return Collections.emptyList();
    }

    /**
     *
     * @param key
     * @param map Double 代表分数，用于排序。用户自己设置分数，如：以创建时间作为分数等
     */
    public void addOne2ManyWithScore(String key, Map<String, Double> map, Long second){
        try {
            RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet(key);
            sortedSet.addAll(map);
            if(second != null){
                sortedSet.expire(second, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("redisson: addOne2ManyWithScore error",e);
        }
    }


    public List<String> findOne2ManySortedByScore(String key, boolean asc){
        try {
            RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet(key);
            int size = sortedSet.size();
            if(size == 0){
                return null;
            }
            if(asc){
                return (List<String>) sortedSet.valueRange(0, size);
            }else{
                return (List<String>) sortedSet.valueRangeReversed(0, size);
            }
        } catch (Exception e) {
            log.error("redisson: findOne2ManySortedByScore error",e);
        }
        return null;
    }


    public RLock acquireDistLock(String lockName) {
        return redissonClient.getLock(lockName);
    }



    public void close() {
        log.info("shutdown redis");
        EventLoopGroup eventLoopGroup = redissonClient.getConfig().getEventLoopGroup();
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
        redissonClient.shutdown();
    }

}

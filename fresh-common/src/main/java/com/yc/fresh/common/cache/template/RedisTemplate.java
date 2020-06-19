package com.yc.fresh.common.cache.template;

import io.netty.channel.EventLoopGroup;
import org.redisson.api.*;
import org.redisson.client.codec.IntegerCodec;
import org.redisson.client.codec.LongCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by quyang on 2017/6/9.
 *
 */
public class RedisTemplate {

    private static final Logger log = LoggerFactory.getLogger("RedisTemplate");

    private RedissonClient redissonClient;

    public RedisTemplate(RedissonClient redissonClient) {
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
            log.error("redis: set operation error",e);
        }
        return flag;
    }


    public List<String> keyLike(String keyPattern) {
        RKeys keys = redissonClient.getKeys();
        Iterable<String> iterator = keys.getKeysByPattern(keyPattern);
        List<String> list = new ArrayList<>();
        iterator.forEach(t->list.add(t));
        return this.getEntitys(list);
    }



    public Integer getInteger(String key){
        try {
            RBucket<Integer> rBucket = redissonClient.getBucket(key);
            return rBucket.get();
        } catch (Exception e) {
            log.error("redis: getInteger error",e);
        }
        return null;
    }


    public <T> T getEntity(String key, Nil nil){
        try {
            RBucket<T> rBucket = redissonClient.getBucket(key);
            return rBucket.get();
        } catch (Exception e) {
            log.error("redis: getEntity error",e);
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
            log.error("redis: delEntity error",e);
        }
        return flag;
    }


    public <T> List<T> getEntitys(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        List<T> list = new ArrayList<>();
        try {
            RBuckets buckets = redissonClient.getBuckets();
            Map<String, T> map = buckets.get(keys.toArray(new String[0]));
            for (Map.Entry<String, T> stringTEntry : map.entrySet()) {
                T t = stringTEntry.getValue();
                if (t == null) {
                    continue;
                }
                list.add(t);
            }
        } catch (Exception e) {
            log.error("redis: getEntitys error",e);
        }
        return list;
    }

    public <T> T getEntity(String key) {
        try {
            RBucket<T> rBucket = redissonClient.getBucket(key);
            return rBucket.get();
        } catch (Exception e) {
            log.error("redis: getEntity error",e);
        }
        return null;
    }


    public <T> boolean appendList(String key, T t, Long second){
        boolean flag;
        RList<T> rList = redissonClient.getList(key);
        try{
            flag = rList.add(t);
        } catch (Exception e) {
            flag = false;
            log.error("redis: appendList error",e);
        }
        if (!flag) {
            return flag;
        }
        setExpireTime(second, rList);
        return flag;
    }


    private void setExpireTime(Long second, RList rList) {
        if(second == null){
            return;
        }
        boolean expireOk;
        try {
            expireOk = rList.expire(second, TimeUnit.SECONDS);
        } catch (Exception e) {
            expireOk = false;
            log.error("redis: expire operation error",e);
        }
        if (!expireOk) {
            //TODO 补偿机制
        }
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
        boolean flag;
        RList<T> rList = redissonClient.getList(key);
        try{
            flag = rList.addAll(list);
        } catch (Exception e) {
            flag = false;
            log.error("redis: addList error",e);
        }
        if (!flag) {
            return flag;
        }
        setExpireTime(second, rList);
        return flag;
    }


    public boolean cleanList(String key) {
        boolean flag = true;
        try {
            RList rList = redissonClient.getList(key);
            rList.delete();
        }catch (Exception e) {
            flag = false;
            log.error("redis: cleanList error", e);
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
            log.error("redis: rmvList error", e);
        }
        return flag;
    }


    private void setExpireTime(Long second, RMap rMap) {
        if(second == null){
            return;
        }
        boolean expireOk;
        try {
            expireOk = rMap.expire(second, TimeUnit.SECONDS);
        } catch (Exception e) {
            expireOk = false;
            log.error("redis: expire operation error",e);
        }
        if (!expireOk) {
            //TODO 补偿机制
        }
    }


    public  <T> boolean mapAdd(String key, String id, T t, Long second) {
        boolean flag = true;
        RMap<String, T> map = redissonClient.getMap(key);
        try {
            map.fastPut(id, t);
        } catch (Exception e) {
            flag = false;
            log.error("redis: mapAdd error", e);
        }
        if (!flag) {
            return flag;
        }
        setExpireTime(second, map);
        return flag;
    }

    public  <T> boolean mapUpt(String key, String id, T t) {
        boolean flag = true;
        try {
            RMap<String, T> map = redissonClient.getMap(key);
            map.fastPut(id, t);
        } catch (Exception e) {
            flag = false;
            log.error("redis: mapUpt error", e);
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
            log.error("redis: mapRmv error", e);
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
            log.error("redis: mapClean error", e);
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
            log.error("redis: findFromMap error",e);
        }
        return Collections.emptyList();
    }

    public <T> Map<String, T> findMap(String key, int batchSize) {
        Map<String, T> vMap = new HashMap<>();
        RMap<String, T> map = redissonClient.getMap(key);
        try {
            for (Map.Entry<String, T> stringTEntry : map.entrySet(batchSize)) {
                vMap.put(stringTEntry.getKey(), stringTEntry.getValue());
            }
            return vMap;
        } catch (Exception e) {
            log.error("redis: findMap error",e);
        }
        return Collections.emptyMap();
    }


    public <T> T getFromMap(String key, String internalKey) {
        RMap<String, T> map = redissonClient.getMap(key);
        return map.get(internalKey);
    }

    /**
     *
     * @param key
     * @param map Double 代表分数，用于排序。用户自己设置分数，如：以创建时间作为分数等
     */
    public void addOne2ManyWithScore(String key, Map<String, Double> map){
        try {
            RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet(key);
            sortedSet.addAll(map);
        } catch (Exception e) {
            log.error("redis: addOne2ManyWithScore error",e);
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
            log.error("redis: findOne2ManySortedByScore error",e);
        }
        return null;
    }



    private void setExpireTime(Long second, RAtomicLong atomicLong) {
        if(second == null){
            return;
        }
        boolean expireOk;
        try {
            expireOk = atomicLong.expire(second, TimeUnit.SECONDS);
        } catch (Exception e) {
            expireOk = false;
            log.error("redis: expire operation error",e);
        }
        if (!expireOk) {
            //TODO 补偿机制
        }
    }

    /**
     *
     * @param key
     * @param num 扣减时 为负数
     * @param second
     * @return
     */
    public boolean optNum(String key, int num, Long second) {
        boolean flag = true;
        RAtomicLong atomicLong = this.redissonClient.getAtomicLong(key);
        try {
            atomicLong.addAndGet(num);
        } catch (Exception e) {
            flag = false;
            log.error("redis: optNum error", e);
        }
        if (!flag) {
            return flag;
        }
        setExpireTime(second, atomicLong);
        return flag;
    }

    public boolean clearNum(String key) {
        boolean flag = true;
        RAtomicLong atomicLong = this.redissonClient.getAtomicLong(key);
        try {
            atomicLong.delete();
        } catch (Exception e) {
            flag = false;
            log.error("redis: clearNum error", e);
        }
        if (!flag) {
            return flag;
        }
        return flag;
    }

    public Long getNum(String key) {
        try {
            RAtomicLong atomicLong = this.redissonClient.getAtomicLong(key);
            return atomicLong.get();
        } catch (Exception e) {
            log.error("redis: getNum error", e);
        }
        return null;
    }

    public Map<String, Integer> findNums(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyMap();
        }
        try {
            RBuckets buckets = redissonClient.getBuckets(new IntegerCodec()); //如果这里不用IntegerCodec,则数据转换的就是错误的
            return buckets.get(keys.toArray(new String[0]));
        } catch (Exception e) {
            log.error("redis: findNums error",e);
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



    /*String listAddLua = "return redis.call('RPUSH', KEYS[1], ARGV[1]);";

    public void testScriptList(String key, Object t) {
        Long v = redissonClient.getScript().eval(RScript.Mode.READ_WRITE,
                listAddLua, RScript.ReturnType.INTEGER, Arrays.asList(key), t);
        System.out.println(v);
    }
*/
}

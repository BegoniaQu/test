package com.yc.fresh.busi.cache;

import com.yc.fresh.busi.cache.key.RedisKeyUtils;
import com.yc.fresh.common.cache.lock.impl.LockProxy;
import com.yc.fresh.common.cache.service.impl.AbstractCacheServiceImpl;
import com.yc.fresh.common.cache.template.RedisTemplate;
import com.yc.fresh.common.exception.SCApiRuntimeException;
import com.yc.fresh.common.lock.DistributedLock;
import com.yc.fresh.service.IShoppingCarService;
import com.yc.fresh.service.entity.ShoppingCar;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by quy on 2020/5/21.
 * Motto: you can do it
 */
@Component
public class ShopCarCacheService extends AbstractCacheServiceImpl<ShoppingCar, Long> {


    private final DistributedLock<LockProxy> distributedLock;

    public ShopCarCacheService(RedisTemplate redisTemplate, IShoppingCarService dbService,
                               DistributedLock<LockProxy> distributedLock) {
        super(redisTemplate, dbService);
        this.distributedLock = distributedLock;
    }

    public boolean populateCar(ShoppingCar t) {
        String lockName = RedisKeyUtils.lockKey(String.valueOf(t.getUserId()));
        LockProxy lock = distributedLock.lock(lockName);
        if (lock == null) {
            throw new SCApiRuntimeException();
        }
        String key = RedisKeyUtils.getUser2Shopcar(t.getUserId());
        Integer v = this.redisTemplate.getFromMap(key, t.getGoodsId());
        boolean isOk;
        if (v == null) {
            isOk = this.redisTemplate.mapAdd(key, t.getGoodsId(), t.getNum(), defaultLiveSecond * 2);
        } else {
            Integer newV = t.getNum() + v.intValue();
            isOk = this.redisTemplate.mapUpt(key, t.getGoodsId(), newV);
        }
        lock.release();
        return isOk;
    }

    public boolean removeFromCar(ShoppingCar t) {
        String key = RedisKeyUtils.getUser2Shopcar(t.getUserId());
        return this.redisTemplate.mapRmv(key, t.getGoodsId());
    }

    public boolean delCar(Long userId) {
        String key = RedisKeyUtils.getUser2Shopcar(userId);
        return this.redisTemplate.mapClean(key);
    }


    public List<ShoppingCar> findCar(Long userId) {
        String key = RedisKeyUtils.getUser2Shopcar(userId);
        Map<String, Integer> map = this.redisTemplate.findMap(key, 30);
        return map.entrySet().stream().map(t->{
            ShoppingCar one = new ShoppingCar();
            one.setGoodsId(t.getKey());
            one.setNum(t.getValue());
            return one;
        }).collect(Collectors.toList());
    }
}

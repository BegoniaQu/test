package com.yc.fresh.busi.id;

import com.yc.fresh.busi.cache.key.RedisKeyUtils;
import com.yc.fresh.common.cache.lock.impl.LockProxy;
import com.yc.fresh.common.cache.template.RedissonTemplate;
import com.yc.fresh.common.exception.SCApiRuntimeException;
import com.yc.fresh.common.lock.DistributedLock;
import com.yc.fresh.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by quy on 2019/11/28.
 * Motto: you can do it
 */
@Component
public class IdGenerator {

    private final RedissonTemplate redissonTemplate;
    private final DistributedLock<LockProxy> distributedLock;

    private static final String format = "yyMMddHHmmss";
    private static final int initValue = 1;
    private static final int tailLen = 3; //末尾位数

    public static final String USRID_PREFIX = "50";
    public static final String ORDERID_PREFIX = "60";

    @Autowired
    public IdGenerator(RedissonTemplate redissonTemplate, DistributedLock distributedLock) {
        this.redissonTemplate = redissonTemplate;
        this.distributedLock = distributedLock;
    }

    public String generate() {
        long timestamp = System.currentTimeMillis();
        return String.valueOf(timestamp);
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }

    public String generate(String prefix) {
        StringBuilder sb = new StringBuilder(prefix);
        String dateStr = DateUtils.getCurrentTime(format);
        sb.append(dateStr);
        String frontPart = sb.toString();
        String lockKey = RedisKeyUtils.lockKey(frontPart);//50190614170122
        //get lock
        LockProxy lockProxy = distributedLock.lock(lockKey, 3L, 6L);
        if (lockProxy == null) {
            throw new SCApiRuntimeException();
        }
        String bizCodeCounterKey = RedisKeyUtils.bizCodeCounterKey(frontPart);
        Integer value = redissonTemplate.getInteger(bizCodeCounterKey);
        if(value == null){
            value = initValue;
        }else{
            value ++;
        }
        redissonTemplate.set(bizCodeCounterKey, value ,3L); //6-3
        // unlock
        lockProxy.release();
        String valueStr = String.valueOf(value);
        int len = tailLen - valueStr.length();
        if(len > 0){
            for(int i = 1; i <= len; i++){
                valueStr = "0" + valueStr;
            }
        }
        sb.append(valueStr);
        return sb.toString();
    }
}

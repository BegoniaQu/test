package com.yc.fresh.common.cache;

import com.yc.fresh.common.cache.template.RedissonTemplate;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.MsgPackJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by quy on 2019/11/26.
 * Motto: you can do it
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(RedissonAutoConfiguration.class);

    private RedissonProperties redissonProperties;

    public RedissonAutoConfiguration(RedissonProperties redissonProperties) {
        this.redissonProperties = redissonProperties;
    }

    @Override
    public void afterPropertiesSet() {
        checkConfig();
    }

    private void checkConfig() {
        boolean flag = !StringUtils.isEmpty(redissonProperties.getHost()) && !StringUtils.isEmpty(redissonProperties.getPort());
        Assert.state(flag, "cannot find redis connection info, please check your config");
    }

    @Bean
    @ConditionalOnMissingBean
    RedissonClient singleServerMode() {
        log.info("start to create RedisClient");
        Config config = new Config();
        config.setThreads(0);
        config.setNettyThreads(0);
        config.setCodec(new MsgPackJacksonCodec());
        String address = "redis://"+ redissonProperties.getHost() + ":" + redissonProperties.getPort();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setClientName(redissonProperties.getClientName())
                .setAddress(address)
                .setSubscriptionConnectionPoolSize(redissonProperties.getSubscriptionConnectionPoolSize())
                .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());

        if(!StringUtils.isEmpty(redissonProperties.getPwd())) {
            serverConfig.setPassword(redissonProperties.getPwd());
        }
        return Redisson.create(config);
    }

    @Bean(destroyMethod = "close")
    public RedissonTemplate getTemplate(RedissonClient redissonClient) {
        return new RedissonTemplate(redissonClient);
    }
}

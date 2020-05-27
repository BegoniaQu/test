package com.yc.fresh.api.conf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yc.fresh.api.filter.AuthFilter;
import com.yc.fresh.api.filter.LogFilter;
import com.yc.fresh.common.cache.lock.impl.Lock;
import com.yc.fresh.common.cache.template.RedisTemplate;
import com.yc.fresh.common.lock.DistributedLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created by quy on 2019/7/16.
 * Motto: you can do it
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectMapper objectMapper = builder.build();
        SimpleModule simpleModule = new SimpleModule();
        //后端把Long类型的数据传给前端，前端可能会出现精度丢失的情况。例如：201511200001725439这样一个Long类型的整数，传给前端后会变成201511200001725440。
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);// 忽略 transient 修饰的属性
        converters.add(0, new MappingJackson2HttpMessageConverter(objectMapper));//放到list的第一位，不然不会被用到
        //converters.add(new BufferedImageHttpMessageConverter()); //用于支持BufferedImage返回
    }

    @Bean
    public LogFilter getLogFilter() {
        return new LogFilter();
    }
    @Bean
    public AuthFilter getAuthFilter() {
        return new AuthFilter();
    }

    @Bean
    public DistributedLock geLock(RedisTemplate redisTemplate) {
        return new Lock(redisTemplate);
    }
}

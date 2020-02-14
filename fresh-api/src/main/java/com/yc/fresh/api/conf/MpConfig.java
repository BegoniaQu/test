package com.yc.fresh.api.conf;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by quy on 2019/7/15.
 * Motto: you can do it
 */
@Configuration
@MapperScan("com.yc.fresh.service.mapper*")
public class MpConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

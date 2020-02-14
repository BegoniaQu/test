package com.yc.fresh.api.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by quy on 2020/1/8.
 * Motto: you can do it
 */
@Configuration
@PropertySource("classpath:fresh.properties")
@Getter
@Setter
public class AppConfig {

    @Value("${wx.url}")
    private String wxUrl;
    @Value("${wx.appid}")
    private String wxAppid;
    @Value("${wx.appscrt}")
    private String wxAppSecret;

}

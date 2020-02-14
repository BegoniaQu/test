package com.yc.fresh.api;

import com.yc.fresh.common.utils.ApplicationContextUtil;
import org.springframework.beans.BeansException;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(
        scanBasePackages = {
                "com.yc.fresh.api.component",
                "com.yc.fresh.api.conf",
                "com.yc.fresh.api.rest",
                "com.yc.fresh.busi",
                "com.yc.fresh.service.impl"
        })
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableSwagger2
public class FreshApplication extends SpringBootServletInitializer implements ApplicationContextAware{
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(new Class[]{FreshApplication.class});
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
        //SpringApplication.run(HomedoActivitiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.application().setBannerMode(Banner.Mode.OFF);
        return builder.sources(FreshApplication.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.setApplicationContext(applicationContext);
    }
}

package com.yc.fresh.common.utils;

import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil {
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}

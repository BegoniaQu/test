package com.yc.fresh.common.cache.annotation;


import java.lang.annotation.*;

/**
 * Created by quy on 2020/4/22.
 * Motto: you can do it
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheId {

    String value() default "";

}

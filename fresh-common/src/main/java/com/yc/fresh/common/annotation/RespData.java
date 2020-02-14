package com.yc.fresh.common.annotation;

import java.lang.annotation.*;

/**
 * Created by quy on 2019/7/24.
 * Motto: you can do it
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RespData {

    String value() default "";
}

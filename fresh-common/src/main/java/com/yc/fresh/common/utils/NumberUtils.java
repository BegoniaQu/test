package com.yc.fresh.common.utils;

import org.springframework.util.Assert;

import java.util.regex.Pattern;

/**
 * Created by quy on 2020/6/15.
 * Motto: you can do it
 */
public class NumberUtils {

    public static void checkMobile(String str) {
        Assert.isTrue(str.length() == 11, "无效的号码");
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        Assert.isTrue(pattern.matcher(str).matches(), "无效的号码");
    }
}

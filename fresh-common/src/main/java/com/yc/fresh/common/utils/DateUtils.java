package com.yc.fresh.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by quy on 2019/8/6.
 * Motto: you can do it
 */
public class DateUtils {

    private static final String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
    private static final String datePattern = "yyyy-MM-dd";


    public static String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimePattern));
    }

    public static String getCurrentTime(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCurrentDay() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(datePattern));
    }

    public static LocalDateTime getCurrentDate() {
        return LocalDateTime.now().withNano(0);
    }

    public static String convert2Str(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(dateTimePattern));
    }


    public static void main(String[] args) {
        System.out.println(DateUtils.getCurrentDay());
    }


}

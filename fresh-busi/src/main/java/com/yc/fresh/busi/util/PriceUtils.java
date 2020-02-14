package com.yc.fresh.busi.util;

import java.math.BigDecimal;

/**
 * Created by quy on 2019/12/2.
 * Motto: you can do it
 */
public class PriceUtils {

    public static BigDecimal getWeightedAveragePrice(BigDecimal price1, int num1, BigDecimal price2, int num2) {
        BigDecimal fz = price1.multiply(new BigDecimal(num1)).add(price2.multiply(new BigDecimal(num2)));
        BigDecimal fm = new BigDecimal(num1 + num2);
        return fz.divide(fm, 2, BigDecimal.ROUND_HALF_EVEN);
    }

    public static void main(String[] args) {
        BigDecimal price1 = new BigDecimal("4");
        int num1 = 10;
        BigDecimal price2 = new BigDecimal("4.5");
        int num2 = 20;
        System.out.println(PriceUtils.getWeightedAveragePrice(price1, num1, price2, num2));
    }
}

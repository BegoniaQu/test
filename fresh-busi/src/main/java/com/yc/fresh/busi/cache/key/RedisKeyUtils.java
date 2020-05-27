package com.yc.fresh.busi.cache.key;



/**
 * Created by quy on 2019/11/28.
 * Motto: you can do it
 */
public class RedisKeyUtils {

    private static final String lock = "lock:";
    private static final String bizCodeCounter = "bizCodeCounter:";
    private static final String firstCategory2List = "firstCategory2List:";
    private static final String warehouseFc2List = "warehouseFc2List:";
    private static final String saleGdPics = "saleGdPics:";
    private static final String user2Shopcar = "user2ShopCar:";
    private static final String shopCarGd2Num = "shopCarGd2Num:";
    public static final String saleGdSearch = "gdSearch:";

    public static final String delimiter = ":";

    public static String lockKey(String name){
        return lock + name;
    }

    public static String bizCodeCounterKey(String key) {
        return bizCodeCounter + key;
    }


    public static String getSaleGdPics(String goodsId) {
        return saleGdPics + goodsId;
    }

    public static String getFirstCategory2List(Integer parentId) {
        return firstCategory2List + parentId;
    }

    public static String getWarehouseFc2List(String warehouseCode, Integer fCategoryId) {
        return warehouseFc2List + warehouseCode + delimiter + fCategoryId;
    }

    public static String getUser2Shopcar(Long userId) {
        return user2Shopcar + userId;
    }

    public static String getShopCarGd2Num(String goodsId) {
        return shopCarGd2Num + goodsId;
    }

    public static String getSaleGdSearch(String name) {
        return saleGdSearch + name;
    }
}

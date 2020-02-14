package com.yc.fresh.common.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class KeyUtils {


    private static  final String key = "LVaOOGMsCaD4y8QUH8byTw==";
    private static final String prefix = "fresh";
    private static byte[] getKey(){
        return base64Decode(key);
    }

    public static String createToken(Long id){
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append("_").append(id).append("_").append(date);
        String str = sb.toString();
        try {
            byte[] b = AESUtils.encrypt(str.getBytes(),getKey());
            return base64Encode(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String parse(String str){
        try {
            byte[] b = AESUtils.decrypt(base64Decode(str), getKey());
            return new String(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Long getUserId(String str){
        String [] arr = str.split("_");
        return Long.parseLong(arr[1]);
    }

    public static String getTime(String str) {
        String [] arr = str.split("_");
        return arr[2];
    }


    public static String encrypt(String str) {
        try {
            byte[] b = AESUtils.encrypt(str.getBytes(),getKey());
            return base64Encode(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    public static void main(String[] args) throws Exception {
       /* KeyGenerator keyGen = KeyGenerator.getInstance("AES");//密钥生成器
        keyGen.init(128); //默认128，获得无政策权限后可为192或256
        SecretKey secretKey = keyGen.generateKey();//生成密钥
        byte[] key = secretKey.getEncoded();//密钥字节数组
        System.out.println(base64Encode(key));*/
        String token = createToken(26L);
        System.out.println(token);
        System.out.println(parse("bxEykZt0cZ4DTy9qkF35sEyI5Rg2riwzORPBn2lPKCk="));
    }


    private static String base64Encode(byte [] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }
    private static byte[] base64Decode(String str){
        return Base64.getDecoder().decode(str);
    }
}

package com.yc.fresh.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.yc.fresh.common.exception.SCCustomRuntimeException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quyang on 2018/4/3.
 * note：接收json格式的请求参数
 */
public class JsonUtil {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    private static final ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
                false);
        objectMapper.configure(
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * springboot中支持@requestBody注解完成这一工具目的
     * @param request
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T jsonConvert(HttpServletRequest request, Class<T> cls) {
        String json = readStringFromRequest(request, CHARSET);
        return getObjectFromJson(json, cls);
    }



    public static <T> T getObjectFromJson(String str, Class<T> cls) {
        try {
            JsonParser parser = objectMapper.getFactory().createParser(str);
            T t = objectMapper.readValue(parser, cls);
            return t;
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new SCCustomRuntimeException("parse json error");
        } catch (IOException e) {
            e.printStackTrace();
            throw new SCCustomRuntimeException("parse json error");
        }

    }

    public  static <T> List<T> getObjectsFromJson(String str, Class<T> clsT) {
        List<T> list;
        try {
            JsonParser parser = objectMapper.getFactory().createParser(str);

            list = objectMapper.readValue(parser, new TypeReference<List<T>>(){});

        } catch(JsonParseException e) {
            e.printStackTrace();
            throw new SCCustomRuntimeException("parse json error");

        } catch(IOException e) {
            e.printStackTrace();
            throw new SCCustomRuntimeException("parse json error");
        }
        return list;
    }

    public static <T> List<T> getListFromJson(String str, Class<T> cls){
        List<T> list;
        try {
            list = objectMapper.readValue(str, getCollectionType(ArrayList.class, cls));
        } catch(JsonParseException e) {
            e.printStackTrace();
            throw new SCCustomRuntimeException("parse json error");

        } catch(IOException e) {
            e.printStackTrace();
            throw new SCCustomRuntimeException("parse json error");
        }
        return list;
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


    public static String getJsonFromObject(Object object) {
        try {
            return objectMapper.writeValueAsString(object);

        } catch(JsonGenerationException e) {
            throw new SCCustomRuntimeException("parse json error");

        } catch(JsonMappingException e) {
            throw new SCCustomRuntimeException("parse json error");

        } catch(IOException e) {
            throw new SCCustomRuntimeException("parse json error");
        }
    }




    private static String readStringFromRequest(HttpServletRequest request, Charset charset) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(), charset));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new SCCustomRuntimeException("read request param error");
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }
}

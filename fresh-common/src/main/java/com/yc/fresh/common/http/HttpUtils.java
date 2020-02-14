package com.yc.fresh.common.http;

import com.yc.fresh.common.exception.SCHttpRuntimeException;
import com.yc.fresh.common.utils.JsonUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by quyang on 2018/4/9.
 */
public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
    private OkHttpClient okHttpClient;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private HttpUtils() {
        okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS).build();
    }

    public static HttpUtils getInstance() {
        return HttpClient.INSTANCE.getInstance();
    }


    public String doGet(String url, Map<String,String> params) {
        if(params != null && params.size() > 0){
            StringBuilder sbUrl = new StringBuilder();
            sbUrl.append(url).append("?");
            params.forEach((k,v)-> sbUrl.append(k).append("=").append(v).append("&"));
            url = sbUrl.substring(0,sbUrl.lastIndexOf("&"));
        }
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();//得到Response 对象
            if (response.isSuccessful()){
                String respStr = response.body().string();
                return respStr;
            }
            log.error("url: {} doGet failed, resp: {}", url, response.body().toString());
        } catch (IOException e) {
            log.error(String.format("url: {} doGet error", url), e);
        }finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }


    public <T> String doPost(String url, T t) {
        String content = JsonUtil.getJsonFromObject(t);
        RequestBody requestBody = RequestBody.create(JSON, content);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();//得到Response 对象
            if (response.isSuccessful()){
                String respStr = response.body().string();
                return respStr;
            }
            log.error("url: {}, ctn: {} doPost failed, resp: {}", url, content, response.body().toString());
        } catch (IOException e) {
            throw new SCHttpRuntimeException(String.format("okHttp doPost error: %s", e.getMessage()));
        }finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }


    public void doPostAsync(final String url, String jsonStr, final AsyncFailedHandler handler) {
        String content = jsonStr;
        RequestBody requestBody = RequestBody.create(JSON, content);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String str = e.getMessage();
                handler.handle(new FailedContent(url, content, str));
                log.error("url: {}, ctn: {} doPost exception: {}", url, content, str);
            }
            @Override
            public void onResponse(Call call, Response response) {
                String str = response.body().toString();
                if (response.isSuccessful()){
                    log.info("url: {}, ctn: {} doPost ok, resp: {}", url, content, str);
                }else{
                    handler.handle(new FailedContent(url, content, response.body().toString()));
                    log.error("url: {}, ctn: {} doPost failed, resp: {}", url, content, str);
                }
            }
        });
    }

    public void doPostAsync(final String url, Object body, final AsyncFailedHandler handler) {
        final String jsonStr = JsonUtil.getJsonFromObject(body);
        doPostAsync(url, jsonStr, handler);
    }


    private enum HttpClient{

        INSTANCE;

        private final HttpUtils instance;

        HttpClient() {
            this.instance = new HttpUtils();
        }

        private HttpUtils getInstance() {
            return instance;
        }
    }

    public static void main(String[] args) {
        /*Map<String, String> map = new HashMap<>();
        map.put("year", "2018");
        System.out.println( HttpUtils.doGet("www.baidu.com", null));*/
    }

}

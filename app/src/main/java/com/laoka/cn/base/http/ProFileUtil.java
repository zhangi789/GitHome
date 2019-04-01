package com.laoka.cn.base.http;

import android.util.Log;

import com.laoka.cn.util.CPoolUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * @author 张海洋
 * @Date on 2019/03/21.
 * @org 上海..科技有限公司
 * @describe
 */

public class ProFileUtil {
    public static String TAG = "----";

    //日志拦截器
    public static HttpLoggingInterceptor setLog() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(TAG, "log: 日志拦截器 " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
    }

    //设置头部拦截
    public static Interceptor setHead() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //获取原始的originalRequest
                Request originalRequest = chain.request();
                //获取老的url
                HttpUrl oldUrl = originalRequest.url();
                //获取originalRequest的创建者builder
                Request.Builder builder = originalRequest.newBuilder();
                //获取头信息的集合如：manage,mdffx
                List<String> urlnameList = originalRequest.headers("flag");
                if (urlnameList != null && urlnameList.size() > 0) {
                    //删除原有配置中的值,就是namesAndValues集合里的值
                    builder.removeHeader("flag");
                    //获取头信息中配置的value,如：manage或者mdffx
                    String urlname = urlnameList.get(0);
                    HttpUrl baseURL=null;
                    //根据头信息中配置的value,来匹配新的base_url地址
                    if ("login".equals(urlname)) {
                        baseURL = HttpUrl.parse(CPoolUtil.BASE_API);
                    } else if ("video".equals(urlname)) {
                        baseURL = HttpUrl.parse(CPoolUtil.BASE_VIDEO);
                    }
                    //重建新的HttpUrl，需要重新设置的url部分
                    HttpUrl newHttpUrl = oldUrl.newBuilder()
                            .scheme(baseURL.scheme())//http协议如：http或者https
                            .host(baseURL.host())//主机地址
                            .port(baseURL.port())//端口
                            .build();
                    //获取处理后的新newRequest
                    Request newRequest = builder.url(newHttpUrl).build();
                    return  chain.proceed(newRequest);
                }else{
                    return chain.proceed(originalRequest);
                }


            }
        };
    }

    /**
     * OkHttpClient 创建
     */
    public static OkHttpClient initClient() {
        return new OkHttpClient.Builder()
                //日志拦截器
                .addInterceptor(setLog())
                //处理头部拦截器
                .addInterceptor(setHead())
                //连接时间 10秒
                .connectTimeout(20, TimeUnit.SECONDS)
                //超时时间 10秒
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }


}

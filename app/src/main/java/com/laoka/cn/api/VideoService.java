package com.laoka.cn.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * @author 张海洋
 * @Date on 2019/03/25.
 * @org 上海..科技有限公司
 * @describe
 */
public interface VideoService {

    /**
     * 登陆   表单请求
     */
    @Headers({DOMAIN_NAME_HEADER + Api.VIDEO_DOMAIN_NAME})
    @GET("/tabs/selected")
    Observable<String> getVideoData(@QueryMap Map<String, Object> mapsLogin);





}

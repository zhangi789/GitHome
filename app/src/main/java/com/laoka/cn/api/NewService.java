package com.laoka.cn.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * @author 张海洋
 * @Date on 2019/04/12.
 * @org 上海..科技有限公司
 * @describe
 */
public interface NewService {
    /**
     * 获得列表
     */
    @Headers({DOMAIN_NAME_HEADER + Api.NEW_INFO_DOMAIN_NAME})
    @GET("/api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752")
    Observable<String> getNewData(@QueryMap Map<String, Object> mapsLogin);


    @Headers({DOMAIN_NAME_HEADER + Api.NEW_INFO_DOMAIN_NAME})
    @GET("/api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752")
    Observable<String> getNewListData(@QueryMap Map<String, Object> mapsLogin);
}

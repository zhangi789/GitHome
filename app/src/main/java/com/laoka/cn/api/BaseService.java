package com.laoka.cn.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author 张海洋
 * @Date on 2019/03/21.
 * @org 上海..科技有限公司
 * @describe
 */
public interface BaseService {
    /**
     * 登陆    表单请求
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<String> getLogin(@FieldMap Map<String, Object> mapsLogin);

    /**
     * 首页
     * 获得图表List
     */
    @FormUrlEncoded
    @POST("user/getElectricByDepartmentOrStaff")
    Observable<String> getPageData(@FieldMap Map<String, Object> mapsLogin);


}




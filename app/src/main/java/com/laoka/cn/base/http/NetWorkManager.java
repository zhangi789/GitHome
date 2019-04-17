/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.laoka.cn.base.http;

import com.laoka.cn.api.Api;
import com.laoka.cn.api.BaseService;
import com.laoka.cn.api.NewService;
import com.laoka.cn.api.VideoInfoService;
import com.laoka.cn.api.VideoService;

import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * ================================================
 * Created by JessYan on 18/07/2017 17:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class NetWorkManager {
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private BaseService mBaseApiService;
    private VideoService mVideoApiService;
    private VideoInfoService mVideoInfoAipService;


    private NewService mNewApiService;

    private static class NetWorkManagerHolder {
        private static final NetWorkManager INSTANCE = new NetWorkManager();
    }

    public static final NetWorkManager getInstance() {
        return NetWorkManagerHolder.INSTANCE;
    }

    private NetWorkManager() {
        this.mOkHttpClient = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder()) //RetrofitUrlManager 初始化
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(Api.APP_DEFAULT_DOMAIN)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用Rxjava
                .addConverterFactory(ScalarsConverterFactory.create()) //防止String出错
                .addConverterFactory(GsonConverterFactory.create())//使用Gson
                .client(mOkHttpClient)
                .build();

        this.mBaseApiService = mRetrofit.create(BaseService.class);
        this.mVideoApiService = mRetrofit.create(VideoService.class);
        this.mVideoInfoAipService = mRetrofit.create(VideoInfoService.class);
        this.mNewApiService = mRetrofit.create(NewService.class);
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public BaseService getmBaseApiService() {
        return mBaseApiService;
    }

    public VideoService getmVideoApiService() {
        return mVideoApiService;
    }

    public VideoInfoService getmVideoInfoAipService() {
        return mVideoInfoAipService;
    }

    public NewService getmNewApiService() {
        return mNewApiService;
    }
}

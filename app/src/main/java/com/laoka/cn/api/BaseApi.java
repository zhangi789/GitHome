package com.laoka.cn.api;

import android.support.multidex.MultiDexApplication;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * @author 张海洋
 * @Date on 2019/03/21.
 * @org 上海..科技有限公司
 * @describe
 */
public class BaseApi extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitUrlManager.getInstance().setDebug(true);
        //将每个 BaseUrl 进行初始化,运行时可以随时改变 DOMAIN_NAME 对应的值,从而达到切换 BaseUrl 的效果
        RetrofitUrlManager.getInstance().putDomain(Api.VIDEO_DOMAIN_NAME, Api.APP_VIDEO_DOMAIN);
        RetrofitUrlManager.getInstance().putDomain(Api.VIDEO_INFO_DOMAIN_NAME, Api.APP_INFO_VIDEO_DOMAIN);

//      RetrofitUrlManager.getInstance().putDomain(GITHUB_DOMAIN_NAME, APP_GITHUB_DOMAIN);


    }
}

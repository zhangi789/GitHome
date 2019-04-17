package com.laoka.cn.util;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.laoka.cn.bean.Channel;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 张海洋
 * @Date on 2019/03/21.
 * @org 上海..科技有限公司
 * @describe
 */
public class BaseUtil {
    /**
     * 转化用于Activity
     *
     * @param observable
     * @param lifecycleProvider
     * @param <T>
     * @return
     */
    public static <T> Observable<T> observeat(Observable<T> observable, LifecycleProvider<ActivityEvent> lifecycleProvider) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(lifecycleProvider.<T>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread()); //指定在主线程中
    }

    /**
     * 转化用于Fragment
     *
     * @param observable
     * @param lifecycleProvider
     * @param <T>
     * @return
     */
    public static <T> Observable<T> observefg(Observable<T> observable, LifecycleProvider<FragmentEvent> lifecycleProvider) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(lifecycleProvider.<T>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread()); //指定在主线程中
    }


    /**
     * 根据秒速获取时间格式
     */
    public static String gennerTime(int totalSeconds) {
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 虚拟像素
     * @return 像素
     */
    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    public double getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * dp转 px.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int dp2px(float value, Context context) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }


    /**
     * px转dp.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int px2dp(float value, Context context) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) ((value * 160) / scale + 0.5f);
    }

    /**
     * sp转px.
     *
     * @param value the value
     * @return the int
     */
    public static int sp2px(float value, Context context) {
        Resources r;
        if (context == null) {
            r = Resources.getSystem();
        } else {
            r = context.getResources();
        }
        float spvalue = value * r.getDisplayMetrics().scaledDensity;
        return (int) (spvalue + 0.5f);
    }


    /**
     * px转sp.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int px2sp(float value, Context context) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value / scale + 0.5f);
    }


    //转化成对象
    public static <T> T toBean(String result, Class<T> beanClass) {
        Gson gson = new Gson();
        T t = gson.fromJson(result, beanClass);
        return t;
    }

    /**
     * 转化成字符串
     *
     * @param o
     * @return
     */
    public static String toJson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);


    }

    public static boolean isEmpty(List list){
        if (list == null){
            return true;
        }
        return list.size() == 0;
    }





}

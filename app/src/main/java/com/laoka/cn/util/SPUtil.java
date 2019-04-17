package com.laoka.cn.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author 张海洋
 * @Date on 2019/04/16.
 * @org 上海..科技有限公司
 * @describe 本地存储
 */
public class SPUtil {

    public static SharedPreferences mSp;
    public static String SP_SPLASH = "headlines";

    //存储数据
    public static void putString(Context context, String key, String value) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_SPLASH, Context.MODE_PRIVATE);
        }

        mSp.edit().putString(key, value).commit();
    }

    //或得数据
    public static String getString(Context context, String key, String defValue) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_SPLASH, Context.MODE_PRIVATE);
        }
        return mSp.getString(key, defValue);
    }


}

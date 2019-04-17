package com.laoka.cn.enter.sett;

import com.laoka.cn.base.mvp.IModel;
import com.laoka.cn.base.mvp.IView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author 张海洋
 * @Date on 2019/04/11.
 * @org 上海..科技有限公司
 * @describe
 */
public class SettContract {

    interface View extends IView {
        //上传图片反馈
        void uploadPicSuccess(boolean isSuccess);
    }

    interface Model extends IModel {
        // 上传图片
        // Observable<String> getLogin(Map<String, Object> mapsLogin, LifecycleProvider<FragmentEvent> lifecycleProvider);
        //修改操作


    }
}

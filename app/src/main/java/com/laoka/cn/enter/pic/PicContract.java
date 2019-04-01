package com.laoka.cn.enter.pic;

import com.laoka.cn.base.mvp.IModel;
import com.laoka.cn.base.mvp.IView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @author 张海洋
 * @Date on 2019/03/21.
 * @org 上海..科技有限公司
 * @describe
 */
public interface PicContract {

    interface View extends IView {
        //更新图表
        void updataChart(boolean mIsFrist, String msg);
    }

    interface Model extends IModel {
        // 获得首页数据
        Observable<String> getLogin(Map<String, Object> mapsLogin, LifecycleProvider<FragmentEvent> lifecycleProvider);
    }
}

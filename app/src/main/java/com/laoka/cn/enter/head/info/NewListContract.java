package com.laoka.cn.enter.head.info;

import com.laoka.cn.base.mvp.IModel;
import com.laoka.cn.base.mvp.IView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @author 张海洋
 * @Date on 2019/04/16.
 * @org 上海..科技有限公司
 * @describe
 */
public interface NewListContract {

    interface View extends IView {
        //更新图表
        void upDataNewListData(String info);
    }
    interface Model extends IModel {
        // 获得新闻列表数据
        Observable<String> getNewListData(Map<String, Object> mapsLogin, LifecycleProvider<FragmentEvent> lifecycleProvider);
    }

}

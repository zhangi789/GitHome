package com.laoka.cn.enter.video;

import com.laoka.cn.base.mvp.IModel;
import com.laoka.cn.base.mvp.IView;
import com.laoka.cn.bean.ItemListBean;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author 张海洋
 * @Date on 2019/03/22.
 * @org 上海..科技有限公司
 * @describe
 */
public interface VideoContract {

    interface View extends IView {
        //更新视频Video

        void upDateVideo(int type,List<ItemListBean> videoList,String date);

    }

    interface Model extends IModel {
        // 获得视频数据
        Observable<String> getVideoData(Map<String, Object> mapsLogin, LifecycleProvider<FragmentEvent> lifecycleProvider);

    }
}

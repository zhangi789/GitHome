package com.laoka.cn.enter.video;

import com.laoka.cn.base.http.NetWorkManager;
import com.laoka.cn.base.mvp.BaseModel;
import com.laoka.cn.util.BaseUtil;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 张海洋
 * @Date on 2019/03/22.
 * @org 上海..科技有限公司
 * @describe
 */
public class VideoModel extends BaseModel implements VideoContract.Model {

    @Override
    public Observable<String> getVideoData(Map<String, Object> mapsLogin, LifecycleProvider<FragmentEvent> lifecycleProvider) {
        return BaseUtil.observefg(NetWorkManager.getInstance().getmVideoApiService().getVideoData(mapsLogin), lifecycleProvider);

    }
}

package com.laoka.cn.enter.pic;

import com.laoka.cn.base.http.NetWorkManager;
import com.laoka.cn.base.mvp.BaseModel;
import com.laoka.cn.util.BaseUtil;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @author 张海洋
 * @Date on 2019/03/21.
 * @org 上海..科技有限公司
 * @describe
 */
public class PicModel extends BaseModel implements PicContract.Model {
    @Override
    public Observable<String> getLogin(Map<String, Object> mapsLogin, LifecycleProvider<FragmentEvent> lifecycleProvider) {
        return BaseUtil.observefg(NetWorkManager.getInstance().getmBaseApiService().getLogin(mapsLogin), lifecycleProvider);
    }
}

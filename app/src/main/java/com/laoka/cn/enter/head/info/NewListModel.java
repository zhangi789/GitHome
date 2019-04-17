package com.laoka.cn.enter.head.info;

import com.laoka.cn.base.http.NetWorkManager;
import com.laoka.cn.base.mvp.BaseModel;
import com.laoka.cn.util.BaseUtil;
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
public class NewListModel extends BaseModel implements NewListContract.Model {
    @Override
    public Observable<String> getNewListData(Map<String, Object> mapsLogin, LifecycleProvider<FragmentEvent> lifecycleProvider) {
        return BaseUtil.observefg(NetWorkManager.getInstance().getmNewApiService().getNewListData(mapsLogin), lifecycleProvider);
    }
}

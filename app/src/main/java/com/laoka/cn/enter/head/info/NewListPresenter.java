package com.laoka.cn.enter.head.info;

import android.util.Log;

import com.laoka.cn.base.mvp.BasePresenter;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 张海洋
 * @Date on 2019/04/16.
 * @org 上海..科技有限公司
 * @describe
 */
public class NewListPresenter extends BasePresenter<NewListContract.Model,NewListContract.View> {
    @Override
    protected NewListContract.Model createModel() {
        return new NewListModel();
    }



    public void getNewListData(boolean isFrist, Map<String, Object> mapsLogin, LifecycleProvider<FragmentEvent> lifecycleProvider) {
        getModel().getNewListData(mapsLogin, lifecycleProvider).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(String s) {
                getView().upDataNewListData(s);
                Log.i("GGG", "结果 " + "\r\n" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }




}

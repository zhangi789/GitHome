package com.laoka.cn.enter.pic;

import android.util.Log;

import com.laoka.cn.base.mvp.BasePresenter;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 张海洋
 * @Date on 2019/03/21.
 * @org 上海..科技有限公司
 * @describe
 */
public class PicPresenter extends BasePresenter<PicContract.Model, PicContract.View> {
    @Override
    protected PicContract.Model createModel() {
        return new PicModel();
    }

    public void getLogin(Map<String, Object> mapsLogin, LifecycleProvider<FragmentEvent> lifecycleProvider) {
        getModel().getLogin(mapsLogin, lifecycleProvider)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("GGG", "s " + s);
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

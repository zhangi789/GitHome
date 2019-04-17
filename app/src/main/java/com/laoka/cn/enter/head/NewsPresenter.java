package com.laoka.cn.enter.head;

import android.util.Log;

import com.laoka.cn.base.mvp.BasePresenter;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 张海洋
 * @Date on 2019/04/12.
 * @org 上海..科技有限公司
 * @describe
 */
public class NewsPresenter extends BasePresenter<NewsContract.Model, NewsContract.View> {
    @Override
    protected NewsContract.Model createModel() {
        return new NewsModel();
    }

    public void getNewData(boolean isFrist, Map<String, Object> mapsLogin, LifecycleProvider<FragmentEvent> lifecycleProvider) {
        getModel().getNewData(mapsLogin, lifecycleProvider).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {


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

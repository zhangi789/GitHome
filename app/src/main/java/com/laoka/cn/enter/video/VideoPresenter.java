package com.laoka.cn.enter.video;

import android.util.Log;

import com.google.gson.Gson;
import com.laoka.cn.base.mvp.BasePresenter;
import com.laoka.cn.bean.ItemListBean;
import com.laoka.cn.bean.VideoListBean;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 张海洋
 * @Date on 2019/03/22.
 * @org 上海..科技有限公司
 * @describe
 */
public class VideoPresenter extends BasePresenter<VideoContract.Model, VideoContract.View> {
    @Override
    protected VideoContract.Model createModel() {
        return new VideoModel();
    }

    public void getVideoData(Map<String, Object> mapsLogin, LifecycleProvider<FragmentEvent> lifecycleProvider, int type) {
        getModel().getVideoData(mapsLogin, lifecycleProvider).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {
                String mDate = "";

                VideoListBean videoListBean = new Gson().fromJson(s, VideoListBean.class);
                //第一次加载
                if (videoListBean != null && videoListBean.getItemList() != null) {
                    List<ItemListBean> videoList = new ArrayList();
                    for (ItemListBean itemListBean : videoListBean.getItemList()) {
                        if (itemListBean != null && (itemListBean.getItemType() == 0 || itemListBean.getItemType() == 1)) {
                            videoList.add(itemListBean);
                        }
                    }
                    int end = videoListBean.getNextPageUrl().lastIndexOf("&num");
                    int start = videoListBean.getNextPageUrl().lastIndexOf("date=");
                    mDate = videoListBean.getNextPageUrl().substring(start + 5, end);
Log.i("GGG","mDate "+mDate);
                    if (type == 1) {
                        //第一次加载
                        getView().upDateVideo(1, videoList,mDate);
                    } else if (type == 2) {
                        // 下拉
                        getView().upDateVideo(2, videoList,mDate);
                    } else {
                        // 加载更多
                        getView().upDateVideo(3, videoList,mDate);
                    }

                }


            }

            @Override
            public void onError(Throwable e) {
                Log.i("GGG", "onError"
                        + "\r\n" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


}

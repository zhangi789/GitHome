package com.laoka.cn.enter.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoka.cn.R;
import com.laoka.cn.adapter.KaiYanOnlineVideoAdapter;
import com.laoka.cn.api.Api;
import com.laoka.cn.base.BaseMvpFragment;
import com.laoka.cn.bean.DataBean;
import com.laoka.cn.bean.ItemListBean;
import com.laoka.cn.enter.basic.VideoInfoActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.xfresh.cn.RxRefreshLayout;
import com.xfresh.cn.footer.LoadingView;
import com.xfresh.cn.header.SinaRefreshView;
import com.xfresh.cn.header.progresslayout.ProgressLayout;
import com.xfresh.cn.util.RefreshListenerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.HttpUrl;

/**
 * @author 张海洋
 * @Date on 2019/03/22.
 * @org 上海..科技有限公司
 * @describe
 */
public class VideoFragment extends BaseMvpFragment<VideoPresenter> implements VideoContract.View {
    @BindView(R.id.mRV)
    RecyclerView mRV;
    @BindView(R.id.mRxRefresh)
    RxRefreshLayout mRxRefresh;
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String mTitle;
    private String date;
    private LinearLayoutManager mLayoutManager;
    private KaiYanOnlineVideoAdapter mOnlineVideoAdapter;


    @Override
    protected VideoPresenter createPresenter() {
        return new VideoPresenter();
    }

    public static VideoFragment getInstance(String title, String date) {
        VideoFragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.mTitle = title;
        fragment.date = date;
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        tvTitle.setText("在线视频");
        Log.i("GGG", "lazyLoad");
        HttpUrl httpUrl = RetrofitUrlManager.getInstance().fetchDomain(Api.VIDEO_DOMAIN_NAME);
        if (httpUrl == null || !httpUrl.toString().equals(Api.APP_VIDEO_DOMAIN)) { //可以在 App 运行时随意切换某个接口的 BaseUrl
            RetrofitUrlManager.getInstance().putDomain(Api.VIDEO_DOMAIN_NAME, Api.APP_VIDEO_DOMAIN);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("date", "");
        mPresenter.getVideoData(map, this, 1);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.frag_video;
    }

    @Override
    protected void initView() {

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRV.setLayoutManager(mLayoutManager);


        ProgressLayout headerView = new ProgressLayout(mContext);
        mRxRefresh.setHeaderView(headerView);
        LoadingView loadingView = new LoadingView(mContext);
        mRxRefresh.setBottomView(loadingView);
        mRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
        mRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int fistVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                // 判断是否滚动超过一屏
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        GSYVideoManager.releaseAllVideos();
                        mOnlineVideoAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        mRxRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final RxRefreshLayout refreshLayout) {
                updata(2);
            }

            @Override
            public void onLoadMore(final RxRefreshLayout refreshLayout) {
                updata(3);
            }
        });
    }

    private void updata(int type) {
        HttpUrl httpUrl = RetrofitUrlManager.getInstance().fetchDomain(Api.VIDEO_DOMAIN_NAME);
        if (httpUrl == null || !httpUrl.toString().equals(Api.APP_VIDEO_DOMAIN)) { //可以在 App 运行时随意切换某个接口的 BaseUrl
            RetrofitUrlManager.getInstance().putDomain(Api.VIDEO_DOMAIN_NAME, Api.APP_VIDEO_DOMAIN);
        }
        Log.i("GGG","type "+type+" "+date);
        Map<String, Object> map = new HashMap<>();
        if (type==2){
            map.put("date", "");
        }else{
            map.put("date", date);
        }
        mPresenter.getVideoData(map, this, type);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void showInfo(String mInfo) {

    }

    @Override
    public void upDateVideo(int type, List<ItemListBean> videoList, String mdate) {
        date = mdate;
        if (type == 1) {
            mOnlineVideoAdapter = new KaiYanOnlineVideoAdapter(videoList, getActivity());
            mOnlineVideoAdapter.setOnItemClickLitener(new KaiYanOnlineVideoAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position, DataBean dataBean) {
                    Intent intent = new Intent(getActivity(), VideoInfoActivity.class);
                    intent.putExtra("playUrl", dataBean.getPlayUrl());
                    intent.putExtra("playTitle", dataBean.getTitle());
                    intent.putExtra("playDescription", dataBean.getDescription());
                    intent.putExtra("playPic", dataBean.getCover().getDetail());
                    getActivity().startActivity(intent);
                }
            });
            mRV.setAdapter(mOnlineVideoAdapter);
        } else if (type == 2) {
            mRxRefresh.finishRefreshing();
        } else {
            mOnlineVideoAdapter.addData(videoList);
            mOnlineVideoAdapter.notifyDataSetChanged();
            mRxRefresh.finishLoadmore();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
        } else {
            GSYVideoManager.releaseAllVideos();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.releaseAllVideos();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
            GSYVideoManager.releaseAllVideos();
        }
    }

    public boolean onBackPressed() {
        return GSYVideoManager.backFromWindowFull(mContext);
    }


    public double getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

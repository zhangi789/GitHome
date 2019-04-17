package com.laoka.cn.enter.head.info;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.laoka.cn.R;
import com.laoka.cn.adapter.NewsAdapter;
import com.laoka.cn.api.Api;
import com.laoka.cn.base.BaseMvpFragment;
import com.laoka.cn.bean.News;
import com.laoka.cn.bean.NewsData;
import com.laoka.cn.bean.NewsResponse;
import com.laoka.cn.util.BaseUtil;
import com.laoka.cn.util.Constant;
import com.laoka.cn.view.TipView;
import com.shuyu.gsyvideoplayer.utils.NetworkUtils;
import com.xfresh.cn.RxRefreshLayout;
import com.xfresh.cn.footer.LoadingView;
import com.xfresh.cn.header.progresslayout.ProgressLayout;
import com.xfresh.cn.util.RefreshListenerAdapter;

import java.util.ArrayList;
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
 * @Date on 2019/04/16.
 * @org 上海..科技有限公司
 * @describe
 */
public class NewListFragment extends BaseMvpFragment<NewListPresenter> implements NewListContract.View {
    @BindView(R.id.mTipView)
    TipView mTipView;
    @BindView(R.id.mRv)
    RecyclerView mRv;
    @BindView(R.id.mRx)
    RxRefreshLayout mRx;
    Unbinder unbinder;

    private String category = "";
    private boolean isRecommendChannel;//是否是推荐频道
    long lastTime;
    private boolean isVideoList;//是否是视频列表页面,根据判断频道号是否是视频

    private NewsAdapter mNewAdapter;

    private boolean isFrist = false;

    @Override
    protected NewListPresenter createPresenter() {
        return new NewListPresenter();
    }
    @Override
    protected void lazyLoad() {
        setLoadData();
        Log.i("NNN", "lazyLoad--  当前页面---");
    }

    private void setLoadData() {
        HttpUrl httpUrl = RetrofitUrlManager.getInstance().fetchDomain(Api.NEW_INFO_DOMAIN_NAME);
        if (httpUrl == null || !httpUrl.toString().equals(Api.APP_INFO_NEWS_DOMAIN)) { //可以在 App 运行时随意切换某个接口的 BaseUrl
            RetrofitUrlManager.getInstance().putDomain(Api.NEW_INFO_DOMAIN_NAME, Api.APP_INFO_NEWS_DOMAIN);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        map.put("min_behot_time", "1555032749");
        map.put("last_refresh_sub_entrance_interval", "1555032749 ");
        mPresenter.getNewListData(true, map, this);
        String[] channelCodes = getResources().getStringArray(R.array.channel_code);
        isRecommendChannel = category.equals(channelCodes[0]);//是否是推荐频道
        if (!NetworkUtils.isAvailable(getActivity())) {
            //网络不可用弹出提示
            mTipView.show();
            return;
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.frag_new_info;
    }

    @Override
    protected void initView() {
        ProgressLayout headerView = new ProgressLayout(getActivity());
        mRx.setHeaderView(headerView);
        LoadingView loadingView = new LoadingView(getActivity());
        mRx.setBottomView(loadingView);
        mRv.setLayoutManager(new GridLayoutManager(getActivity(), 1));

    }

    @Override
    protected void initData() {
        Log.i("NNN", "initData--");
        // setLoadData();
        mRx.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final RxRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTipView.show("更新10条数据");
                        Log.i("zh", "------------------------");
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final RxRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void upDataNewListData(String response) {
        if (response == null) {
            return;
        }
        isFrist = true;
        lastTime = System.currentTimeMillis() / 1000;
        NewsResponse newsResponse = new Gson().fromJson(response, NewsResponse.class);
        List<NewsData> data = newsResponse.data;
        List<News> newsList = new ArrayList<>();
        if (!BaseUtil.isEmpty(data)) {
            for (NewsData newsData : data) {
                News news = new Gson().fromJson(newsData.content, News.class);
                if (news == null || (news.has_video && (news.video_detail_info == null || news.video_detail_info.video_id == null))) {
                    continue;
                }
                if (isVideoList && !news.has_video) {
                    continue;
                }
//                                LogUtil.e("zh","News +++++= " + news.toString());
                if (TextUtils.isEmpty(news.title)) {
                    //由于汽车、体育等频道第一条属于导航的内容，所以如果第一条没有标题，则移除
                    continue;
                }
                newsList.add(news);

                mNewAdapter = new NewsAdapter(getActivity(), category, isVideoList, newsList);
                mRv.setAdapter(mNewAdapter);
                mNewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                        Toast.makeText(mContext, "待处理", Toast.LENGTH_LONG).show();
                    }
                });

            }
        }
    }

    public static NewListFragment newInstance(String channelCode, boolean isVideoList) {
        Bundle args = new Bundle();
        args.putString(Constant.CHANNEL_CODE, channelCode);
        args.putBoolean(Constant.IS_VIDEO_LIST, isVideoList);
        NewListFragment fragment = new NewListFragment();
        fragment.setArguments(args);
        fragment.category = channelCode;
        fragment.isVideoList = isVideoList;
        return fragment;
    }


    @Override
    public void showInfo(String mInfo) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("NNN","onResume--");
    }
}

package com.laoka.cn.enter.head.info;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laoka.cn.R;
import com.laoka.cn.api.Api;
import com.laoka.cn.base.BaseMvpFragment;
import com.laoka.cn.base.mvp.IPresenter;
import com.laoka.cn.util.Constant;
import com.laoka.cn.view.TipView;
import com.xfresh.cn.RxRefreshLayout;
import com.xfresh.cn.footer.LoadingView;
import com.xfresh.cn.header.progresslayout.ProgressLayout;
import com.xfresh.cn.util.RefreshListenerAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.HttpUrl;

/**
 * @author 张海洋
 * @Date on 2019/04/15.
 * @org 上海..科技有限公司
 * @describe
 */
public class NewInfoFragment extends BaseMvpFragment {
    @BindView(R.id.mTipView)
    TipView mTipView;
    @BindView(R.id.mRv)
    RecyclerView mRv;
    @BindView(R.id.mRx)
    RxRefreshLayout mRx;
    Unbinder unbinder;

    @Override
    protected IPresenter createPresenter() {
        return null;
    }
    @Override
    protected void lazyLoad() {

    }

    public static NewInfoFragment newInstance(String channelCode, boolean isVideoList) {
        Bundle args = new Bundle();
        args.putString(Constant.CHANNEL_CODE, channelCode);
        args.putBoolean(Constant.IS_VIDEO_LIST, isVideoList);
        NewInfoFragment fragment = new NewInfoFragment();
        fragment.setArguments(args);
        return fragment;
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
    }

    @Override
    protected void initData() {
        mRx.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final RxRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      mTipView.show();
                      refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final RxRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTipView.show("哈哈");
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
    public void showInfo(String mInfo) {

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
        super.onDestroyView();
        unbinder.unbind();
    }
}

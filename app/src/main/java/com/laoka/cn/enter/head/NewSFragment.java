package com.laoka.cn.enter.head;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.laoka.cn.R;
import com.laoka.cn.adapter.ChannelPagerAdapter;
import com.laoka.cn.base.BaseMvpFragment;
import com.laoka.cn.bean.Channel;
import com.laoka.cn.enter.head.info.NewListFragment;
import com.laoka.cn.enter.head.manager.ChannelDialogFragment;
import com.laoka.cn.util.BaseUtil;
import com.laoka.cn.util.Constant;
import com.laoka.cn.util.OnChannelListener;
import com.laoka.cn.util.SPUtil;
import com.laoka.cn.view.CircleImageView;
import com.laoka.cn.view.tab.ColorTrackTabLayout;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author 张海洋
 * @Date on 2019/04/12.
 * @org 上海..科技有限公司
 * @describe 仿今日头条新闻刷新
 */
public class NewSFragment extends BaseMvpFragment<NewsPresenter> implements NewsContract.View,OnChannelListener {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    Unbinder unbinder;
    @BindView(R.id.mColorTab)
    ColorTrackTabLayout mTabLayout;
    @BindView(R.id.mTouIV)
    CircleImageView mTouIV;
    @BindView(R.id.mTouAdd)
    ImageView mTouAdd;
    @BindView(R.id.mTouIVSearch)
    ImageView mTouIVSearch;
    @BindView(R.id.mTouManager)
    RelativeLayout mTouManager;
    private String mTitle;
    private List<NewListFragment> mNewsFragmentList = new ArrayList<>();
    private List<Channel> mSelectedChannels = new ArrayList<>();
    private List<Channel> mUnSelectedChannels = new ArrayList<>();
    private ChannelPagerAdapter mChannelPagerAdapter;
    private String[] mChannelCodes;
    private Gson mGson = new Gson();
    @Override
    protected NewsPresenter createPresenter() {
        return new NewsPresenter();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.frag_new;
    }

    @Override
    protected void initView() {
        initChannelData();
        initChannelFragments();
    }

    /**
     * 初始化已选频道和未选频道的数据
     */
    private void initChannelData() {
        String selectedChannelJson = SPUtil.getString(mContext, Constant.SELECTED_CHANNEL_JSON, "");
        String unselectChannel = SPUtil.getString(mContext, Constant.UNSELECTED_CHANNEL_JSON, "");
        if (TextUtils.isEmpty(selectedChannelJson) || TextUtils.isEmpty(unselectChannel)) {
            String[] channels = getResources().getStringArray(R.array.channel);
            String[] channelCodes = getResources().getStringArray(R.array.channel_code);
            //添加全部频道
            for (int i = 0; i < channelCodes.length; i++) {
                String title = channels[i];
                String code = channelCodes[i];
                mSelectedChannels.add(new Channel(title, code));
            }
            selectedChannelJson = BaseUtil.toJson(mSelectedChannels);
            SPUtil.putString(mContext, Constant.SELECTED_CHANNEL_JSON, selectedChannelJson);//保存到sp
        } else {
            //之前添加过
            Gson gson = new Gson();
            List<Channel> selectedChannel = gson.fromJson(selectedChannelJson, new TypeToken<List<Channel>>() {
            }.getType());
            List<Channel> unselectedChannel = gson.fromJson(unselectChannel, new TypeToken<List<Channel>>() {
            }.getType());
            mSelectedChannels.addAll(selectedChannel);
            mUnSelectedChannels.addAll(unselectedChannel);
        }
    }

    /**
     * 初始化已选频道的fragment的集合
     */
    private void initChannelFragments() {
        mChannelCodes = getResources().getStringArray(R.array.channel_code);
        for (Channel channel : mSelectedChannels) {
            NewListFragment newsFragment = NewListFragment.newInstance(channel.channelCode, channel.channelCode.equals(mChannelCodes[1]));
            mNewsFragmentList.add(newsFragment);//添加到集合中
        }
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {
        String[] channelCodes = getResources().getStringArray(R.array.channel_code);
        mChannelPagerAdapter = new ChannelPagerAdapter(mNewsFragmentList, mSelectedChannels, channelCodes, getChildFragmentManager());
        mViewPager.setAdapter(mChannelPagerAdapter);
        mViewPager.setOffscreenPageLimit(mSelectedChannels.size());
        mTabLayout.setTabPaddingLeftAndRight(BaseUtil.dp2px(10, getActivity()), BaseUtil.dp2px(10, getActivity()));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                //设置最小宽度，使其可以在滑动一部分距离
                ViewGroup slidingTabStrip = (ViewGroup) mTabLayout.getChildAt(0);
                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + mTouManager.getMeasuredWidth());
            }
        });
        //隐藏指示器
        mTabLayout.setSelectedTabIndicatorHeight(0);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当页签切换的时候，如果有播放视频，则释放资源
                GSYVideoManager.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void upDataNewData(String info) {

    }

    @Override
    public void showInfo(String mInfo) {

    }

    public static NewSFragment getInstance(String title) {
        NewSFragment fragment = new NewSFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.mTitle = title;
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.mTouIV, R.id.mTouAdd, R.id.mTouManager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mTouIV:
                break;
            case R.id.mTouAdd:
                break;
            case R.id.mTouManager:

                ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance(mSelectedChannels, mUnSelectedChannels);
                dialogFragment.setOnChannelListener(this);
                dialogFragment.show(getChildFragmentManager(), "CHANNEL");
                dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });



                break;
        }
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
        listMove(mSelectedChannels, starPos, endPos);
        listMove(mNewsFragmentList, starPos, endPos);
    }

    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {
        //移动到我的频道
        Channel channel = mUnSelectedChannels.remove(starPos);
        mSelectedChannels.add(endPos, channel);
        NewListFragment newsFragment = NewListFragment.newInstance(channel.channelCode, channel.channelCode.equals(mChannelCodes[1]));
        mNewsFragmentList.add(newsFragment);
    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {
        //移动到推荐频道
        mUnSelectedChannels.add(endPos, mSelectedChannels.remove(starPos));
        mNewsFragmentList.remove(starPos);
    }



    private void listMove(List datas, int starPos, int endPos) {
        Object o = datas.get(starPos);
        //先删除之前的位置
        datas.remove(starPos);
        //添加到现在的位置
        datas.add(endPos, o);
    }
}

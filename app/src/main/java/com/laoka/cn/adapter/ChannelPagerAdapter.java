package com.laoka.cn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.laoka.cn.bean.Channel;
import com.laoka.cn.enter.head.info.NewInfoFragment;
import com.laoka.cn.enter.head.info.NewListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张海洋
 * @Date on 2019/04/15.
 * @org 上海..科技有限公司
 * @describe
 */
public class ChannelPagerAdapter extends FragmentStatePagerAdapter {
    private List<NewListFragment> mFragments;
    private List<Channel> mChannels;

    public ChannelPagerAdapter(List<NewListFragment> fragmentList, List<Channel> channelList, String[] channelCodes, FragmentManager fm) {
        super(fm);
        mFragments = fragmentList != null ? fragmentList : new ArrayList();
        mChannels = channelList != null ? channelList : new ArrayList();

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mChannels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels.get(position).title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}

package com.laoka.cn;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.laoka.cn.base.BaseActivity;
import com.laoka.cn.enter.head.NewSFragment;
import com.laoka.cn.enter.pic.PicContract;
import com.laoka.cn.enter.pic.PicFragment;
import com.laoka.cn.enter.sett.SettFragment;
import com.laoka.cn.enter.video.VideoFragment;
import com.laoka.cn.view.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.content_layout)
    FrameLayout contentLayout;
    @BindView(R.id.tab_view)
    BottomNavigationView mBottomNavigation;

    private PicContract mPicFragment;
    private List<Fragment> fragments;
    private int lastIndex;

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(PicFragment.getInstance("图片"));
        fragments.add(VideoFragment.getInstance("在线视频", ""));
        // fragments.add(PicFragment.getInstance("图片"));
        fragments.add(NewSFragment.getInstance("头条"));
        fragments.add(SettFragment.getInstance("设置"));
    }

    @Override
    protected void initView() {
        initFragment();
        selectFragment(0);
        mBottomNavigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
    }
    @Override
    protected void initData() {
        initNavigation();
    }

    @Override
    protected void initListener() {

    }


    /**
     * 切换Fragment
     *
     * @param position
     */
    private void selectFragment(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = fragments.get(position);
        Fragment lastFragment = fragments.get(lastIndex);
        lastIndex = position;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.content_layout, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }


    /**
     * bottom选择器
     */
    private void initNavigation() {
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_pic:
                    selectFragment(0);
                    break;
                case R.id.tab_video:

                    selectFragment(1);
                    break;
                case R.id.tab_news:
                    selectFragment(2);
                    break;
                case R.id.tab_my:
                    selectFragment(3);
                    break;

            }
            return true;
        });
    }

}

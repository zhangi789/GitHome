package com.laoka.cn.enter.pic;

import android.os.Bundle;
import android.widget.TextView;

import com.laoka.cn.R;
import com.laoka.cn.base.BaseMvpFragment;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 张海洋
 * @Date on 2019/03/21.
 * @org 上海..科技有限公司
 * @describe
 */
public class PicFragment extends BaseMvpFragment<PicPresenter> implements PicContract.View {
    private String mTitle;

    private TextView ms;

    @Override
    protected PicPresenter createPresenter() {
        return new PicPresenter();
    }

    public static PicFragment getInstance(String title) {
        PicFragment fragment = new PicFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.mTitle = title;
        return fragment;
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.frag_pic;
    }

    @Override
    protected void initView() {
        ms = mRootView.findViewById(R.id.tv_show);
    }

    @Override
    protected void initData() {
        ms.setText(mTitle);
        Map<String, Object> map = new HashMap<>();
        map.put("account", "13965685480");
        map.put("password", "123456");
        mPresenter.getLogin(map, this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void showInfo(String mInfo) {

    }

    @Override
    public void updataChart(boolean mIsFrist, String msg) {

    }
}

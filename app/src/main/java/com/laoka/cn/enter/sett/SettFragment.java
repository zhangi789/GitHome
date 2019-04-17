package com.laoka.cn.enter.sett;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.laoka.cn.R;
import com.laoka.cn.base.BaseMvpFragment;
import com.laoka.cn.enter.pic.PicFragment;

/**
 * @author 张海洋
 * @Date on 2019/04/11.
 * @org 上海..科技有限公司
 * @describe
 */
public class SettFragment extends BaseMvpFragment<SettPresenter> implements SettContract.View {

    private String mTitle;

    private ImageView iv_user;

    @Override
    protected SettPresenter createPresenter() {
        return new SettPresenter();
    }

    public static SettFragment getInstance(String title) {
        SettFragment fragment = new SettFragment();
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
        return R.layout.frag_sett;
    }

    @Override
    protected void initView() {
        iv_user=(ImageView) mRootView.findViewById(R.id.iv_user);

      /*  MultiTransformation mation5 = new MultiTransformation(
                new RoundedCorners(200,0,RoundedCorners.CornerType.ALL));*/


        RequestOptions mRequestOptions = RequestOptions.
                circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存

        Glide.with(mContext).load(R.mipmap.wei_user).apply(new RequestOptions()
                .transforms(new CenterCrop(), new RoundedCorners((int) getResources().getDimension(R.dimen.x16))
                ))
                .into(iv_user);







    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void uploadPicSuccess(boolean isSuccess) {

    }

    @Override
    public void showInfo(String mInfo) {

    }
}

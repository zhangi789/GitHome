package com.laoka.cn.enter.basic;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laoka.cn.R;
import com.laoka.cn.base.BaseActivity;
import com.laoka.cn.view.video.SampleCoverVideo;

import butterknife.BindView;

/**
 * @author 张海洋
 * @Date on 2019/03/27.
 * @org 上海..科技有限公司
 * @describe
 */
public class VideoInfoActivity extends BaseActivity {
    @BindView(R.id.detail_player)
    SampleCoverVideo detailPlayer;
    @BindView(R.id.video_name)
    TextView mVideoName;
    @BindView(R.id.video_dec)
    TextView mVideoDec;
    @BindView(R.id.post_detail_nested_scroll)
    NestedScrollView postDetailNestedScroll;
    @BindView(R.id.activity_detail_player)
    LinearLayout activityDetailPlayer;

    public String playUrl;
    public String playTitle;
    public String playDescription;
    public String playPic;
    public String playId;

    @Override
    protected void getIntent(Intent intent) {
        playUrl = intent.getStringExtra("playUrl");
        playTitle =intent.getStringExtra("playTitle");
        playDescription = intent.getStringExtra("playDescription");
        playPic =intent.getStringExtra("playPic");
        playId = intent.getStringExtra("playId");
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_player;
    }

    @Override
    protected void initView() {
        detailPlayer.getTitleTextView().setTextSize(16);
        detailPlayer.loadCoverImage(playPic + "", 0);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

}

package com.laoka.cn.view.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.laoka.cn.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;
import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;

/**
 * @author 张海洋
 * @Date on 2019/04/16.
 * @org 上海..科技有限公司
 * @describe
 */
public class ColorTrackTabLayout extends TabLayout {

    private int tabTextSize;//每个tab字体大小
    private int tabSelectedTextColor;//每个tab选中字体颜色
    private int tabTextColor;//每个tab未选中颜色
    private static final int INVALID_TAB_POS = -1;

    //最后的选中位置
    private int lastSelectedTabPosition = INVALID_TAB_POS;

    private ViewPager viewPager;//所绑定的viewpager

    private ColorClipTabLayoutOnPageChangeListener colorClipTabLayoutOnPageChangeListener;


    public ColorTrackTabLayout(Context context) {
        this(context, null);
    }

    public ColorTrackTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            // Text colors/sizes come from the text appearance first
            final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTabLayout);
            //Tab字体大小
            tabTextSize = ta.getDimensionPixelSize(R.styleable.ColorTrackTabLayout_text_size, 72);
            //Tab文字颜色
            tabTextColor = ta.getColor(R.styleable.ColorTrackTabLayout_text_unselected_color, Color.parseColor("#000000"));
            tabSelectedTextColor = ta.getColor(R.styleable.ColorTrackTabLayout_text_selected_color, Color.parseColor("#cc0000"));
            ta.recycle();
        }
    }

    @Override
    public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
        //通过addTab的方式将colorClipView作为customView传入tab
        ColorTrackView colorClipView = new ColorTrackView(getContext());
        colorClipView.setProgress(setSelected ? 1 : 0);
        colorClipView.setText(tab.getText() + "");
        colorClipView.setTextSize(tabTextSize);
        colorClipView.setTag(position);
        colorClipView.setTextSelectedColor(tabSelectedTextColor);
        colorClipView.setTextUnselectColor(tabTextColor);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        colorClipView.setLayoutParams(layoutParams);
        tab.setCustomView(colorClipView);
        super.addTab(tab, position, setSelected);
        int selectedTabPosition = getSelectedTabPosition();
        if ((selectedTabPosition == INVALID_TAB_POS && position == 0) || (selectedTabPosition == position)) {
            setSelectedView(position);
        }

        setTabWidth(position, colorClipView);
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager, boolean autoRefresh) {
        super.setupWithViewPager(viewPager, autoRefresh);
        try {
            if (viewPager != null)
                this.viewPager = viewPager;
            //通过反射找到mPageChangeListener
//            Field field = TabLayout.class.getDeclaredField("colorClipTabLayoutOnPageChangeListener");
//            field.setAccessible(true);
//            TabLayoutOnPageChangeListener listener = (TabLayoutOnPageChangeListener) field.get(this);
//            if (listener != null) {
//                //删除自带监听
//                viewPager.removeOnPageChangeListener(listener);
            colorClipTabLayoutOnPageChangeListener = new ColorClipTabLayoutOnPageChangeListener(this);
            colorClipTabLayoutOnPageChangeListener.reset();
            viewPager.addOnPageChangeListener(colorClipTabLayoutOnPageChangeListener);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAllTabs() {
        lastSelectedTabPosition = getSelectedTabPosition();
        super.removeAllTabs();
    }

    @Override
    public int getSelectedTabPosition() {
        final int selectedTabPositionAtParent = super.getSelectedTabPosition();
        return selectedTabPositionAtParent == INVALID_TAB_POS ?
                lastSelectedTabPosition : selectedTabPositionAtParent;
    }

    public void setLastSelectedTabPosition(int lastSelectedTabPosition) {
        lastSelectedTabPosition = lastSelectedTabPosition;
    }

    public void setCurrentItem(int position) {
        if (viewPager != null)
            viewPager.setCurrentItem(position);
    }

    private void setTabWidth(int position, ColorTrackView colorClipView) {
        ViewGroup slidingTabStrip = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) slidingTabStrip.getChildAt(position);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        int w = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        //手动测量一下
        colorClipView.measure(w, h);
        params.width = colorClipView.getMeasuredWidth() + tabView.getPaddingLeft() + tabView.getPaddingRight();
        //设置tabView的宽度
        tabView.setLayoutParams(params);
    }

    private void setSelectedView(int position) {
        final int tabCount = getTabCount();
        if (position < tabCount) {
            for (int i = 0; i < tabCount; i++) {
                getColorClipView(i).setProgress(i == position ? 1 : 0);
            }
        }
    }

    public void tabScrolled(int position, float positionOffset) {

        if (positionOffset == 0.0F) {
            return;
        }
        ColorTrackView currentTrackView = getColorClipView(position);
        ColorTrackView nextTrackView = getColorClipView(position + 1);
        currentTrackView.setDirection(1);
        currentTrackView.setProgress(1.0F - positionOffset);
        nextTrackView.setDirection(0);
        nextTrackView.setProgress(positionOffset);
    }

    private ColorTrackView getColorClipView(int position) {
        return (ColorTrackView) getTabAt(position).getCustomView();
    }

    public static class ColorClipTabLayoutOnPageChangeListener extends TabLayoutOnPageChangeListener {

        private final WeakReference<ColorTrackTabLayout> mTabLayoutRef;
        private int mPreviousScrollState;
        private int mScrollState;

        public ColorClipTabLayoutOnPageChangeListener(TabLayout tabLayout) {
            super(tabLayout);
            mTabLayoutRef = new WeakReference<>((ColorTrackTabLayout) tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            mPreviousScrollState = mScrollState;
            mScrollState = state;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            ColorTrackTabLayout tabLayout = mTabLayoutRef.get();
            if (tabLayout == null) return;
            final boolean updateText = mScrollState != SCROLL_STATE_SETTLING ||
                    mPreviousScrollState == SCROLL_STATE_DRAGGING;
            if (updateText) {
                Log.e("tag1", "positionOffset" + positionOffset);
                tabLayout.tabScrolled(position, positionOffset);
            }
        }
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            ColorTrackTabLayout tabLayout = mTabLayoutRef.get();
            mPreviousScrollState = SCROLL_STATE_SETTLING;
            tabLayout.setSelectedView(position);
        }

        void reset() {
            mPreviousScrollState = mScrollState = SCROLL_STATE_IDLE;
        }

    }
    /**
     * 设置每个Tab的左内边距和右内边距
     *
     * @param left
     * @param right
     */
    public void setTabPaddingLeftAndRight(int left, int right) {
        try {
            Field mTabPaddingStartField = TabLayout.class.getDeclaredField("mTabPaddingStart");
            Field mTabPaddingEndField = TabLayout.class.getDeclaredField("mTabPaddingEnd");

            mTabPaddingStartField.setAccessible(true);
            mTabPaddingEndField.setAccessible(true);

            mTabPaddingStartField.set(this, left);
            mTabPaddingEndField.set(this, right);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

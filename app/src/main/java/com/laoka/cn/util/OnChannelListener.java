package com.laoka.cn.util;

/**
 * @author 张海洋
 * @Date on 2019/04/17.
 * @org 上海..科技有限公司
 * @describe
 */
public interface OnChannelListener {
    void onItemMove(int starPos, int endPos);
    void onMoveToMyChannel(int starPos, int endPos);
    void onMoveToOtherChannel(int starPos, int endPos);
}

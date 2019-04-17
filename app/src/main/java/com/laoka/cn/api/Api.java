package com.laoka.cn.api;

/**
 * @author 张海洋
 * @Date on 2019/03/25.
 * @org 上海..科技有限公司
 * @describe
 */
public interface Api {
    /**
     * 基础BaseURL
     */
    String APP_DEFAULT_DOMAIN = "http://energy.shanutec.com/api/";
    /**
     * Video
     * 用于切换BaseURL
     */
    String VIDEO_DOMAIN_NAME = "video";
    String APP_VIDEO_DOMAIN = "http://baobab.kaiyanapp.com/api/v4";

    String VIDEO_INFO_DOMAIN_NAME = "videoInfo";
    String APP_INFO_VIDEO_DOMAIN = "http://ib.365yg.com";

    //新闻
    String NEW_INFO_DOMAIN_NAME = "news";
    String APP_INFO_NEWS_DOMAIN = "http://is.snssdk.com";


}

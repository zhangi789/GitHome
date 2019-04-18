package com.laoka.cn.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.laoka.cn.bean.News;
import com.laoka.cn.util.BaseUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author 张海洋
 * @Date on 2019/04/18.
 * @org 上海..科技有限公司
 * @describe
 */
public class NewsRecordHelper {
    private static Gson mGson = new Gson();

    /**
     * 获取数据库保存的某个频道的最后一条记录
     *
     * @param channelCode 频道
     * @return
     */
    public static NewsRecord getLastNewsRecord(String channelCode) {
        return DataSupport.where("channelCode=?", channelCode).findLast(NewsRecord.class);
    }

    /**
     * 获取某个频道上一组新闻记录
     *
     * @param channelCode 频道
     * @param page        页码
     * @return
     */
    public static NewsRecord getPreNewsRecord(String channelCode, int page) {
        List<NewsRecord> newsRecords = selectNewsRecords(channelCode, page - 1);

        if (BaseUtil.isEmpty(newsRecords)) {
            return null;
        }
        return newsRecords.get(0);
    }


    /**
     * 保存新闻记录
     *
     * @param channelCode
     * @param json
     */
    public static void save(String channelCode, String json) {
        int page = 1;
        NewsRecord lastNewsRecord = getLastNewsRecord(channelCode);
        if (lastNewsRecord != null) {
            //如果有记录
            page = lastNewsRecord.getPage() + 1;//页码为最后一条的页码加1
        }
        //保存新的记录
        NewsRecord newsRecord = new NewsRecord(channelCode, page, json, System.currentTimeMillis());
        newsRecord.saveOrUpdate("channelCode = ? and page = ?", channelCode, String.valueOf(page));
    }


    /**
     * 根据频道码和页码查询新闻记录
     *
     * @param channelCode
     * @param page
     * @return
     */
    private static List<NewsRecord> selectNewsRecords(String channelCode, int page) {
        return DataSupport
                .where("channelCode = ? and page = ?", channelCode, String.valueOf(page))
                .find(NewsRecord.class);
    }

    /**
     * 将json转换成新闻集合
     *
     * @param json
     * @return
     */
    public static List<News> convertToNewsList(String json) {
        return mGson.fromJson(json, new TypeToken<List<News>>() {
        }.getType());
    }
}

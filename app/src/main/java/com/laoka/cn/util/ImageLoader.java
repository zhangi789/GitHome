package com.laoka.cn.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * @author 张海洋
 * @Date on 2019/04/17.
 * @org 上海..科技有限公司
 * @describe
 */
public class ImageLoader {




    private static final String TAG = ImageLoader.class.getSimpleName();
    private ImageLoader() {
    }

    public static void init(Context context) {
    }

    public static void displayImage(Context context, ImageView imageView, Uri uri) {
        displayImage(context,imageView,uri,0);
    }

    public static void displayImage(Context context,ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static void displayImage(Context context,ImageView imageView, Uri uri,int placeholder) {
        if(context == null || imageView == null){
            return;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)	//加载成功之前占位图
                .error(placeholder)	//加载错误之后的错误图
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop();

        Glide.with(context)
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    public static void displayImage(Context context,ImageView imageView, String url,int placeholder) {
        if(context == null || imageView == null){
            return;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)	//加载成功之前占位图
                .error(placeholder)	//加载错误之后的错误图
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop();

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }


    public static void displayImageCircleCrop(Context context,ImageView imageView, Uri uri,int placeholder) {
        if(context == null || imageView == null){
            return;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)	//加载成功之前占位图
                .error(placeholder)	//加载错误之后的错误图
                .circleCrop()
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop();

        Glide.with(context)
                .load(uri)
                .apply(options)
                .into(imageView);
    }


    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}

package com.fyj.easylinkingimageloader.core;

import android.content.Context;
import android.widget.ImageView;

import com.fyj.easylinkingimageloader.imageoption.ImageOptions;
import com.fyj.easylinkingimageloader.listener.ImageHelperListener;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/29<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public interface BaseImageLoader {

    void initApplication(Context context);

    void disableLog(boolean yes);

    void clearDiscCache();

    void clearMemoryCache();

    void resume();

    void stop();

    void destroy();

    void display(String uri, ImageView imageView, ImageOptions ops);

    void display(String uri, ImageView imageView, int type);

    void loadImage(String url, int w, int h, ImageOptions ops, ImageHelperListener listener);

    void loadImage(String url, int w, int h, int type, ImageHelperListener listener);

    void loadImage(String uri, int width, int height, ImageHelperListener listener);

    void loadImage(String url, int type, ImageHelperListener listener);

    void loadImage(String url, final ImageHelperListener listener);

}

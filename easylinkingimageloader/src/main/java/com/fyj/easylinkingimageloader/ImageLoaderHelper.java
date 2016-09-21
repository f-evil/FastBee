package com.fyj.easylinkingimageloader;

import android.content.Context;
import android.widget.ImageView;

import com.fyj.easylinkingimageloader.core.BaseImageLoader;
import com.fyj.easylinkingimageloader.core.DefaultImageLoader;
import com.fyj.easylinkingimageloader.imageoption.ImageOptions;
import com.fyj.easylinkingimageloader.listener.ImageHelperListener;

/**
 * 图片加载器总配置
 * Created by Fyj on 2016/6/18.
 */
public class ImageLoaderHelper {

    /**
     * SD卡
     */
    public static final int SDCARD_TYPE = 0;
    /**
     * 内容提供器
     */
    public static final int CONTENT_PROVIDER_TYPE = 1;
    /**
     * 资源文件
     */
    public static final int ASSETS_TYPE = 2;
    /**
     * drawable
     * 不推荐,请使用ImageView.setImageResource(int)
     */
    @Deprecated
    public static final int DRAWABLE_TYPE = 3;

    /*
    *
    * "http://site.com/image.png" // http
    * "file:///mnt/sdcard/image.png" // SD card
    * "file:///mnt/sdcard/video.mp4" // SD card (视频缩略图)
    * "content://media/external/images/media/13" // content provider
    * "content://media/external/video/media/13" // content provider (视频缩略图)
    * "assets://image.png" // assets
    * "drawable://" + R.drawable.img //drawables (不支持.9图)
    *
    * */

    private static ImageLoaderHelper INSTANCE;
    private static BaseImageLoader INSTANCE_LOADER;

    public static void initApplication(Context context, BaseImageLoader loader) {
        loader.initApplication(context);
        INSTANCE_LOADER = loader;

    }

    public static void initInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImageLoaderHelper();
        }
    }

    public static ImageLoaderHelper getInstance() {
        confirmHelperExist();
        return INSTANCE;
    }

    /**
     * 验证实例是否存在
     */
    private static void confirmHelperExist() {
        if (INSTANCE == null) {
            initInstance();
        }

        if (INSTANCE_LOADER == null) {
            INSTANCE_LOADER = new DefaultImageLoader();
        }
    }

    /**
     * 清除缓存
     */
    public static void cleanCacheLoader() {
        confirmHelperExist();
        INSTANCE_LOADER.clearDiscCache();
        INSTANCE_LOADER.clearMemoryCache();
    }

    /**
     * 停止加载
     */
    public static void stopLoader() {
        confirmHelperExist();
        INSTANCE_LOADER.stop();
    }

    /**
     * 销毁加载器
     */
    public static void destoryLoader() {
        confirmHelperExist();
        INSTANCE_LOADER.destroy();
    }

    /**
     * 加载手机中图像
     *
     * @param url  地址尾
     * @param iv   目标对象
     * @param type 类型
     */
    public static void display(String url, int urlType, ImageView iv, int type) {
        if (iv != null) {
            if (url == null || url.trim().isEmpty()) {
                INSTANCE_LOADER
                        .display("", iv, type);
            } else {
                INSTANCE_LOADER
                        .display(appendUrl(url, urlType), iv, type);
            }
        }
    }

    /**
     * 加载手机中图像
     *
     * @param url 地址尾
     * @param iv  目标对象
     * @param ops 加载配置
     */
    public static void display(String url, int urlType, ImageView iv, ImageOptions ops) {
        if (iv != null) {
            if (url == null || url.trim().isEmpty()) {
                INSTANCE_LOADER
                        .display("", iv, ops);
            } else {
                INSTANCE_LOADER
                        .display(appendUrl(url, urlType), iv, ops);
            }
        }
    }

    /**
     * 根据类型拼接地址
     *
     * @param url     url
     * @param urlType 类型
     * @return 完整链接
     */
    private static String appendUrl(String url, int urlType) {

        String tempUrl = "";

        switch (urlType) {
            case SDCARD_TYPE:
                tempUrl = "file://" + url;
                break;

            case CONTENT_PROVIDER_TYPE:
                tempUrl = "content://" + url;
                break;

            case ASSETS_TYPE:
                tempUrl = "assets://" + url;
                break;

            case DRAWABLE_TYPE:
                tempUrl = "drawable://" + url;
                break;

            default:
                tempUrl = "";
                break;
        }

        return tempUrl;
    }

    /**
     * 显示图像
     *
     * @param url  地址
     * @param iv   目标对象
     * @param type 类型
     */
    public static void display(String url, ImageView iv, int type) {
        if (iv != null) {
            if (url == null || url.trim().isEmpty()) {
                INSTANCE_LOADER.display("", iv, type);
            } else {
                INSTANCE_LOADER.display(url, iv, type);
            }
        }
    }

    /**
     * 显示图像
     *
     * @param headurl 地址头
     * @param url     地址尾
     * @param iv      目标对象
     * @param type    类型
     */
    public static void display(String headurl, String url, ImageView iv, int type) {
        if (iv != null) {
            if (url == null || url.trim().isEmpty()) {
                INSTANCE_LOADER.display("", iv, type);
            } else {
                INSTANCE_LOADER.display(headurl + url, iv, type);
            }
        }
    }

    /**
     * 显示图像
     *
     * @param headurl 地址头
     * @param url     地址尾
     * @param iv      目标对象
     * @param ops     加载配置
     */
    public static void display(String headurl, String url, ImageView iv, ImageOptions ops) {
        if (iv != null) {
            if (url == null || url.trim().isEmpty()) {
                INSTANCE_LOADER.display("", iv, ops);
            } else {
                INSTANCE_LOADER.display(headurl + url, iv, ops);
            }
        }
    }


    /**
     * 加载头像
     *
     * @param url 头像地址
     * @param iv  目标对象
     */
    public static void displayHeadImage(String url, ImageView iv) {

        confirmHelperExist();

        display("", url, iv, ImageOptions.HEAD_TYPE);

    }

    /**
     * 加载头像
     *
     * @param url 头像地址
     * @param iv  目标对象
     */
    public static void displayDeamndHeadImage(String url, ImageView iv) {

        confirmHelperExist();

        display("", url, iv, ImageOptions.DEMAND_HEAD_TYPE);

    }

    /**
     * 加载头部广告栏
     *
     * @param url 广告地址
     * @param iv  目标对象
     */
    public static void displayBannerImage(String url, ImageView iv) {

        confirmHelperExist();

        display("", url, iv, ImageOptions.BANNER_TYPE);

    }

    /**
     * 需求应答页
     *
     * @param url 广告地址
     * @param iv  目标对象
     */
    public static void displayDemandADImage(String url, ImageView iv) {

        confirmHelperExist();

        display("", url, iv, ImageOptions.BANNER_TYPE);

    }

    /**
     * 加载感兴趣横向图片广告栏
     *
     * @param url 图片地址
     * @param iv  目标对象
     */
    public static void displayInterestHorImage(String url, ImageView iv) {

        confirmHelperExist();

        display("", url, iv, ImageOptions.INTEREST_HOR_TYPE);

    }

    /**
     * 加载感兴趣纵向图片广告栏
     *
     * @param url 图片地址
     * @param iv  目标对象
     */
    public static void displayInterestVerImage(String url, ImageView iv) {

        confirmHelperExist();

        display("", url, iv, ImageOptions.INTEREST_VER_TYPE);

    }

    /**
     * 加载圆形背景
     *
     * @param url 图片地址
     * @param iv  目标对象
     */
    public static void displayCircleBgImage(String url, ImageView iv) {

        confirmHelperExist();

        display("", url, iv, ImageOptions.CIRCLR_TYPE);

    }

    /**
     * 加载约企海报与九宫图
     *
     * @param url 图片地址
     * @param iv  目标对象
     */
    public static void displayYueNineAndPostImage(String url, ImageView iv) {

        confirmHelperExist();

        display("", url, iv, ImageOptions.YUE_TYPE);

    }

    /**
     * 加载消息图片
     *
     * @param url 图片地址
     * @param iv  目标对象
     */
    public static void displayMsgImage(String url, ImageView iv) {

        confirmHelperExist();

        display("", url, iv, ImageOptions.YUE_TYPE);

    }

    /**
     * 加载图片到内存
     *
     * @param url      url
     * @param width    宽
     * @param height   高
     * @param listener 监听器
     */
    public static void loadImage(String url, int width, int height, final ImageHelperListener listener) {
        confirmHelperExist();
        INSTANCE_LOADER.loadImage(url, width, height, listener);
    }

    /**
     * 加载图片到内存
     *
     * @param url      url
     * @param listener 监听器
     */
    public static void loadImage(String url, int type, final ImageHelperListener listener) {
        confirmHelperExist();
        INSTANCE_LOADER.loadImage(url, type, listener);
    }

    /**
     * 加载图片到内存
     *
     * @param url      url
     * @param listener 监听器
     */
    public static void loadImage(String url, final ImageHelperListener listener) {
        confirmHelperExist();
        INSTANCE_LOADER.loadImage(url, listener);
    }

    /**
     * 加载手机中图像
     *
     * @param url 地址尾
     * @param ops 加载配置
     */
    public static void loadImage(String url, int urlType, int w, int h, ImageOptions ops, final ImageHelperListener listener) {
        confirmHelperExist();
        INSTANCE_LOADER.loadImage(appendUrl(url, urlType), w, h, ops, listener);
    }

    /**
     * 加载手机中图像
     *
     * @param url 地址尾
     */
    public static void loadImage(String url, int urlType, int w, int h, int type, final ImageHelperListener listener) {
        confirmHelperExist();
        INSTANCE_LOADER.loadImage(appendUrl(url, urlType), w, h, type, listener);
    }
}

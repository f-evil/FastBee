package com.fyj.fastbee.imageloaderutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.fyj.easylinkingimageloader.core.BaseImageLoader;
import com.fyj.easylinkingimageloader.imageoption.ImageOptions;
import com.fyj.easylinkingimageloader.listener.ImageHelperListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/29<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class UILImageLoader implements BaseImageLoader {

    @Override
    public void initApplication(Context context) {
        int NORM_PRIORITY = Thread.NORM_PRIORITY;
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(context)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(4)
                .threadPriority(NORM_PRIORITY - 1 == 0 ? NORM_PRIORITY : NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(80 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
                .build();

        ImageLoader.getInstance().init(configuration);
        com.nostra13.universalimageloader.utils.L.disableLogging();


    }


    @Override
    public void disableLog(boolean yes) {
        if (yes) {
            com.nostra13.universalimageloader.utils.L.disableLogging();
        } else {
            com.nostra13.universalimageloader.utils.L.enableLogging();
        }
    }

    @Override
    public void clearDiscCache() {
        ImageLoader.getInstance().clearDiscCache();
    }

    @Override
    public void clearMemoryCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    @Override
    public void resume() {
        ImageLoader.getInstance().resume();
    }

    @Override
    public void stop() {
        ImageLoader.getInstance().stop();
    }

    @Override
    public void destroy() {
        ImageLoader.getInstance().stop();
    }

    @Override
    public void display(String url, ImageView imageView, ImageOptions ops) {
        ImageLoader.getInstance().displayImage(url, imageView, ops.build());
    }

    @Override
    public void display(String url, ImageView imageView, int type) {
        ImageLoader.getInstance().displayImage(url, imageView, new ImageOptions().build(type));
    }

    @Override
    public void loadImage(String url, int w, int h, ImageOptions ops, final ImageHelperListener listener) {
        ImageLoader
                .getInstance()
                .loadImage(url,
                        new ImageSize(w, h),
                        ops.build(),
                        new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String s, View view) {
                                listener.onLoadingStarted(s, view);
                            }

                            @Override
                            public void onLoadingFailed(String s, View view, FailReason failReason) {
                                listener.onLoadingFailed(s, view, failReason.getCause().getLocalizedMessage());
                            }

                            @Override
                            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                listener.onLoadingComplete(s, view, bitmap);
                            }

                            @Override
                            public void onLoadingCancelled(String s, View view) {
                                listener.onLoadingCancelled(s, view);
                            }
                        });
    }

    @Override
    public void loadImage(String url, int w, int h, int type, final ImageHelperListener listener) {
        ImageLoader
                .getInstance()
                .loadImage(url,
                        new ImageSize(w, h),
                        new ImageOptions().build(type),
                        new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String s, View view) {
                                listener.onLoadingStarted(s, view);
                            }

                            @Override
                            public void onLoadingFailed(String s, View view, FailReason failReason) {
                                listener.onLoadingFailed(s, view, failReason.getCause().getLocalizedMessage());
                            }

                            @Override
                            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                listener.onLoadingComplete(s, view, bitmap);
                            }

                            @Override
                            public void onLoadingCancelled(String s, View view) {
                                listener.onLoadingCancelled(s, view);
                            }
                        });
    }

    @Override
    public void loadImage(String uri, int width, int height, final ImageHelperListener listener) {
        ImageLoader
                .getInstance()
                .loadImage(uri,
                        new ImageSize(width, height),
                        new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String s, View view) {
                                listener.onLoadingStarted(s, view);
                            }

                            @Override
                            public void onLoadingFailed(String s, View view, FailReason failReason) {
                                listener.onLoadingFailed(s, view, failReason.getCause().getLocalizedMessage());
                            }

                            @Override
                            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                listener.onLoadingComplete(s, view, bitmap);
                            }

                            @Override
                            public void onLoadingCancelled(String s, View view) {
                                listener.onLoadingCancelled(s, view);
                            }
                        });
    }

    @Override
    public void loadImage(String url, int type, final ImageHelperListener listener) {
        ImageLoader
                .getInstance()
                .loadImage(url,
                        new ImageOptions().build(type),
                        new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String s, View view) {
                                listener.onLoadingStarted(s, view);
                            }

                            @Override
                            public void onLoadingFailed(String s, View view, FailReason failReason) {
                                listener.onLoadingFailed(s, view, failReason.getCause().getLocalizedMessage());
                            }

                            @Override
                            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                listener.onLoadingComplete(s, view, bitmap);
                            }

                            @Override
                            public void onLoadingCancelled(String s, View view) {
                                listener.onLoadingCancelled(s, view);
                            }
                        });
    }

    @Override
    public void loadImage(String url, final ImageHelperListener listener) {
        ImageLoader
                .getInstance()
                .loadImage(url,
                        new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String s, View view) {
                                listener.onLoadingStarted(s, view);
                            }

                            @Override
                            public void onLoadingFailed(String s, View view, FailReason failReason) {
                                listener.onLoadingFailed(s, view, failReason.getCause().getLocalizedMessage());
                            }

                            @Override
                            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                listener.onLoadingComplete(s, view, bitmap);
                            }

                            @Override
                            public void onLoadingCancelled(String s, View view) {
                                listener.onLoadingCancelled(s, view);
                            }
                        });
    }
}

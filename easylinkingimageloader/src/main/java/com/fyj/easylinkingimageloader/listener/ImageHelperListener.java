package com.fyj.easylinkingimageloader.listener;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Fyj on 2016/6/18.
 */
public interface ImageHelperListener {
    void onLoadingStarted(String imageUri, View view);

    void onLoadingFailed(String imageUri, View view, String failReason);

    void onLoadingComplete(String imageUri, View view, Bitmap bitmap);

    void onLoadingCancelled(String imageUri, View view);
}

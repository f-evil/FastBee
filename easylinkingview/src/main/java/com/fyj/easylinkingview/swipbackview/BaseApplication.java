package com.fyj.easylinkingview.swipbackview;

import android.app.Activity;
import android.app.Application;
import android.view.View;

import com.fyj.easylinkingview.swipbackview.ActivityLifecycleHelper;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/21<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class BaseApplication extends Application {

    private ActivityLifecycleHelper mActivityLifecycleHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(mActivityLifecycleHelper = new ActivityLifecycleHelper());

    }

    public ActivityLifecycleHelper getActivityLifecycleHelper() {
        return mActivityLifecycleHelper;
    }

    public void onSlideBack(boolean isReset, float distance) {
        if(mActivityLifecycleHelper != null) {
            Activity lastActivity = mActivityLifecycleHelper.getPreActivity();
            if(lastActivity != null) {
                View contentView = lastActivity.findViewById(android.R.id.content);
                if(isReset) {
                    contentView.setX(contentView.getLeft());
                } else {
                    final int width = getResources().getDisplayMetrics().widthPixels;
                    contentView.setX(-width / 3 + distance / 3);
                }
            }
        }
    }
}

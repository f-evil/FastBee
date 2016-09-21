package com.fyj.easylinkingutils.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Toast显示工具类
 * 防止Toast多次显示的问题
 * <p/>
 * Created by fyj on 2015/9/19.
 */
public class ToastUtil {

    private static Context context;

    private static Toast mToast = null;

    private ToastUtil() {
    }

    public static void init(Context mContext) {
        context = mContext;
    }

    public static void makeText(CharSequence msg) {

        if (null == msg) {
            return;
        }

        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }

        mToast.show();
    }

    public static void makeText(final Activity activity, final String message) {
        if (activity == null) {
            return;
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(message);
                    if (message.length() < 20)
                        mToast.setDuration(Toast.LENGTH_SHORT);
                    else
                        mToast.setDuration(Toast.LENGTH_LONG);
                }
                mToast.show();
            }
        });
    }

    /**
     * 在关闭页面后调用
     * ondestory();
     */
    public static void destory() {
        if (mToast != null) {
            mToast = null;
        }
    }

}

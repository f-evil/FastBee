package com.fyj.easylinkingutils.utils;

import android.view.View;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/20<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class ViewCheckUtils {

    public static boolean isViewNull(View view) {
        return view == null;
    }

    public static boolean isViewVisiable(View view) {
        return !isViewNull(view) && view.getVisibility() == View.VISIBLE;
    }
}

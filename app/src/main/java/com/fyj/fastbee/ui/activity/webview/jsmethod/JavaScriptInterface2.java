package com.fyj.fastbee.ui.activity.webview.jsmethod;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.fyj.easylinkingutils.utils.ToastUtil;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/8<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class JavaScriptInterface2 extends JavaScriptInterface {

    public JavaScriptInterface2(Context context) {
        super(context);
    }

    //分享
    @JavascriptInterface
    public void promotePage(final String encodeP, final String ptype, final String title, final String content, final String logo, final String url) {
        ToastUtil.makeText(ptype);
    }
}

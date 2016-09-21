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
public class JavaScriptInterface {

    private Context context;

    public JavaScriptInterface(Context context) {
        this.context = context;
    }

    //打开公司图片
    private static String COMPANY_IMAGE = "companyImage";
    //打开公司动态
    private static String COMPANYACT = "companyAct";
    //打开比别人的地图
    private static String ADDRESS = "address";
    //打开我的地图
    private static String MYADDRESS = "myAddress";
    //打开个人动态
    private static String ACT = "act";
    //打开我的需求
    private static String DEMAND = "demand";
    // 打开二维码
    private static String QRCOED = "QRcode";
    //发消息
    private static String MSG = "msg";
    //交换好友
    private static String FRIEND = "friend";
    //更多页面
    private static String MORE = "more";
    //更多需求推送设置
    private static String CHANNEL = "channel";
    //更多职位申请
    private static String ALIAS = "alias";
    //更多名片
    private static String CARD = "card";
    //修改头像
    private static String HEAD = "head";
    //更多系统消息
    private static String SERVICE_MSG = "service_msg";
    //微信好友
    private static String WX = "WX";
    //朋友圈
    private static String PYQ = "PYQ";
    //QQ
    private static String QQ = "QQ";
    //QQ空间
    private static String KJ = "KJ";
    //微博
    private static String WB = "WB";
    //共同好友
    private static String COMMONFRIEND = "commonfriend";

    @JavascriptInterface
    public void openWindow(String type, String id, String name) {
        openWin(type, id, name);
    }

    private void openWin(String type, String id, String name) {

        ToastUtil.makeText(type);
//        Intent i = new Intent();
        if (type.equals(COMPANY_IMAGE)) {

        } else if (type.equals(COMPANYACT)) {

        } else if (type.equals(ADDRESS)) {

        } else if (type.equals(MYADDRESS)) {

        } else if (type.equals(ACT)) {

        } else if (type.equals(DEMAND)) {

        } else if (type.equals(QRCOED)) {

        } else if (type.equals(MSG)) {

        } else if (type.equals(FRIEND)) {

        } else if (type.equals(CHANNEL)) {

        } else if (type.equals(ALIAS)) {

        } else if (type.equals(CARD)) {

        } else if (type.equals(MORE)) {

        } else if (type.equals(SERVICE_MSG)) {

        } else if (type.equals(HEAD)) {

        } else if (type.equals(COMMONFRIEND)) {

        }
    }
}

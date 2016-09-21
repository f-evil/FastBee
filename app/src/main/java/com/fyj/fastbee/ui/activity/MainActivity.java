package com.fyj.fastbee.ui.activity;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.ui.activity.blur.BlurActivity;
import com.fyj.fastbee.ui.activity.cache.CacheActivity;
import com.fyj.fastbee.ui.activity.contract.ContractActivity;
import com.fyj.fastbee.ui.activity.contract.ContractRecycleViewActivity;
import com.fyj.fastbee.ui.activity.dayornight.DayOrNightActivity;
import com.fyj.fastbee.ui.activity.friendcircle.FriendCircleActivity;
import com.fyj.fastbee.ui.activity.login.LoginActivity;
import com.fyj.fastbee.ui.activity.msg.MsgActivity;
import com.fyj.fastbee.ui.activity.other.V4V7DesignActivity;
import com.fyj.fastbee.ui.activity.permissionmodel.PermissionModelActivitye;
import com.fyj.fastbee.ui.activity.qr.QrMangerActivity;
import com.fyj.fastbee.ui.activity.retrofit.RetrofitActivit;
import com.fyj.fastbee.ui.activity.tab.TabActivity;
import com.fyj.fastbee.ui.activity.viewpager.ViewpagerShowActivity;
import com.fyj.fastbee.ui.activity.webview.WebviewActivity;
import com.fyj.fastbee.ui.activity.welcome.WelcomeActivitye;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: <br>
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数:
 */
public class MainActivity extends BaseAppCompatActivity {

    @BindView(R.id.bt_welcome)
    Button bt_welcome;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.bt_vp)
    Button bt_vp;
    @BindView(R.id.bt_tab)
    Button bt_tab;
    @BindView(R.id.bt_qr)
    Button bt_qr;
    @BindView(R.id.bt_msg)
    Button bt_msg;
    @BindView(R.id.bt_circle)
    Button bt_circle;
    @BindView(R.id.bt_contract)
    Button bt_contract;
    @BindView(R.id.bt_cache)
    Button bt_cache;
    @BindView(R.id.bt_dayornight)
    Button bt_dayornight;
    @BindView(R.id.bt_webview)
    Button bt_webview;
    @BindView(R.id.bt_other)
    Button bt_other;
    @BindView(R.id.bt_permission_model)
    Button bt_permission_model;
    @BindView(R.id.bt_blur)
    Button bt_blur;
    @BindView(R.id.bt_retrofit)
    Button bt_retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getDate() {

    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {

    }

    @OnClick({R.id.bt_retrofit,R.id.bt_blur,R.id.bt_welcome, R.id.bt_login, R.id.bt_vp, R.id.bt_qr, R.id.bt_msg, R.id.bt_circle, R.id.bt_contract, R.id.bt_cache, R.id.bt_dayornight, R.id.bt_webview, R.id.bt_other, R.id.bt_permission_model, R.id.bt_tab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_welcome:
                ToastUtil.makeText(bt_welcome.getText().toString());
                WelcomeActivitye.goTo(this);
                break;
            case R.id.bt_login:
                ToastUtil.makeText(bt_login.getText().toString());
                LoginActivity.goTo(this);
                break;
            case R.id.bt_vp:
                ToastUtil.makeText(bt_vp.getText().toString());
                ViewpagerShowActivity.goTo(this);
                break;
            case R.id.bt_tab:
                ToastUtil.makeText(bt_tab.getText().toString());
                TabActivity.goTo(this);
                break;
            case R.id.bt_permission_model:
                ToastUtil.makeText(bt_permission_model.getText().toString());
                PermissionModelActivitye.goTo(this);
                break;
            case R.id.bt_qr:
                ToastUtil.makeText(bt_qr.getText().toString());
                QrMangerActivity.goTo(this);
                break;
            case R.id.bt_msg:
                ToastUtil.makeText(bt_msg.getText().toString());
                MsgActivity.goTo(this);
                break;
            case R.id.bt_circle:
                ToastUtil.makeText(bt_circle.getText().toString());
                FriendCircleActivity.goTo(this);
                break;
            case R.id.bt_contract:
                ToastUtil.makeText(bt_contract.getText().toString());
//                ContractRecycleViewActivity.goTo(this);
                ContractActivity.goTo(this);
                break;
            case R.id.bt_cache:
                ToastUtil.makeText(bt_cache.getText().toString());
                CacheActivity.goTo(this);
                break;
            case R.id.bt_dayornight:
                ToastUtil.makeText(bt_dayornight.getText().toString());
                DayOrNightActivity.goTo(this);
                break;
            case R.id.bt_webview:
                ToastUtil.makeText(bt_webview.getText().toString());
                WebviewActivity.goTo(this);
                break;
            case R.id.bt_blur:
                ToastUtil.makeText(bt_blur.getText().toString());
                BlurActivity.goTo(this);
                break;
            case R.id.bt_retrofit:
                ToastUtil.makeText(bt_retrofit.getText().toString());
                RetrofitActivit.goTo(this);
                break;
            case R.id.bt_other:
                ToastUtil.makeText(bt_other.getText().toString());
                V4V7DesignActivity.goTo(this);
                break;
            default:
                ToastUtil.makeText("Nothing at all.");
                break;
        }
    }

}

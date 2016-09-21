package com.fyj.fastbee.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fyj.easylinkingutils.listener.OnSubMultipleClickListener;
import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;

import butterknife.BindView;

/**
 * 描述: <br>
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数:
 */
public class LoginActivity extends BaseAppCompatActivity implements LoginContract.View{

    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;

    private LoginPreImpl loginPreImpl;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {
        loginPreImpl =new LoginPreImpl(this);
        loginPreImpl.setVM(this,new LoginModel(this));
    }

    @Override
    protected void getDate() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {
        btn_login.setOnClickListener(new OnSubMultipleClickListener() {
            @Override
            public void onSubClick(View v) {
                super.onSubClick(v);
                loginPreImpl.setAccountAndPassword(et_account.getText().toString(),et_password.getText().toString());
                loginPreImpl.onStart();
            }
        });
    }

    @Override
    public void onLoginStrat() {
        btn_login.setEnabled(false);
        et_account.setEnabled(false);
        et_password.setEnabled(false);
    }

    @Override
    public void onLoginSuccesed() {
        ToastUtil.makeText("Link start!");
        finish();
    }

    @Override
    public void onLoginFailed(String msg) {
        btn_login.setEnabled(true);
        et_account.setEnabled(true);
        et_password.setEnabled(true);
        ToastUtil.makeText(msg);
    }
}

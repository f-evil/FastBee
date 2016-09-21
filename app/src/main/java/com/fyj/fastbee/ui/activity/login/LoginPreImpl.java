package com.fyj.fastbee.ui.activity.login;

import android.content.Context;

/**
 *
 * Created by Fyj on 2016/8/23.
 */
public class LoginPreImpl extends LoginContract.Presenter {

    private Context context;

    private String account;
    private String password;

    public LoginPreImpl(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        mView.onLoginStrat();
        mModel.Login(account,password);
    }

    @Override
    public void setAccountAndPassword(String account, String password) {
        this.account=account;
        this.password=password;
    }
}

package com.fyj.fastbee.ui.activity.login;

import com.fyj.fastbee.base.BaseModel;
import com.fyj.fastbee.base.BasePresenter;
import com.fyj.fastbee.base.BaseView;

/**
 * 描述: <br>
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数:
 */
public interface LoginContract {

    interface View extends BaseView {
        void onLoginStrat();

        void onLoginSuccesed();

        void onLoginFailed(String msg);
    }

    interface Model extends BaseModel {
        void Login(String account, String password);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        abstract void setAccountAndPassword(String account, String password);
    }
}

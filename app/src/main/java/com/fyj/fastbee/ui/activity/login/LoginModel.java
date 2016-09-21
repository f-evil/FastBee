package com.fyj.fastbee.ui.activity.login;

import com.fyj.easylinkingnet.core.OkHttpUtils;
import com.fyj.easylinkingnet.core.callback.Callback;
import com.fyj.fastbee.bean.LoginBean;
import com.fyj.fastbee.bean.base.BaseObjectBean;
import com.fyj.fastbee.db.UserAndSettingsDB;
import com.fyj.fastbee.globel.HttpInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述: <br>
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数:
 */
public class LoginModel implements LoginContract.Model {

    LoginContract.View view;

    public LoginModel(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void Login(String account, String password) {
        OkHttpUtils
                .get()
                .url(HttpInterface.Login.LOGIN)
                .addParams("regMobile", account)
                .addParams("passWord", password)
                .build()
                .execute(new Callback<BaseObjectBean<LoginBean>>() {
                    @Override
                    public BaseObjectBean<LoginBean> parseNetworkResponse(Response response, int id) throws Exception {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseObjectBean<LoginBean>>() {
                        }.getType();
                        return gson.fromJson(response.body().string(), type);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (view != null) {
                            view.onLoginFailed("网络错误");
                        }
                    }

                    @Override
                    public void onResponse(final BaseObjectBean<LoginBean> bean, int id) {
                        if (bean == null) {
                            if (view != null) {
                                view.onLoginFailed("网络错误");
                            }
                        } else {
                            if (bean.getStatus() == 10001) {
                                if (view != null) {
                                    view.onLoginSuccesed();
                                }
                                new UserAndSettingsDB().insertLoginSettings(bean.getData());
                            } else {
                                view.onLoginFailed(bean.getMsg());
                            }
                        }
                    }
                });
    }
}

package com.fyj.fastbee.ui.activity.friendcircle;

import com.fyj.easylinkingnet.core.OkHttpUtils;
import com.fyj.easylinkingnet.core.callback.Callback;
import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.fastbee.bean.YueProjectBean;
import com.fyj.fastbee.bean.base.BaseObjectBean;
import com.fyj.fastbee.globel.HttpInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/5<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class FriendCircleDataModel implements FriendCircleContract.DataModel {

    private FriendCircleContract.DataPresenter impl;

    public FriendCircleDataModel(FriendCircleContract.DataPresenter impl) {
        this.impl = impl;
    }

    @Override
    public void getCircleData(String updateTime) {
        OkHttpUtils
                .get()
                .url(HttpInterface.YueService.GET_ALL_FRIEND_CRICLE_LIST)
                .addParams("updatedAt", updateTime)
                .tag("circle")
                .build()
                .execute(new Callback<BaseObjectBean<YueProjectBean>>() {
                    @Override
                    public BaseObjectBean<YueProjectBean> parseNetworkResponse(Response response, int id) throws Exception {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseObjectBean<YueProjectBean>>() {
                        }.getType();
                        return gson.fromJson(response.body().string(), type);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        impl.getDateError("数据获取出错");
                        XLog.e(e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(BaseObjectBean<YueProjectBean> response, int id) {
                        int status = response.getStatus();
                        String msg = response.getMsg();

                        if (10001==status){
                            YueProjectBean data = response.getData();
                            impl.getDateSuccesed(data.getActivitys());
                        }else {
                            impl.getDateError(msg);
                        }
                    }
                });
    }
}

package com.fyj.fastbee.ui.activity.contract;

import com.fyj.easylinkingnet.core.OkHttpUtils;
import com.fyj.easylinkingnet.core.callback.Callback;
import com.fyj.fastbee.bean.PersonInfoBean;
import com.fyj.fastbee.bean.base.BaseArrayBean;
import com.fyj.fastbee.db.FriendshipDB;
import com.fyj.fastbee.globel.HttpInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/26<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class RelationModel implements RelationContract.Model {

    private RelationContract.Presenter impl;

    public RelationModel(RelationContract.Presenter impl) {
        this.impl = impl;
    }

    @Override
    public void getContractList() {

        OkHttpUtils
                .get()
                .url(HttpInterface.Friendship.FRIEND)
                .build()
                .execute(new Callback<BaseArrayBean<List<PersonInfoBean>>>() {
                    @Override
                    public BaseArrayBean<List<PersonInfoBean>> parseNetworkResponse(Response response, int id) throws Exception {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseArrayBean<List<PersonInfoBean>>>() {
                        }.getType();
                        return gson.fromJson(response.body().string(), type);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (impl != null) {
                            impl.onLoadError("获取数据失败");
                        }
                    }

                    @Override
                    public void onResponse(BaseArrayBean<List<PersonInfoBean>> bean, int id) {
                        if (bean == null) {
                            if (impl != null) {
                                impl.onLoadError("网络错误");
                            }
                        } else {
                            if (bean.getStatus() == 10001) {

                                List<PersonInfoBean> data = bean.getData();

                                new FriendshipDB().insertFriends(data);

                                if (impl == null) {
                                    return;
                                }

                                if (data != null && data.size() > 0) {
                                    impl.onLoadSuccess();
                                } else {
                                    impl.onLoadEmpty("暂无更多数据");
                                }

                            } else {
                                if (impl != null) {
                                    impl.onLoadError(bean.getMsg());
                                }
                            }
                        }
                    }
                });
    }
}

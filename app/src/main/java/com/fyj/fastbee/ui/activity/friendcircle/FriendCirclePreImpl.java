package com.fyj.fastbee.ui.activity.friendcircle;

import android.content.Context;

import com.fyj.fastbee.bean.ActivitysBean;

import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/5<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class FriendCirclePreImpl extends FriendCircleContract.DataPresenter {

    private Context context;

    public FriendCirclePreImpl(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {

    }

    @Override
    void getCircleDate(String updateTime) {
        mModel.getCircleData(updateTime);
    }

    @Override
    void getDateError(String msg) {
        mView.onetCircleDataFailed(msg);
    }

    @Override
    void getDateSuccesed(List<ActivitysBean> activitys) {
        mView.onGetCircleDataSuccesed(activitys);
    }
}

package com.fyj.fastbee.ui.activity.contract;

import com.fyj.fastbee.bean.PersonInfoBean;
import com.fyj.fastbee.db.FriendshipDB;
import com.fyj.fastbee.utils.FriendShipComparator;

import java.util.Collections;
import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/26<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class RelationPreImpl extends RelationContract.Presenter {

    @Override
    public void onStart() {

        FriendshipDB db = new FriendshipDB();
        List<PersonInfoBean> allFriendList = db.getAllFriendList();
        if (allFriendList != null && allFriendList.size() > 0) {
            Collections.sort(allFriendList, new FriendShipComparator());
            mView.updateView(allFriendList);
        } else {
            mModel.getContractList();
        }

    }

    @Override
    void onLoadError(String msg) {
        mView.getDateError(msg);
    }

    @Override
    void onLoadSuccess() {
        FriendshipDB db = new FriendshipDB();
        List<PersonInfoBean> allFriendList = db.getAllFriendList();
        Collections.sort(allFriendList, new FriendShipComparator());
        mView.updateView(allFriendList);
    }

    @Override
    void onLoadEmpty(String msg) {
        mView.getDateError(msg);
    }
}

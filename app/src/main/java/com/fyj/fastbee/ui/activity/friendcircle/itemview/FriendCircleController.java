package com.fyj.fastbee.ui.activity.friendcircle.itemview;

import com.fyj.easylinkingadapter.MViewController;
import com.fyj.easylinkingadapter.MViewItem;
import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.fastbee.bean.ActivitysBean;
import com.fyj.fastbee.ui.activity.friendcircle.FriendCircleCommonPreImpl;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/6<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class FriendCircleController implements MViewController<ActivitysBean> {

    private static final int TYPE_POST = 0;
    private static final int TYPE_FREE = 1;
    private static final int TYPE_FROM_PROJECT = 2;

    private FriendCircleCommonPreImpl friendCircleCommonPre;

    public FriendCircleController(FriendCircleCommonPreImpl friendCircleCommonPre) {
        this.friendCircleCommonPre=friendCircleCommonPre;
    }

    @Override
    public int getItemViewType(ActivitysBean data, int position) {
        String type = data.getType();
        if (type.toLowerCase().contains("post")) {
            return TYPE_POST;
        } else if (type.toLowerCase().contains("free")) {
            return TYPE_FREE;
        } else {
            return TYPE_FROM_PROJECT;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public MViewItem<ActivitysBean> newViewItem(int type) {

        MViewItem<ActivitysBean> item;

        if (type == TYPE_POST) {
            FriendCirclePost temp = new FriendCirclePost();
            temp.setPreImpl(friendCircleCommonPre);
            item = temp;
        } else if (type == TYPE_FREE) {
            FriendCircleFree temp = new FriendCircleFree();
            temp.setPreImpl(friendCircleCommonPre);
            item = temp;
        } else {
            FriendCircleProject temp = new FriendCircleProject();
            temp.setPreImpl(friendCircleCommonPre);
            item = temp;
        }

        return item;
    }
}

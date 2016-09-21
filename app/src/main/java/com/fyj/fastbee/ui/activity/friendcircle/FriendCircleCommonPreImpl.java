package com.fyj.fastbee.ui.activity.friendcircle;

import android.view.View;

import com.fyj.easylinkingview.commentview.CommentWidget;
import com.fyj.easylinkingview.commentview.bean.DateCommentList;
import com.fyj.fastbee.bean.ActivitysBean;
import com.fyj.fastbee.bean.OwnerBean;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/6<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class FriendCircleCommonPreImpl extends FriendCircleContract.CommentPresenter {

    @Override
    public void onNameOrHeadClick(OwnerBean owner) {
        mView.onNameOrHeadClick(owner);
    }

    @Override
    public void onCommentClick(CommentWidget commentWidget, DateCommentList dateCommentList, int pos, int poss) {
        mView.onCommentClick(commentWidget,dateCommentList,pos,poss);
    }


    @Override
    public void onImageClick() {

    }

    @Override
    public void onControllerClick(View v, int pos, ActivitysBean data) {
        mView.onControllerClick(v,pos,data);
    }

    @Override
    public void onStart() {

    }
}

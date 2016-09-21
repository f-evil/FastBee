package com.fyj.fastbee.ui.activity.friendcircle;

import com.fyj.easylinkingview.commentview.CommentWidget;
import com.fyj.easylinkingview.commentview.bean.DateCommentList;
import com.fyj.fastbee.base.BaseModel;
import com.fyj.fastbee.base.BasePresenter;
import com.fyj.fastbee.base.BaseView;
import com.fyj.fastbee.bean.ActivitysBean;
import com.fyj.fastbee.bean.OwnerBean;

import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/5<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public interface FriendCircleContract {

    interface View extends BaseView {

        void onGetCircleDataSuccesed(List<ActivitysBean> activitys);

        void onetCircleDataFailed(String msg);
    }

    interface CommentView extends BaseView {

        void onNameOrHeadClick(OwnerBean owner);

        void onCommentClick(CommentWidget commentWidget, DateCommentList dateCommentList, int pos, int poss);

        void onImageClick();

        void onControllerClick(android.view.View v, int pos, ActivitysBean data);
    }

    interface DataModel extends BaseModel {
        void getCircleData(String updateTime);
    }

    abstract class DataPresenter extends BasePresenter<View, DataModel> {

        abstract void getCircleDate(String updateTime);

        abstract void getDateError(String msg);

        abstract void getDateSuccesed(List<ActivitysBean> activitys);
    }

    abstract class CommentPresenter extends BasePresenter<CommentView, DataModel> {

        public abstract void onNameOrHeadClick(OwnerBean owner);

        public abstract void onCommentClick(CommentWidget commentWidget, DateCommentList dateCommentList, int pos, int poss);

        public abstract void onImageClick();

        public abstract void onControllerClick(android.view.View v, int pos, ActivitysBean data);


    }
}

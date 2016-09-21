package com.fyj.fastbee.ui.activity.contract;

import com.fyj.fastbee.base.BaseModel;
import com.fyj.fastbee.base.BasePresenter;
import com.fyj.fastbee.base.BaseView;
import com.fyj.fastbee.bean.PersonInfoBean;

import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/26<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public interface RelationContract {

    interface View extends BaseView {
        void updateView(List<PersonInfoBean> data);

        void getDateError(String msg);
    }

    interface Model extends BaseModel {
        void getContractList();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        abstract void onLoadError(String msg);

        abstract void onLoadSuccess();

        abstract void onLoadEmpty(String msg);
    }
}

package com.fyj.fastbee.ui.activity.welcome;

import android.content.Context;
import android.view.animation.Animation;

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
public interface WelContract {

    interface View extends BaseView{
        void startBgAnimate(Animation animation);
        void toNextPage();
    }

    interface Model extends BaseModel{
        Animation getAnimation(Context context);
    }

    abstract class Presenter extends BasePresenter<View, Model>{

    }

}

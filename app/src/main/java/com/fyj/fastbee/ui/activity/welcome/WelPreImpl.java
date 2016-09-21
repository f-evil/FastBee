package com.fyj.fastbee.ui.activity.welcome;

import android.content.Context;
import android.view.animation.Animation;

/**
 * 描述: <br>
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数:
 */
public class WelPreImpl extends WelContract.Presenter {

    private Context mContext;

    public WelPreImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onStart() {
        Animation animation = mModel.getAnimation(mContext);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mView.toNextPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation.setFillAfter(true);

        mView.startBgAnimate(animation);
    }
}

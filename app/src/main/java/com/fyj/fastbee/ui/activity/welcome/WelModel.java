package com.fyj.fastbee.ui.activity.welcome;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * 描述: <br>
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数:
 */
public class WelModel implements WelContract.Model {

    @Override
    public Animation getAnimation(Context context) {

        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);

        return animation;
    }
}

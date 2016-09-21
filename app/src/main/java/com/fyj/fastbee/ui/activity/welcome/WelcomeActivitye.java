package com.fyj.fastbee.ui.activity.welcome;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;

import butterknife.BindView;

/**
 * 描述: <br>
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数:
 */
public class WelcomeActivitye extends BaseAppCompatActivity implements WelContract.View {

    @BindView(R.id.iv_wel)
    ImageView iv_wel;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, WelcomeActivitye.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {
        WelPreImpl impl = new WelPreImpl(this);
        impl.setVM(this, new WelModel());
        impl.onStart();
    }

    @Override
    protected void getDate() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void startBgAnimate(Animation animation) {
        iv_wel.startAnimation(animation);
    }

    @Override
    public void toNextPage() {
        finish();
    }

}

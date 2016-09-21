package com.fyj.fastbee.ui.activity.dayornight;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Button;

import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DayOrNightActivity extends BaseAppCompatActivity {

    @BindView(R.id.button)
    Button button;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, DayOrNightActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_day_or_night;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {
        XLog.e("oncreate");
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

    @OnClick(R.id.button)
    public void onClick() {
        if (((int) (Math.random() * 2 + 1)) % 2 == 0) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        recreate();
    }

}

package com.fyj.fastbee.ui.activity.viewpager;

import android.os.Bundle;

import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;

public class DemoFragmentActivity extends BaseAppCompatActivity {

    @Override
    protected int setLayout() {
        return R.layout.activity_demo_fragment;
    }

    @Override
    protected boolean supportSlideBack() {
        return !super.supportSlideBack();
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {
        DemoFragmente fragment = new DemoFragmente();
        Bundle args = new Bundle();
        args.putString(DemoFragmente.ARG_PARAM1, "DemoFragmentActivity");
        args.putString(DemoFragmente.ARG_PARAM2, "DemoFragmentActivity");
        fragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_demo, fragment)
                .commit();
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

}

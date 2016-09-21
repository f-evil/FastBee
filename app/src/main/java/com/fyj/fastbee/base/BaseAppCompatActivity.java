package com.fyj.fastbee.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.easylinkingview.swipbackview.SwipeWindowHelper;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subscriptions.CompositeSubscription;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public abstract class BaseAppCompatActivity extends RxAppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private Unbinder unBinder;

    private SwipeWindowHelper mSwipeWindowHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(setLayout());
        unBinder = ButterKnife.bind(this);
        initDate();
        getDate();
        initView();
        initCustomFunction();
        bindEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        XLog.eLine();
        XLog.e("Showing Activity Name:", TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryPre();
        unBinder.unbind();
        ToastUtil.destory();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!supportSlideBack()) {
            return super.dispatchTouchEvent(ev);
        }

        if(mSwipeWindowHelper == null) {
            mSwipeWindowHelper = new SwipeWindowHelper(getWindow());
        }
        return mSwipeWindowHelper.processTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    protected boolean supportSlideBack() {
        return false;
    }

    protected abstract int setLayout();

    protected abstract void destoryPre();

    protected abstract void initDate();

    protected abstract void getDate();

    protected abstract void initView();

    protected abstract void initCustomFunction();

    protected abstract void bindEvent();
}

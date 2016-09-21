package com.fyj.fastbee.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.fastbee.ui.application.FastBebApplication;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public abstract class BaseLazyAppFragment extends RxFragment {

    private String TAG = this.getClass().getSimpleName();

    private FragmentActivity activity;

    private View mCurrentView;
    private Unbinder unbinder;

    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;

    //标志位 fragment是否可见
    protected boolean isVisible;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mCurrentView = inflater.inflate(setLayout(), container, false);
        unbinder = ButterKnife.bind(this, mCurrentView);
        return mCurrentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destoryPre();
        unbinder.unbind();
        ToastUtil.destory();

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

    /**
     * Fragment数据的懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            XLog.e("Showing Fragment Name:", TAG);
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        initDate();
        getDate();
        initView();
        initBroadCast();
        bindEvent();
    }

    protected void onInvisible() {

    }

    protected abstract int setLayout();

    protected abstract void destoryPre();

    protected abstract void initDate();

    protected abstract void getDate();

    protected abstract void initView();

    protected abstract void initBroadCast();

    protected abstract void bindEvent();

    private View getCurrentView() {
        return mCurrentView;
    }

    public FragmentActivity getSupportActivity() {

        return super.getActivity();
    }

    public Context getApplicationContext() {

        return FastBebApplication.getAppContext().get();
    }
}

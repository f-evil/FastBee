package com.fyj.fastbee.ui.activity.other.custombehavior.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.fyj.fastbee.ui.activity.other.custombehavior.AnimatorUtil;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/18<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class ScaleDownBehavior extends FloatingActionButton.Behavior {

    /**
     * 退出动画是否正在执行。
     */
    private boolean isAnimatingOut = false;

    private OnStateChangedListener mOnStateChangedListener;

    public ScaleDownBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !isAnimatingOut && child.getVisibility() == View.VISIBLE) {//往下滑
            AnimatorUtil.scaleHide(child, viewPropertyAnimatorListener);
            if (mOnStateChangedListener != null) {
                mOnStateChangedListener.onChanged(false);
            }
        } else if ((dyConsumed < 0 || dyUnconsumed < 0) && child.getVisibility() != View.VISIBLE) {
            AnimatorUtil.scaleShow(child, null);
            if (mOnStateChangedListener != null) {
                mOnStateChangedListener.onChanged(true);
            }
        }
    }

    public void setOnStateChangedListener(OnStateChangedListener mOnStateChangedListener) {
        this.mOnStateChangedListener = mOnStateChangedListener;
    }

    // 外部监听显示和隐藏。
    public interface OnStateChangedListener {
        void onChanged(boolean isShow);
    }

    public static <V extends View> ScaleDownBehavior from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (!(behavior instanceof ScaleDownBehavior)) {
            throw new IllegalArgumentException("The view is not associated with ScaleDownShowBehavior");
        }
        return (ScaleDownBehavior) behavior;
    }

    private ViewPropertyAnimatorListener viewPropertyAnimatorListener = new ViewPropertyAnimatorListener() {

        @Override
        public void onAnimationStart(View view) {
            isAnimatingOut = true;
        }

        @Override
        public void onAnimationEnd(View view) {
            isAnimatingOut = false;
            view.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationCancel(View arg0) {
            isAnimatingOut = false;
        }
    };
}

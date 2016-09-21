package com.fyj.easylinkingview.refreshandload;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fyj.easylinkingview.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by 大灯泡 on 2016/2/9.
 * 下拉刷新的头头
 * pengyouq
 */
public class RefreshMorePtrHeader extends RelativeLayout {
    private static final String TAG = "FriendCirclePtrHeader";

    private ImageView mRotateIcon;
    private View rootView;
    private boolean isAutoRefresh;
    private boolean isFirstRefresh = false;
    private RotateAnimation rotateAnimation;
    private SmoothChangeThread mSmoothChangeThread;
    private ValueAnimator mValueAnimator;

    //当前状态
    private PullState mPullState;
    //当前模式，加载更多的时候刷新的icon无需执行动画
    private PullMode mPullMode;

    public RefreshMorePtrHeader(Context context) {
        this(context, null);
    }

    public RefreshMorePtrHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshMorePtrHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RefreshMorePtrHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.widget_ptr_header, this, false);
        addView(rootView);

        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateAnimation.setDuration(600);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException("不可以添加多个view噢");
        }
    }

    //=============================================================ptr:
    private PtrUIHandler mPtrUIHandler = new PtrUIHandler() {
        /**回到初始位置*/
        @Override
        public void onUIReset(PtrFrameLayout frame) {
            mPullState = PullState.NORMAL;
            if (null != mRotateIcon && mRotateIcon.getAnimation() != null) {
                mRotateIcon.clearAnimation();
            }
        }

        /**离开初始位置*/
        @Override
        public void onUIRefreshPrepare(PtrFrameLayout frame) {

        }

        /**开始刷新动画*/
        @Override
        public void onUIRefreshBegin(PtrFrameLayout frame) {
            mPullState = PullState.REFRESHING;
            if (null != mRotateIcon) {
                if (isAutoRefresh && isFirstRefresh) {
                    ValueAnimator autoRefreshAnimator = ValueAnimator.ofInt(0, frame.getOffsetToRefresh());
                    autoRefreshAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int result = (int) animation.getAnimatedValue();
                            updateRotateAnima(result);
                            mRotateIcon.setRotation(-(result << 1));
                        }
                    });
                    autoRefreshAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isFirstRefresh = false;

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            isFirstRefresh = false;

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    autoRefreshAnimator.setDuration(300);
                    autoRefreshAnimator.start();
                }

                if (mRotateIcon.getAnimation() != null) {
                    mRotateIcon.clearAnimation();
                }
                mRotateIcon.startAnimation(rotateAnimation);
            }
        }

        /**刷新完成*/
        @Override
        public void onUIRefreshComplete(PtrFrameLayout frame) {
            mPullState = PullState.NORMAL;
            if (mRotateIcon == null) return;
            /**采取通用插值器线程实现*/
           /* if (mSmoothChangeThread == null) {
                mSmoothChangeThread = SmoothChangeThread.CreateLinearInterpolator(mRotateIcon,
                        frame.getOffsetToRefresh(), 0, 300, 75);
                mSmoothChangeThread.setOnSmoothResultChangeListener(
                        new SmoothChangeThread.OnSmoothResultChangeListener() {
                            @Override
                            public void onSmoothResultChange(int result) {
                                updateRotateAnima(result);
                                mRotateIcon.setRotation(-(result << 1));
                            }
                        });
            }
            else {
                mSmoothChangeThread.stop();
            }
            mRotateIcon.post(mSmoothChangeThread);*/

            if (mPullMode == PullMode.FROM_START) {
                /**采取valueAnimator*/
                if (mValueAnimator == null) {
                    mValueAnimator = ValueAnimator.ofInt(frame.getOffsetToRefresh(), 0);
                    mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int result = (int) animation.getAnimatedValue();
                            updateRotateAnima(result);
                            mRotateIcon.setRotation(-(result << 1));
                        }
                    });
                    mValueAnimator.setDuration(300);
                }
                mValueAnimator.start();
            }
        }

        /**位移更新重载*/
        @Override
        public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator
                ptrIndicator) {
            if (mRotateIcon == null) return;
            final int mOffsetToRefresh = frame.getOffsetToRefresh();
            final int currentPos = ptrIndicator.getCurrentPosY();
            final int lastPos = ptrIndicator.getLastPosY();

            if (currentPos < mOffsetToRefresh) {
                //未到达刷新线
                if (status == PtrFrameLayout.PTR_STATUS_PREPARE && mRotateIcon != null) {
                    updateRotateAnima(currentPos);
                    mRotateIcon.setRotation(-(currentPos << 1));
                }
            } else if (currentPos > mOffsetToRefresh) {
                //到达或超过刷新线
                if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE && mRotateIcon != null) {
                    updateRotateAnima(mOffsetToRefresh);
                    mRotateIcon.setRotation(-(currentPos << 1));
                }
            }
        }
    };

    private void  updateRotateAnima(int marginTop) {
//        Log.d(TAG, "curMargin=========" + marginTop);
        if (mRotateIcon == null) return;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mRotateIcon.getLayoutParams();
        params.topMargin = marginTop;
        mRotateIcon.setLayoutParams(params);
    }

    //=============================================================Getter/Setter

    public PtrUIHandler getPtrUIHandler() {
        return mPtrUIHandler;
    }

    public void setPtrUIHandler(PtrUIHandler ptrUIHandler) {
        mPtrUIHandler = ptrUIHandler;
    }

    public boolean isAutoRefresh() {
        return isAutoRefresh;
    }

    public void setAutoRefresh(boolean autoRefresh) {
        isAutoRefresh = autoRefresh;
        isFirstRefresh = autoRefresh;
    }

    public ImageView getRotateIcon() {
        return mRotateIcon;
    }

    public void setRotateIcon(ImageView rotateIcon) {
        mRotateIcon = rotateIcon;
    }

    public PullState getPullState() {
        return mPullState;
    }

    public PullMode getPullMode() {
        return mPullMode;
    }

    public void setPullMode(PullMode pullMode) {
        mPullMode = pullMode;
    }
}

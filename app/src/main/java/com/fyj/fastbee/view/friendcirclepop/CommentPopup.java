package com.fyj.fastbee.view.friendcirclepop;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fyj.easylinkingutils.utils.SizeUtils;
import com.fyj.easylinkingview.basepopup.BasePopupWindow;
import com.fyj.fastbee.R;
import com.fyj.fastbee.bean.ActivitysBean;

import java.lang.ref.WeakReference;

/**
 * Created by 大灯泡 on 2016/3/6.
 * 朋友圈点赞
 */
public class CommentPopup extends BasePopupWindow implements View.OnClickListener {
    private static final String TAG = "CommentPopup";

    private ImageView mLikeView;
    private TextView mLikeText;

    private RelativeLayout mLikeClikcLayout;
    private RelativeLayout mCommentClickLayout;

    private int[] viewLocation;

    private WeakHandler handler;
    private ScaleAnimation mScaleAnimation;

    private OnCommentPopupClickListener mOnCommentPopupClickListener;
    private ActivitysBean info;

    public CommentPopup(Activity context) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        viewLocation = new int[2];
        handler = new WeakHandler(context);

        mLikeView = (ImageView) mPopupView.findViewById(R.id.iv_like);
        mLikeText = (TextView) mPopupView.findViewById(R.id.tv_like);

        mLikeClikcLayout = (RelativeLayout) mPopupView.findViewById(R.id.item_like);
        mCommentClickLayout = (RelativeLayout) mPopupView.findViewById(R.id.item_comment);

        mLikeClikcLayout.setOnClickListener(this);
        mCommentClickLayout.setOnClickListener(this);

        buildAnima();
    }

    private void buildAnima() {
        mScaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleAnimation.setDuration(300);
        mScaleAnimation.setInterpolator(new SpringInterPolator());
        mScaleAnimation.setFillAfter(false);

        mScaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 150);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected Animation getShowAnimation() {
        return getScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
    }

    @Override
    public Animation getExitAnimation() {
        return getScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
    }

    @Override
    protected View getClickToDismissView() {
        return null;
    }

    @Override
    public View getPopupView() {
        return getPopupViewById(R.layout.popup_comment);
    }

    @Override
    public View getAnimaView() {
        return mPopupView.findViewById(R.id.comment_popup_contianer);
    }

    @Override
    public void showPopupWindow(View v) {
        try {
            //得到v的位置
            v.getLocationOnScreen(viewLocation);
            //展示位置：
            //参照点为view的右上角，偏移值为：x方向距离参照view的一定倍数距离
            //垂直方向自身减去popup自身高度的一半（确保在中间）
            mPopupWindow.showAtLocation(v, Gravity.RIGHT | Gravity.TOP, (int) (v.getWidth() * 1.8),
                    viewLocation[1] - SizeUtils.dp2px(mContext, 10));

            if (getShowAnimation() != null && getAnimaView() != null) {
                getAnimaView().startAnimation(getShowAnimation());
            }
        } catch (Exception e) {
            Log.w("error", "error");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_like:
                if (mOnCommentPopupClickListener != null) {
                    mOnCommentPopupClickListener.onLikeClick(v, info);
                    mLikeView.clearAnimation();
                    mLikeView.startAnimation(mScaleAnimation);
                }
                break;
            case R.id.item_comment:
                if (mOnCommentPopupClickListener != null) {
                    mOnCommentPopupClickListener.onCommentClick(v, info);
                    mPopupWindow.dismiss();
                }
                break;
        }
    }
    //=============================================================Getter/Setter

    public OnCommentPopupClickListener getOnCommentPopupClickListener() {
        return mOnCommentPopupClickListener;
    }

    public void setOnCommentPopupClickListener(OnCommentPopupClickListener onCommentPopupClickListener) {
        mOnCommentPopupClickListener = onCommentPopupClickListener;
    }

    public void setDynamicInfo(ActivitysBean bean) {
        if (bean == null) return;
        info=bean;
        if (bean.getIsLike().equals("1")) {
            mLikeText.setText("取消赞");
        } else {
            mLikeText.setText("赞  ");
        }
    }

    //=============================================================InterFace
    public interface OnCommentPopupClickListener {
        void onLikeClick(View v, ActivitysBean bean);

        void onCommentClick(View v, ActivitysBean bean);
    }

    static class WeakHandler extends Handler {
        private final WeakReference<Context> contenxt;

        public WeakHandler(Context contenxt) {
            this.contenxt = new WeakReference<Context>(contenxt);
        }
    }

    static class SpringInterPolator extends LinearInterpolator {

        public SpringInterPolator() {
        }


        @Override
        public float getInterpolation(float input) {
            return (float) Math.sin(input* Math.PI);
        }
    }
}

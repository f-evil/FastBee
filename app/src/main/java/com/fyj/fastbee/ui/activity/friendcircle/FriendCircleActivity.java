package com.fyj.fastbee.ui.activity.friendcircle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fyj.easylinkingadapter.MAdapter;
import com.fyj.easylinkingkeyboard.fragment.EmotionMainFragment;
import com.fyj.easylinkingkeyboard.model.KeyboardFunctionBean;
import com.fyj.easylinkingutils.listener.OnSoftKeyboardChangeListener;
import com.fyj.easylinkingutils.utils.KeyboardUtils;
import com.fyj.easylinkingutils.utils.SPUtils;
import com.fyj.easylinkingutils.utils.ScreenUtils;
import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.easylinkingview.commentview.CommentWidget;
import com.fyj.easylinkingview.commentview.bean.DateCommentList;
import com.fyj.easylinkingview.refreshandload.OnLoadMoreRefreshListener;
import com.fyj.easylinkingview.refreshandload.OnPullDownRefreshListener;
import com.fyj.easylinkingview.refreshandload.PullMode;
import com.fyj.easylinkingview.refreshandload.CircleRefreshAndLoadMoreLayout;
import com.fyj.fastbee.R;
import com.fyj.fastbee.adapter.FriendListAdapter;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.bean.ActivitysBean;
import com.fyj.fastbee.bean.OwnerBean;
import com.fyj.fastbee.ui.activity.friendcircle.itemview.FriendCircleController;
import com.fyj.fastbee.utils.KeyboardFunctionUtils;
import com.fyj.fastbee.view.friendcirclepop.CommentJoinPopup;
import com.fyj.fastbee.view.friendcirclepop.CommentPopup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class FriendCircleActivity extends BaseAppCompatActivity implements FriendCircleContract.View,
        FriendCircleContract.CommentView, OnSoftKeyboardChangeListener, ViewTreeObserver.OnGlobalLayoutListener {

    public static final String EMOTION_KEYBOARD = "EmotionKeyboard";
    public static final String SOFT_INPUT_HEIGHT = "soft_input_height";
    @BindView(R.id.rotate_icon)
    ImageView rotateIcon;
    @BindView(R.id.rll_lv_content)
    CircleRefreshAndLoadMoreLayout rllLvContent;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.fl_keyboard)
    FrameLayout flKeyboard;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    private EmotionMainFragment mEmotionMainFragment;

    private FriendCirclePreImpl mFriendCirclePreImpl;
    private FriendCircleCommonPreImpl friendCircleCommonPre;

    private List<ActivitysBean> mActivityDatas;
    private FriendListAdapter mAdapter;
    private MAdapter<ActivitysBean> mAdapter2;

    private int currentDynamicPos;
    private CommentWidget mCommentWidget;

    private CommentPopup mCommentPopup;
    private CommentJoinPopup mCommentJoinPopup;


    public static void goTo(Context context) {
        context.startActivity(new Intent(context, FriendCircleActivity.class));
    }

    @Override
    protected void onDestroy() {
        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        super.onDestroy();

    }

    @Override
    protected int setLayout() {
        return R.layout.activity_friend_circle;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {
        mFriendCirclePreImpl = new FriendCirclePreImpl(this);
        mActivityDatas = new ArrayList<>();

        friendCircleCommonPre = new FriendCircleCommonPreImpl();
        friendCircleCommonPre.setVM(this, null);

    }

    @Override
    protected void getDate() {

    }

    @Override
    protected void initView() {

        initEmotionView();
        initList();

        KeyboardUtils.observeSoftKeyboard(this, this);

        mCommentPopup = new CommentPopup(this);
        mCommentJoinPopup = new CommentJoinPopup(this);

        mFriendCirclePreImpl.setVM(this, new FriendCircleDataModel(mFriendCirclePreImpl));
        mFriendCirclePreImpl.getCircleDate("0");
    }

    private void initList() {
        rllLvContent.setRotateIcon(rotateIcon);

        View friendCircleHeader = LayoutInflater.from(this).inflate(R.layout.item_header, null, false);

        rllLvContent.addHeaderView(friendCircleHeader);

        mAdapter = new FriendListAdapter(this, mActivityDatas);

        mAdapter2 = new MAdapter<>(this, mActivityDatas, new FriendCircleController(friendCircleCommonPre));

        rllLvContent.setAdapter(mAdapter2);
    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {
        rllLvContent.setOnPullDownRefreshListener(new OnPullDownRefreshListener() {
            @Override
            public void onRefreshing(PtrFrameLayout frame) {
                mActivityDatas.clear();
                mFriendCirclePreImpl.getCircleDate("0");
            }
        });

        rllLvContent.setOnLoadMoreRefreshListener(new OnLoadMoreRefreshListener() {
            @Override
            public void onRefreshing(PtrFrameLayout frame) {
                ActivitysBean item = mAdapter.getItem(mAdapter.getCount() - 1);
                mFriendCirclePreImpl.getCircleDate(item.getUpdatedAt() + "");
            }
        });

        rllLvContent.setOnDispatchTouchEventListener(new CircleRefreshAndLoadMoreLayout.OnDispatchTouchEventListener() {
            @Override
            public boolean OnDispatchTouchEvent(MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                    View v = getCurrentFocus();
                    if (isShouldHideKeyboard(v, ev)) {
                        if (mEmotionMainFragment != null) {
                            mEmotionMainFragment.isInterceptBackPress();
                        }
                        KeyboardUtils.hideSoftInput(FriendCircleActivity.this);
                        flKeyboard.setVisibility(View.GONE);
                        mCommentWidget = null;
                        currentDynamicPos = -1;
                    }
                }

                return false;
            }
        });

        mEmotionMainFragment.setOnEmotionKeyboardEventsListener(new EmotionMainFragment.OnEmotionKeyboardEventsListener() {
            @Override
            public void onEmotionSendEventKey(String msg) {

            }

            @Override
            public void onFunctionEventKey(KeyboardFunctionBean bean) {

            }

            @Override
            public void onSoundEventKey(MotionEvent event) {

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mEmotionMainFragment != null) {
                boolean interceptBackPress = mEmotionMainFragment.isInterceptBackPress();
                if (!interceptBackPress) {
                    return super.onKeyDown(keyCode, event);
                } else {
                    return true;
                }
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onGetCircleDataSuccesed(List<ActivitysBean> activityDatas) {
        mActivityDatas.addAll(activityDatas);
        mAdapter2.notifyDataSetChanged();
        if (rllLvContent != null) {
            rllLvContent.setHasMore(true);
            if (rllLvContent.getCurMode() == PullMode.FROM_START) {
                rllLvContent.refreshComplete();
            } else {
                rllLvContent.loadmoreCompelete();
            }
        }

    }

    @Override
    public void onetCircleDataFailed(String msg) {
        ToastUtil.makeText(msg);
        if (rllLvContent != null) {
            if (rllLvContent.getCurMode() == PullMode.FROM_START) {
                rllLvContent.refreshComplete();
            } else {
                rllLvContent.loadmoreCompelete();
            }
        }
    }

    private void initEmotionView() {
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true);
        //隐藏控件
        bundle.putInt(EmotionMainFragment.VIEW_SHOW_TYPE_KEY, EmotionMainFragment.VIEW_SHOW_TYPE_PARAM_FRIEND_CIRCLE);
        //替换fragment
        //创建修改实例
        mEmotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class, bundle);
        mEmotionMainFragment.bindToContentView(flContent);
        mEmotionMainFragment.bindToFunctionView(KeyboardFunctionUtils.getFunctionList(KeyboardFunctionUtils.MAP_TYEP_MSG));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_keyboard, mEmotionMainFragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onNameOrHeadClick(OwnerBean owner) {
        ToastUtil.makeText(owner.getRegName());
    }

    @Override
    public void onCommentClick(CommentWidget commentWidget, DateCommentList dateCommentList, int pos, int poss) {
        currentDynamicPos = pos;
        mCommentWidget = commentWidget;

        if (flKeyboard.getVisibility() == View.GONE) {
            flKeyboard.setVisibility(View.VISIBLE);
            if (mEmotionMainFragment != null) {
                mEmotionMainFragment.showKeyBorad();
            }
        }

    }

    @Override
    public void onImageClick() {

    }

    @Override
    public void onControllerClick(View v, int pos, ActivitysBean bean) {
        String type = bean.getType();
        if (type.toLowerCase().equals("post")) {
            mCommentPopup.setDynamicInfo(bean);
            mCommentPopup.showPopupWindow(v);
            mCommentPopup.setOnCommentPopupClickListener(new CommentPopup.OnCommentPopupClickListener() {
                @Override
                public void onLikeClick(View v, ActivitysBean bean) {
                    ToastUtil.makeText("onLikeClick");
                }

                @Override
                public void onCommentClick(View v, ActivitysBean bean) {
                    ToastUtil.makeText("onCommentClick");
                }
            });
        } else {
            mCommentJoinPopup.setDynamicInfo(bean);
            mCommentJoinPopup.showPopupWindow(v);
            mCommentJoinPopup.setOnCommentPopupClickListener(new CommentJoinPopup.OnCommentPopupClickListener() {
                @Override
                public void onJoinClick(View v, final ActivitysBean bean) {
                    ToastUtil.makeText("onJoinClick");
                }

                @Override
                public void onLikeClick(View v, ActivitysBean bean) {
                    ToastUtil.makeText("onLikeClick");
                }

                @Override
                public void onCommentClick(View v, ActivitysBean bean) {
                    ToastUtil.makeText("onCommentClick");
                }
            });
        }

    }

    @Override
    public void onSoftKeyBoardChange(int softKeybardHeight, boolean visible) {

        SPUtils.putInt(this, EMOTION_KEYBOARD, SOFT_INPUT_HEIGHT, softKeybardHeight);

        if (visible) {
            final int offset = calculateListViewOffset(currentDynamicPos, mCommentWidget, softKeybardHeight);
            final int pos = currentDynamicPos + rllLvContent.getHeaderViewsCount();
            rllLvContent.smoothScrollToPositionFromTop(pos, offset);
        }
    }

    private int screenHeight = 0;
    private int statusBarHeight = 0;

    private int calculateListViewOffset(int currentDynamicPos, CommentWidget commentWidget, int keyBoardHeight) {
        int result;
        if (screenHeight == 0) screenHeight = ScreenUtils.getScreenHeight(this);
        if (statusBarHeight == 0) statusBarHeight = ScreenUtils.getStatusBarHeight(this);

        if (commentWidget == null) {
            // 评论控件为空，证明回复的是整个动态
            result = getOffsetOfDynamic(currentDynamicPos, keyBoardHeight);
        } else {
            // 评论控件不空，证明回复的是评论
            result = getOffsetOfComment(commentWidget, keyBoardHeight);
        }
        return result;
    }

    // 得到动态偏移量
    private int getOffsetOfDynamic(int currentDynamicPos, int keyBoardHeight) {
        int result = 0;
        ListView contentListView = null;
        if (rllLvContent.getContentView() instanceof ListView) {
            contentListView = (ListView) rllLvContent.getContentView();
        }

        if (contentListView == null) return 0;

        int firstItemPos = contentListView.getFirstVisiblePosition();
        int dynamicItemHeight = 0;
        View currentDynamicItem = contentListView.getChildAt(
                currentDynamicPos - firstItemPos + contentListView.getHeaderViewsCount());
        if (currentDynamicItem != null) {
            dynamicItemHeight = currentDynamicItem.getHeight();
            Log.d("dynamicItemHeight", "dynamicItemHeight=========    " + dynamicItemHeight);
        }
        int contentHeight = 0;
        contentHeight = screenHeight - keyBoardHeight - flKeyboard.getHeight();
        result = dynamicItemHeight - contentHeight;
        return -result;
    }

    // 得到评论的偏移量
    private int getOffsetOfComment(CommentWidget commentWidget, int keyBoardHeight) {
        int result = 0;
        int contentHeight;
        contentHeight = screenHeight - keyBoardHeight - flKeyboard.getHeight();
        try {

            final LinearLayout parent = (LinearLayout) commentWidget.getParent();

            final RelativeLayout commentParent = (RelativeLayout) parent.getParent();

            final int praiseAndCommentParentTop = commentParent.getTop();

            final int parentTop = parent.getTop();

            final int currentCommentBottom = commentWidget.getBottom();

            final int currentCommentOffset = praiseAndCommentParentTop + parentTop +
                    currentCommentBottom;

            result = currentCommentOffset - contentHeight;

        } catch (Exception e) {
            XLog.e(e.getLocalizedMessage());
        }
        return -result;
    }

    int previousKeyboardHeight = -1;
    Rect rect = new Rect();
    boolean lastVisibleState = false;

    @Override
    public void onGlobalLayout() {
        final View decorView = getWindow().getDecorView();
        rect.setEmpty();
        decorView.getWindowVisibleDisplayFrame(rect);
        int displayHeight = rect.bottom - rect.top;
        int height = decorView.getHeight();
        int keyboardHeight = height - displayHeight;
        if (previousKeyboardHeight != keyboardHeight) {
            boolean hide = (double) displayHeight / height > 0.8;
            if (hide != lastVisibleState) {
                this.onSoftKeyBoardChange(keyboardHeight, !hide);
                lastVisibleState = hide;
            }
        }
        previousKeyboardHeight = height;

        XLog.e("onGlobalLayout");

    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof AppCompatEditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

}

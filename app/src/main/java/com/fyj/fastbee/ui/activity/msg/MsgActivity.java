package com.fyj.fastbee.ui.activity.msg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.fyj.easylinkingkeyboard.fragment.EmotionMainFragment;
import com.fyj.easylinkingkeyboard.model.KeyboardFunctionBean;
import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.utils.KeyboardFunctionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MsgActivity extends BaseAppCompatActivity {

    @BindView(R.id.lv_msg)
    ListView lvMsg;
    @BindView(R.id.fl_keyboard)
    FrameLayout flKeyboard;

    private EmotionMainFragment mEmotionMainFragment;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, MsgActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_msg;
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

    }

    @Override
    protected void getDate() {

    }

    @Override
    protected void initView() {
        initEmotionView();

        lvMsg.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, getListData()));

        lvMsg.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    if (mEmotionMainFragment!=null){
                        mEmotionMainFragment.isInterceptBackPress();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    public List<String> getListData() {
        List<String> list = new ArrayList<>();
        list.add("Item 1");
        list.add("Item 2");
        list.add("Item 3");
        list.add("Item 4");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        list.add("Item 5");
        return list;
    }

    private void initEmotionView() {
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true);
        //隐藏控件
        bundle.putInt(EmotionMainFragment.VIEW_SHOW_TYPE_KEY, EmotionMainFragment.VIEW_SHOW_TYPE_PARAM_MSG);
        //替换fragment
        //创建修改实例
        mEmotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class, bundle);
        mEmotionMainFragment.bindToContentView(lvMsg);
        mEmotionMainFragment.bindToFunctionView(KeyboardFunctionUtils.getFunctionList(KeyboardFunctionUtils.MAP_TYEP_MSG));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_keyboard, mEmotionMainFragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {
        mEmotionMainFragment.setOnEmotionKeyboardEventsListener(new EmotionMainFragment.OnEmotionKeyboardEventsListener() {
            @Override
            public void onEmotionSendEventKey(String msg) {
                ToastUtil.makeText(msg);
            }

            @Override
            public void onFunctionEventKey(KeyboardFunctionBean bean) {
                ToastUtil.makeText(bean.getName());
            }

            @Override
            public void onSoundEventKey(MotionEvent event) {
                ToastUtil.makeText("onSoundEventKey");
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {

        if (v == null || !(v instanceof EditText)) {
            return false;
        }

        int[] l = {0, 0};
        v.getLocationInWindow(l);
        int left = l[0];
        int top = l[1];
        int bottom = top + v.getHeight();
        int right = left + v.getWidth();
        return !(event.getX() > left && event.getX() < right
                && event.getY() > top && event.getY() < bottom);
    }

    // 获取InputMethodManager，隐藏软键盘
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}

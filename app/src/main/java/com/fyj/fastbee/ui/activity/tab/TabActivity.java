package com.fyj.fastbee.ui.activity.tab;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fyj.easylinkingview.unreadmsgview.MsgView;
import com.fyj.easylinkingview.unreadmsgview.UnreadMsgUtils;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.ui.activity.viewpager.DemoFragmente;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: <br>
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数:
 */
public class TabActivity extends BaseAppCompatActivity {


    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.tv_line)
    TextView tvLine;
    @BindView(R.id.iv_item1)
    ImageView ivItem1;
    @BindView(R.id.tv_item1)
    TextView tvItem1;
    @BindView(R.id.mv_notif1)
    MsgView mvNotif1;
    @BindView(R.id.rl_item1)
    RelativeLayout rlItem1;
    @BindView(R.id.iv_item2)
    ImageView ivItem2;
    @BindView(R.id.tv_item2)
    TextView tvItem2;
    @BindView(R.id.mv_notif2)
    MsgView mvNotif2;
    @BindView(R.id.rl_item2)
    RelativeLayout rlItem2;
    @BindView(R.id.iv_item3)
    ImageView ivItem3;
    @BindView(R.id.tv_item3)
    TextView tvItem3;
    @BindView(R.id.mv_notif3)
    MsgView mvNotif3;
    @BindView(R.id.rl_item3)
    RelativeLayout rlItem3;
    @BindView(R.id.iv_item4)
    ImageView ivItem4;
    @BindView(R.id.tv_item4)
    TextView tvItem4;
    @BindView(R.id.mv_notif4)
    MsgView mvNotif4;
    @BindView(R.id.rl_item4)
    RelativeLayout rlItem4;

    private DemoFragmente mDemandFragment1;
    private DemoFragmente mDemandFragment2;
    private DemoFragmente mDemandFragment3;
    private DemoFragmente mDemandFragment4;

    private FragmentManager fragmentManager;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, TabActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_tab;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void getDate() {

    }

    @Override
    protected void initView() {
        setTabSelection(0);

        UnreadMsgUtils.show(mvNotif1, 0);
        UnreadMsgUtils.show(mvNotif2, 5);
        UnreadMsgUtils.show(mvNotif3, 50);
        UnreadMsgUtils.show(mvNotif4, 150);
    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {
    }

    private void setTabSelection(int index) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (mDemandFragment1 == null) {
                    mDemandFragment1 = DemoFragmente.newInstance("1", "1");
                    transaction.add(R.id.content, mDemandFragment1);
                } else {
                    transaction.show(mDemandFragment1);
                }
                break;
            case 1:
                if (mDemandFragment2 == null) {
                    mDemandFragment2 = DemoFragmente.newInstance("2", "2");
                    transaction.add(R.id.content, mDemandFragment2);
                } else {
                    transaction.show(mDemandFragment2);
                }
                break;
            case 2:
                if (mDemandFragment3 == null) {
                    mDemandFragment3 = DemoFragmente.newInstance("3", "3");
                    transaction.add(R.id.content, mDemandFragment3);
                } else {
                    transaction.show(mDemandFragment3);
                }
                break;
            case 3:
                if (mDemandFragment4 == null) {
                    mDemandFragment4 = DemoFragmente.newInstance("4", "4");
                    transaction.add(R.id.content, mDemandFragment4);
                } else {
                    transaction.show(mDemandFragment4);
                }
                break;
        }
        transaction.commit();
//        transaction.commitAllowingStateLoss();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mDemandFragment1 != null) {
            transaction.hide(mDemandFragment1);
        }
        if (mDemandFragment2 != null) {
            transaction.hide(mDemandFragment2);
        }
        if (mDemandFragment3 != null) {
            transaction.hide(mDemandFragment3);
        }
        if (mDemandFragment4 != null) {
            transaction.hide(mDemandFragment4);
        }
    }

    @OnClick({R.id.rl_item1, R.id.rl_item2, R.id.rl_item3, R.id.rl_item4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_item1:
                setTabSelection(0);
                break;
            case R.id.rl_item2:
                setTabSelection(1);
                break;
            case R.id.rl_item3:
                setTabSelection(2);
                break;
            case R.id.rl_item4:
                setTabSelection(3);
                break;
        }
    }

}

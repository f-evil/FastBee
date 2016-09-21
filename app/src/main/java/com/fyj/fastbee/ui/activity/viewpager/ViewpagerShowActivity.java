package com.fyj.fastbee.ui.activity.viewpager;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.fyj.fastbee.R;
import com.fyj.fastbee.adapter.MyPagerAdapter;
import com.fyj.fastbee.base.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ViewpagerShowActivity extends BaseAppCompatActivity {

    @BindView(R.id.vpr_show)
    ViewPager vprShow;
    @BindView(R.id.tab_title)
    TabLayout tab_title;

    private List<Fragment> mFragments;
    private List<String> mTitles;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, ViewpagerShowActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_viewpager_show;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {

        mFragments = new ArrayList<>();
        mTitles=new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            mFragments.add(DemoFragmente.newInstance("变量1:" + i, "变量2:" + i));
            mTitles.add("页签"+i);
            tab_title.addTab(tab_title.newTab().setText("页签"+i));
        }

//        tab_title.setTabMode(TabLayout.MODE_FIXED);//平均分
        tab_title.setTabMode(TabLayout.MODE_SCROLLABLE);//可滑动
        tab_title.setTabGravity(TabLayout.GRAVITY_CENTER);//居中
//        tab_title.setTabGravity(TabLayout.GRAVITY_FILL);//填充

    }

    @Override
    protected void getDate() {

    }

    @Override
    protected void initView() {
        vprShow.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mFragments,mTitles));
        tab_title.setupWithViewPager(vprShow);
    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {

    }

}

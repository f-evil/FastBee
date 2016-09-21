package com.fyj.fastbee.ui.activity.other.toolbatlayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fyj.easylinkingutils.utils.ScreenUtils;
import com.fyj.easylinkingutils.utils.SizeUtils;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;

import butterknife.BindView;

public class ToolbarLayoutActivity extends BaseAppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.rl_list)
    RecyclerView rlList;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, ToolbarLayoutActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_toolbar_layout;
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

        setSupportActionBar(toolbar);

        //设置还没收缩时状态下字体颜色
        collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
        //设置收缩后Toolbar上字体的颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);

        rlList.setLayoutManager(new LinearLayoutManager(this));
        rlList.setHasFixedSize(true);
        rlList.setAdapter(new DemoAdapter(this));
    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {

    }

    private class DemoAdapter extends RecyclerView.Adapter {

        private Context context;

        public DemoAdapter(Context context) {
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            TextView tv = new TextView(context);
            tv.setHeight(SizeUtils.dp2px(context, 50));
            tv.setWidth(ScreenUtils.getScreenWidth(context));
            tv.setGravity(Gravity.CENTER);

            return new ViewHolder(tv);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ViewHolder holder1 = (ViewHolder) holder;

            holder1.tv.setText(position + "");

        }

        @Override
        public int getItemCount() {
            return 20;
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);

                tv = (TextView) itemView;
            }

            public TextView tv;
        }
    }
}

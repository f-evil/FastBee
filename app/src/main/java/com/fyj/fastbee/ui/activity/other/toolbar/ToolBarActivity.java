package com.fyj.fastbee.ui.activity.other.toolbar;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class ToolBarActivity extends BaseAppCompatActivity {

    @BindView(R.id.tb_toolbar)
    Toolbar tb_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rl_date)
    RecyclerView rlDate;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, ToolBarActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_tool_bar;
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

        setSupportActionBar(tb_toolbar);

        tv_title.setText("toolbar");

        rlDate.setLayoutManager(new LinearLayoutManager(this));
        rlDate.setHasFixedSize(true);
        rlDate.setAdapter(new DemoAdapter(this));
    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Snack Bar!",Snackbar.LENGTH_SHORT).show();
            }
        });
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

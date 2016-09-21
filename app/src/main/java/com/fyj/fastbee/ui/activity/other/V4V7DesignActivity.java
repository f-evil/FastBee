package com.fyj.fastbee.ui.activity.other;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fyj.easylinkingutils.listener.OnRecyclerViewListener;
import com.fyj.easylinkingutils.utils.ScreenUtils;
import com.fyj.easylinkingutils.utils.SizeUtils;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.ui.activity.other.custombehavior.CustomeBehaviorActivity;
import com.fyj.fastbee.ui.activity.other.toolbar.ToolBarActivity;
import com.fyj.fastbee.ui.activity.other.toolbatlayout.ToolbarLayoutActivity;
import com.fyj.fastbee.ui.application.FastBebApplication;

import butterknife.BindView;

public class V4V7DesignActivity extends BaseAppCompatActivity {

    @BindView(R.id.tb_toolbar)
    Toolbar tb_toolbar;
    @BindView(R.id.rl_date)
    RecyclerView rlDate;

    private String[] data;

    private OnRecyclerViewListener<String> mOnRecyclerViewListener;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, V4V7DesignActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_v4_v7_design;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {
        data = FastBebApplication.getResourse().getStringArray(R.array.V4V7);
    }

    @Override
    protected void getDate() {

    }

    @Override
    protected void initView() {

        setSupportActionBar(tb_toolbar);

        tb_toolbar.setTitle("V4V7实例");

        rlDate.setLayoutManager(new LinearLayoutManager(this));
        rlDate.setHasFixedSize(true);
        rlDate.setAdapter(new DemoAdapter(this));

    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {
        mOnRecyclerViewListener = new OnRecyclerViewListener<String>() {
            @Override
            public void onClick(View view, String bean, int position) {
                switch (position) {
                    case 0:
                        ToolBarActivity.goTo(V4V7DesignActivity.this);
                        break;
                    case 1:
                        ToolbarLayoutActivity.goTo(V4V7DesignActivity.this);
                        break;
                    case 2:
                        CustomeBehaviorActivity.goTo(V4V7DesignActivity.this);
                        break;
                }
            }
        };
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

            holder1.tv.setText(data[position]);

            holder1.pos = position;

        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ViewHolder(View itemView) {
                super(itemView);

                tv = (TextView) itemView;

                tv.setOnClickListener(this);
            }

            public TextView tv;
            private int pos;

            @Override
            public void onClick(View v) {
                mOnRecyclerViewListener.onClick(v, data[pos], pos);
            }
        }
    }


}

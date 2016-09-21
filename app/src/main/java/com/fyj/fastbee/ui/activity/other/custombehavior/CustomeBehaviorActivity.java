package com.fyj.fastbee.ui.activity.other.custombehavior;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fyj.easylinkingutils.utils.ScreenUtils;
import com.fyj.easylinkingutils.utils.SizeUtils;
import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.ui.activity.other.custombehavior.behavior.ScaleDownBehavior;
import com.fyj.fastbee.ui.application.FastBebApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CustomeBehaviorActivity extends BaseAppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tab_layout)
    LinearLayout tabLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private BottomSheetBehavior<View> mBottomSheetBehavior;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, CustomeBehaviorActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_custome_behavior;
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

        toolbar.setTitle("自定义Behavior");

        recyclerView.setHasFixedSize(true);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("我是第" + i + "个");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ListRecyclerAdapter adapter = new ListRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);



        ScaleDownBehavior scaleDownShowFab = ScaleDownBehavior.from(fab);

        scaleDownShowFab.setOnStateChangedListener(onStateChangedListener);

        mBottomSheetBehavior = BottomSheetBehavior.from((View) tabLayout);
    }

    private ScaleDownBehavior.OnStateChangedListener onStateChangedListener = new ScaleDownBehavior.OnStateChangedListener() {
        @Override
        public void onChanged(boolean isShow) {
            mBottomSheetBehavior.setState(isShow ? BottomSheetBehavior.STATE_EXPANDED : BottomSheetBehavior.STATE_COLLAPSED);
        }
    };

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {

    }

    private boolean initialize = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!initialize) {
            initialize = true;
//            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.DefineViewHolder> {

        private List<String> list;

        public ListRecyclerAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public void onBindViewHolder(DefineViewHolder viewHolder, int position) {
            viewHolder.setData(list.get(position));
        }

        @Override
        public DefineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView tx = new TextView(parent.getContext());
            tx.setWidth(ScreenUtils.getScreenWidth(FastBebApplication.getAppContext().get()));
            tx.setHeight(SizeUtils.dp2px(FastBebApplication.getAppContext().get(), 50));
            tx.setGravity(Gravity.CENTER);
            return new DefineViewHolder(tx);
        }

        class DefineViewHolder extends RecyclerView.ViewHolder {

            TextView tvTitle;

            public DefineViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView;
            }

            public void setData(String data) {
                tvTitle.setText(data);
            }

        }

    }

}

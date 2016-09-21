package com.fyj.fastbee.ui.activity.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fyj.easylinkingutils.listener.OnSubMultipleClickListener;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppFragment;

import butterknife.BindView;

public class DemoFragmente extends BaseAppFragment {

    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";

    @BindView(R.id.tv_text)
    TextView tvText;

    private String mParam1;
    private String mParam2;


    public DemoFragmente() {

    }


    public static DemoFragmente newInstance(String param1, String param2) {
        DemoFragmente fragment = new DemoFragmente();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    protected int setLayout() {
        return R.layout.fragment_demo;
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
        tvText.setText("mParam1 : " + mParam1 + " ; " + "mParam2 :" + mParam2);
    }

    @Override
    protected void initBroadCast() {

    }

    @Override
    protected void bindEvent() {
        tvText.setOnClickListener(new OnSubMultipleClickListener() {
            @Override
            public void onSubClick(View v) {
                super.onSubClick(v);
                startActivity(new Intent(getActivity(),DemoFragmentActivity.class));
            }
        });
    }

}

package com.fyj.fastbee.ui.activity.retrofit;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.bean.YueProjectBean;
import com.fyj.fastbee.bean.base.BaseObjectBean;
import com.fyj.fastbee.http.RetrofitHelper;
import com.fyj.fastbee.utils.RxUtil;
import com.google.gson.Gson;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;

public class RetrofitActivit extends BaseAppCompatActivity {


    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.textView)
    TextView mTextView;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, RetrofitActivit.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_retrofit;
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

    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {

    }

    @OnClick(R.id.button)
    public void onClick() {
        get();
    }

    private void get() {
        Subscription subscribe = RetrofitHelper.
                getActivityList("2043", "all", "cafb2d74-0975-454f-80fd-a5e100c31d20,fbd3105f-677c-4fe3-91ea-a5e100c322ee", "0")
                .compose(RxUtil.<BaseObjectBean<YueProjectBean>>rxSchedulerHelper())
                .compose(RxUtil.<YueProjectBean>handleResult())
                .compose(this.<YueProjectBean>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new Observer<YueProjectBean>() {
                    @Override
                    public void onCompleted() {
                        ToastUtil.makeText("end");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTextView.setText(e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(YueProjectBean yueProjectBean) {
                        mTextView.setText(new Gson().toJson(yueProjectBean));
                    }
                });
    }
}

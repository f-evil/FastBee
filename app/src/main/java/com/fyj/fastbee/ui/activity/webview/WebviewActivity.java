package com.fyj.fastbee.ui.activity.webview;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.fyj.easylinkingutils.utils.StringUtil;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.ui.activity.webview.jsmethod.JavaScriptInterface2;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class WebviewActivity extends BaseAppCompatActivity {

    private static final String SHARE_MEMORY_ADDR_NAME = "YLJsInterface";
    private static final String KEY_URL = "url";
    private static final String KEY_TITLE = "title";

    @BindView(R.id.fl_web)
    FrameLayout flWeb;
    @BindView(R.id.tb_title)
    Toolbar tbTitle;

    private String mUrl="";
    private String mTitle="";

    private WebviewFragmente mWebviewFragmente;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, WebviewActivity.class));
    }

    public static Intent newIntent(Context context, String extraURL, String extraTitle) {
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra(KEY_URL, extraURL);
        intent.putExtra(KEY_TITLE, extraTitle);
        return intent;
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void getDate() {
        Intent intent = getIntent();
        if (intent!=null){
            String url = intent.getStringExtra(KEY_URL);
            String title = intent.getStringExtra(KEY_TITLE);
            if (StringUtil.isEmpty(url)){
                mUrl="";
            }else {
                mUrl=url;
            }

            if (StringUtil.isEmpty(title)){
                mTitle="";
            }else {
                mTitle=title;
            }
        }
    }

    @Override
    protected void initView() {

        setSupportActionBar(tbTitle);

        tbTitle.setTitle("webview");

        mWebviewFragmente = WebviewFragmente.newInstance();
        mWebviewFragmente.setHomeUrl("http://t1.easylinking.net:10003/elinkMobile/personalCard/personalCardIndex.do?userId=2043&myUserId=2043");
//        mWebviewFragmente.setHomeUrl("http://www.nhhnhjo.com");
        mWebviewFragmente.addJavascriptInterface(new JavaScriptInterface2(this),SHARE_MEMORY_ADDR_NAME);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_web, mWebviewFragmente);
        transaction.commit();

        mWebviewFragmente.setOnWebCallback(new WebviewFragmente.OnWebCallback() {
            @Override
            public void onTitleRecriveBack(String name) {
                tbTitle.setTitle(name);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {

    }

}

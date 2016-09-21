package com.fyj.fastbee.ui.activity.webview;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class WebviewFragmente extends BaseAppFragment {

    private static String ERRORPAGE = "file:///android_asset/web/404.html";

    @BindView(R.id.wv_web)
    WebView wvWeb;
    @BindView(R.id.ptr_fl)
    PtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private String mOrginUrl = "";

    private Map<String, Object> jsinterfaces = new HashMap<>();;

    private OnWebCallback mOnWebCallback;

    public WebviewFragmente() {

    }

    public static WebviewFragmente newInstance() {
        return new WebviewFragmente();
    }

    @Override
    public void onPause() {
        super.onPause();
        wvWeb.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        wvWeb.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_webview_fragmente;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {

        initWebSetting();
    }

    @Override
    protected void getDate() {

    }

    @Override
    protected void initView() {
        initPtr();
    }

    @Override
    protected void initBroadCast() {

    }

    @Override
    protected void bindEvent() {

    }

    public void setHomeUrl(String url) {
        mOrginUrl = url;
    }

    public void loadUrl(String url) {
        if (url!=null&&url.startsWith("http")){
            mOrginUrl = url;
        }
        if (wvWeb!=null) {
            wvWeb.loadUrl(url);
        }
        XLog.e(url);
    }

    public void addJavascriptInterface(Object object, String name) {
        jsinterfaces.put(name, object);
    }

    private void addInterface() {
        if (!jsinterfaces.isEmpty()) {
            for (String name : jsinterfaces.keySet()) {
                wvWeb.addJavascriptInterface(jsinterfaces.get(name), name);
            }
        }
    }

    private void initWebSetting() {
        WebSettings settings = wvWeb.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        wvWeb.clearFormData();
        wvWeb.clearCache(true);
        wvWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                loadUrl(url);
                return true;
            }

            // 网页加载网页加载完成后是正在加载的图标消失
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadUrl("javascript:getCurrentUserId(2043)");
                if (pbLoading!=null){
                    pbLoading.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbLoading.setProgress(0);
                pbLoading.setVisibility(View.VISIBLE);
            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                loadUrl(ERRORPAGE + "?url=" + mOrginUrl);
            }
        });

        wvWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                try {
                    if (newProgress == 100) {
                            mPtrFrame.refreshComplete();
                    } else {
                        pbLoading.setProgress(newProgress);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                try {
                    if (mOnWebCallback != null) {
                        mOnWebCallback.onTitleRecriveBack(title);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        addInterface();
    }

    private void initPtr() {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        mPtrFrame.setPullToRefresh(false);
        mPtrFrame.setKeepHeaderWhenRefresh(true);
//        mPtrFrame.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mPtrFrame.autoRefresh();
//            }
//        }, 100);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadUrl(mOrginUrl);
            }
        });

        loadUrl(mOrginUrl);

    }

    public boolean goBack() {
        if (wvWeb != null && wvWeb.canGoBack()) {
            wvWeb.goBack();
            return true;
        }
        return false;
    }

    public interface OnWebCallback {
        void onTitleRecriveBack(String name);
    }

    public void setOnWebCallback(OnWebCallback mOnWebCallback) {
        this.mOnWebCallback = mOnWebCallback;
    }
}

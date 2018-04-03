package com.taihold.yuxiangcar.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.taihold.yuxiangcar.util.JsInterFace;
import com.taihold.yuxiangcar.util.NetWorkUtil;
import com.taihold.yuxiangcar.util.toolUtil;
/**
 * Created by jxy on 2018/4/3.
 */

public class WebActivity extends AppCompatActivity {
    WebView webView;
    WebSettings mWebSettings;
    public static String APP_CACAHE_DIRNAME = "yuxiangview";
    public String TAG = "WebActivity";
    LinearLayout mLayout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mLayout = findViewById(R.id.webview_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new WebView(getApplicationContext());
        webView.setLayoutParams(params);
        mLayout.addView(webView);
        mWebSettings = webView.getSettings();
        //与JS交互
        mWebSettings.setJavaScriptEnabled(true);
        //设置自适用屏幕,两者合用
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);//缩放到屏幕大小
        //缩放操作
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDisplayZoomControls(false);
        if (NetWorkUtil.isNetworkConnected(this)) {
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        //其他细节操作
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        //离线加载
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        mWebSettings.setAppCachePath(cacheDirPath);//设置Application Caches 缓存目录
        sharedPreferences=getSharedPreferences("YuXData",MODE_PRIVATE);
        webView.addJavascriptInterface(new AndroidJS(this),"JSHook");
        //加载网页
        webView.loadUrl(getIntent().getStringExtra(FusionAction.WEB_KEY.URL));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                Log.v(TAG, "############打印当前的url" + request.getUrl());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //设定加载的操作
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //设定加载结束的操作
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //记在出错时,调用本地的页面展示如404
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                //处理https请求,webview默认是不处理https请求
            }
        });

    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
    class  AndroidJS implements JsInterFace {
        private Context context;
        public AndroidJS(Context context)
        {
            this.context = context;

        }
        @Override
        @JavascriptInterface
        public void openNewActivity(String title, String url){
            Intent intent = new Intent(FusionAction.WEB_ACTION);
            intent.putExtra(FusionAction.WEB_KEY.URL, url);
            intent.putExtra(FusionAction.WEB_KEY.TITLE, title);
            toolUtil.clearWebCache(WebActivity.this);
            context.startActivity(intent);
        }
        @Override
        @JavascriptInterface
        public void openTransTitleActivity(String title, String url) {

        }

        @Override
        @JavascriptInterface
        public String getUserName() {
            String userName = sharedPreferences.getString("loginName", null);
            return userName;
        }

        @Override
        @JavascriptInterface
        public void openLogin() {
            startActivity(new Intent(FusionAction.LOGIN_ACTION));
        }

        @Override
        @JavascriptInterface
        public String getUserId() {
            String sid = sharedPreferences.getString("sid", null);
            return sid;
        }

        @Override
        @JavascriptInterface
        public void setTitle(String title) {
           WebActivity.this.setTitle(title);
        }

        @Override
        @JavascriptInterface
        public void dialModel(String phone1, String phone2) {
        }
        @Override
        @JavascriptInterface
        public void finish() {
            finish();
        }

        @Override
        @JavascriptInterface
        public void jumpMap(String latitude, String longitude) {
        }

        @Override
        @JavascriptInterface
        public void backToHomepage(){
        }

        @Override
        public void getLocation() {

        }
    }
}
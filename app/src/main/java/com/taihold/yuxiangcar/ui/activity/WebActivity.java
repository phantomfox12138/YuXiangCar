package com.taihold.yuxiangcar.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.taihold.yuxiangcar.logic.HttpHelper;
import com.taihold.yuxiangcar.util.JsInterface;
import com.taihold.yuxiangcar.util.NetWorkUtil;
import com.taihold.yuxiangcar.util.toolUtil;
import com.xander.panel.PanelInterface;
import com.xander.panel.XanderPanel;

/**
 * Created by jxy on 2018/4/3.
 */
public class WebActivity extends AppCompatActivity {
    WebView webView;
    WebSettings mWebSettings;
    public static String APP_CACAHE_DIRNAME = "yuxiangview";
    public String TAG = "WebActivity";
    private SharedPreferences sharedPreferences;
    private ImageView mLoading, mLoadFailed;
    private AnimationDrawable loadingDrawable;
    private TextView webTitle, webRight;
    private ImageView webBack, webRightImage;
    private LinearLayout titleBarLayout;
    private int andVersion = 0;
    private String currentUrl = "";
    private int isCollectType = 0;//1表示收藏,0表示未收藏
    private String secondCarId;//二手车id
    private String toReviewId = "";//跳到评价页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        andVersion = Build.VERSION.SDK_INT;
        webView = findViewById(R.id.webview);
        webBack = findViewById(R.id.web_back);
        // mLoading=findViewById(R.id.loading);
        mLoadFailed = findViewById(R.id.load_failed);
        webTitle = findViewById(R.id.web_title);
        titleBarLayout = findViewById(R.id.title_bar_layout);
        //右面的设置文字的按钮[发布,保存]
        webRight = findViewById(R.id.web_right);
        webRightImage = findViewById(R.id.web_right_image);
        //loadingDrawable =mLoading.getDrawable();

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
        sharedPreferences = getSharedPreferences("YuXData", MODE_PRIVATE);
        //JS调用Android的方法
        webView.addJavascriptInterface(new AndroidJS(this), "JSHook");


        //加载网页
        if (getIntent().getStringExtra(FusionAction.WEB_KEY.VISIBLE) == null) {//默认展示
            titleBarLayout.setVisibility(View.VISIBLE);
        } else if (getIntent().getStringExtra(FusionAction.WEB_KEY.VISIBLE).equals("1")) {
            //显示导航栏
            titleBarLayout.setVisibility(View.VISIBLE);
        } else if (getIntent().getStringExtra(FusionAction.WEB_KEY.VISIBLE).equals("0")) {
            titleBarLayout.setVisibility(View.GONE);
        }
        webTitle.setText(getIntent().getStringExtra(FusionAction.WEB_KEY.TITLE));
        webView.loadUrl(getIntent().getStringExtra(FusionAction.WEB_KEY.URL));
        currentUrl = getIntent().getStringExtra(FusionAction.WEB_KEY.URL);
        Log.v(TAG,"##########是否可以返回"+webView.canGoBack());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //设定加载的操作
                // loadingDrawable.start();//动画加载
                mLoadFailed.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //设定加载结束的操作
                //  loadingDrawable.stop();//动画加载

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //记在出错时,调用本地的页面展示如404
                mLoadFailed.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                //处理https请求,webview默认是不处理https请求
            }
        });
        //设置左面返回按钮
        webBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });

        //设置右面按钮的文字
        webRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //根据title的不同,去变换调用js的接口
                if (currentUrl.indexOf("addAddress.html") != -1) {
                    //新增地址-保存
                    if (andVersion < 18) {
                        webView.loadUrl("javascript:toApply()");
                    } else {
                        webView.evaluateJavascript("javascript:toApply()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为js 返回的结果
                            }
                        });
                    }
                } else if (currentUrl.indexOf("addressMgr.html") != -1) {
                    //地址管理-新增
                    if (andVersion < 18) {
                        webView.loadUrl("javascript:toAddress()");
                    } else {
                        webView.evaluateJavascript("javascript:toAddress()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为js 返回的结果
                            }
                        });
                    }

                } else if (currentUrl.indexOf("carDetail.html") != -1) {
                    if (andVersion < 18) {
                        webView.loadUrl("javascript:toCollect()");
                    } else {
                        webView.evaluateJavascript("javascript:toCollect()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为js 返回的结果
                                Log.v(TAG,"#############打印当前的收藏状态"+value);
                            }
                        });
                    }
                } /*else if (false) {
                    if (andVersion < 18) {
                        webView.loadUrl("javascript:isShowIcon()");
                    } else {
                        webView.evaluateJavascript("javascript:isShowIcon()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为js 返回的结果
                            }
                        });
                    }
                }*/ /*else if (false) {//关闭弹框
                    if (andVersion < 18) {
                        webView.loadUrl("javascript:closeModel()");
                    } else {
                        webView.evaluateJavascript("javascript:closeModel()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为js 返回的结果
                            }
                        });
                    }
                }*/
            }
        });

    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            Log.v(TAG,"KeyDown##################"+webView.canGoBack());
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

    class AndroidJS implements JsInterface {
        private Context context;

        public AndroidJS(Context context) {
            this.context = context;

        }

        @Override
        @JavascriptInterface
        public void openNewActivity(String title, String url) {
            Intent intent = new Intent(getApplicationContext(), WebActivity.class);
            intent.putExtra(FusionAction.WEB_KEY.URL, HttpHelper.HTTP_WEBURL + url);
            intent.putExtra(FusionAction.WEB_KEY.TITLE, title);
            intent.putExtra(FusionAction.WEB_KEY.VISIBLE, "1");
            toolUtil.clearWebCache(WebActivity.this);
            context.startActivity(intent);
        }

        @Override
        @JavascriptInterface
        public void openTransTitleActivity(String url) {//打开新页面（针对要有透明导航栏的页面）
            Intent intent = new Intent(getApplicationContext(), WebActivity.class);
            intent.putExtra(FusionAction.WEB_KEY.URL, HttpHelper.HTTP_WEBURL + url);
            intent.putExtra(FusionAction.WEB_KEY.TITLE, "");
            intent.putExtra(FusionAction.WEB_KEY.VISIBLE, "0");
            toolUtil.clearWebCache(WebActivity.this);
            context.startActivity(intent);
        }

        @Override
        @JavascriptInterface
        public String getUserName() {
            String userName = sharedPreferences.getString("loginName", null);
            return userName;
        }

        @Override
        @JavascriptInterface
        public void goBack() {
            webView.post(new Runnable(){
                @Override
                public void run() {
                    if (webView.canGoBack()){
                        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                        webView.goBack();//返回上个页面
                    }else{
                        finish();
                    }
                }
            });
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

        }

        @Override
        @JavascriptInterface
        public void dialModel(String phone) {
            Intent intent1 = new Intent(Intent.ACTION_DIAL);
            intent1.setData(Uri.parse("tel:" + phone));
            startActivity(intent1);
        }

        @Override
        @JavascriptInterface
        public void finish11() {
            if (webView.canGoBack()) {
                webView.goBack();
            }else{
                finish();
            }
        }

        @Override
        @JavascriptInterface
        public void weiPayFor() {

        }

        @Override
        @JavascriptInterface
        public void alipayPayFor() {

        }

        @Override
        @JavascriptInterface
        public void bankPayFor() {

        }

        @Override
        @JavascriptInterface
        public void toReviewList(String id) {
            //跳到评价页面
            toReviewId = id;
        }

        @Override
        @JavascriptInterface
        public void toPublishCar() {
            webView.loadUrl(FusionAction.WEB_KEY.SECONDHANDCAR);
        }

        @Override
        @JavascriptInterface
        public void toEditCar(String id) {
            secondCarId = id;
        }

        @Override
        @JavascriptInterface
        public void isCollect(int type) {
            isCollectType = type;
        }

        @Override
        @JavascriptInterface
        public void toReviewPage(String imgUrl, String id) {

        }

        @Override
        @JavascriptInterface
        public String getMinePosition() {
            SharedPreferences mSp = context.getSharedPreferences("location_sp",
                    Context.MODE_PRIVATE);
            String detailAddress = mSp.getString("detailAddress", null);
            return detailAddress;
        }

        @Override
        @JavascriptInterface
        public void modifyTitle(String title) {
            webTitle.setText(title);
        }

        @Override
        @JavascriptInterface
        public void toShare() {
            XanderPanel.Builder mBuilder = new XanderPanel.Builder(WebActivity.this);
            mBuilder.grid(1, 3)
                    .setMenu(R.menu.main_menu, new PanelInterface.PanelMenuListener() {
                        @Override
                        public void onMenuClick(MenuItem menuItem) {

                        }
                    })
                    .setGravity(Gravity.TOP)
                    .setCanceledOnTouchOutside(true);
            XanderPanel xanderPanel = mBuilder.create();
            xanderPanel.show();
        }
        @Override
        @JavascriptInterface
        public void finished() {
            if (webView.canGoBack()) {
                webView.goBack();
            }
        }

        @Override
        @JavascriptInterface
        public void toHomepage() {
            startActivity(new Intent(FusionAction.HOME_ACTION));
            finish();
        }

        @Override
        @JavascriptInterface
        public void jumpMap(String latitude, String longitude) {
            //跳转到高德地图导航
        }

        @Override
        @JavascriptInterface
        public String getLocation(){
            SharedPreferences mSp = context.getSharedPreferences("location_sp",
                    Context.MODE_PRIVATE);
            String latitiude = mSp.getString("latitiude", null);
            String longitude = mSp.getString("longitude", null);
            String city = mSp.getString("city", null);
            String province=mSp.getString("province",null);
            return latitiude + "," + longitude + "," + province+","+city;
        }
    }
}

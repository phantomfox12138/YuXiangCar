package com.taihold.yuxiangcar.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.taihold.yuxiangcar.logic.HttpHelper;
import com.taihold.yuxiangcar.ui.activity.WebActivity;
import com.taihold.yuxiangcar.util.JsInterface;
import com.taihold.yuxiangcar.util.NetWorkUtil;
import com.taihold.yuxiangcar.util.toolUtil;

import static android.content.Context.MODE_PRIVATE;
import static com.taihold.yuxiangcar.ui.activity.WebActivity.APP_CACAHE_DIRNAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {
    private static final String TAG = "StoreFragment";
    private View mRootView;
    WebView webView;
    WebSettings mWebSettings;
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_store, container, false);
        initView();
        return mRootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        webView.reload();
    }

    private void initView() {
        webView = mRootView.findViewById(R.id.store_web);
        mWebSettings = webView.getSettings();
        sharedPreferences = getActivity().getSharedPreferences("YuXData", MODE_PRIVATE);
        //与JS交互
        mWebSettings.setJavaScriptEnabled(true);
        //设置自适用屏幕,两者合用
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);//缩放到屏幕大小
        //缩放操作
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDisplayZoomControls(false);
        if (NetWorkUtil.isNetworkConnected(getActivity())) {
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
        String cacheDirPath = getActivity().getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        mWebSettings.setAppCachePath(cacheDirPath);//设置Application Caches 缓存目录
        sharedPreferences = getActivity().getSharedPreferences("YuXData", MODE_PRIVATE);
        //JS调用Android的方法
        webView.addJavascriptInterface(new AndroidJS(getContext()), "JSHook");
        webView.loadUrl(HttpHelper.HTTP_WEBURL + FusionAction.WEB_KEY.STORELIST);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
        });
    }

    @Override
    public void onDestroyView() {

        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroyView();
    }

    class AndroidJS implements JsInterface {
        private Context context;

        public AndroidJS(Context context) {
            this.context = context;

        }

        @Override
        @JavascriptInterface
        public void openNewActivity(String title, String url) {
            Intent intent = new Intent(getContext(), WebActivity.class);
            intent.putExtra(FusionAction.WEB_KEY.URL, HttpHelper.HTTP_WEBURL + url);
            intent.putExtra(FusionAction.WEB_KEY.TITLE, title);
            intent.putExtra(FusionAction.WEB_KEY.VISIBLE, "1");
            toolUtil.clearWebCache(getActivity());
            context.startActivity(intent);
        }

        @Override
        @JavascriptInterface
        public void openTransTitleActivity(String url) {//打开新页面（针对要有透明导航栏的页面）
            Intent intent = new Intent(getContext(), WebActivity.class);
            intent.putExtra(FusionAction.WEB_KEY.URL, HttpHelper.HTTP_WEBURL + url);
            intent.putExtra(FusionAction.WEB_KEY.TITLE, "");
            toolUtil.clearWebCache(getActivity());
            intent.putExtra(FusionAction.WEB_KEY.VISIBLE, "0");
            context.startActivity(intent);
        }

        @Override
        @JavascriptInterface
        public String getUserName() {
            String userName = sharedPreferences.getString("loginName", null);
            return userName;
        }

        @Override
        public void goBack(String reload) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上个页面
            } else {
                getActivity().finish();
            }
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
        public void finish11() {
            if (webView.canGoBack()) {
                webView.goBack();//返回上个页面
            } else {
                getActivity().finish();
            }
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

        }

        @Override
        @JavascriptInterface
        public void toPublishCar() {
            webView.loadUrl(FusionAction.WEB_KEY.SECONDHANDCAR);
        }

        @Override
        @JavascriptInterface
        public void toEditCar(String id) {
        }

        @Override
        @JavascriptInterface
        public void isCollect(int type) {
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
        }

        @Override
        @JavascriptInterface
        public void toShare() {
        }

        @Override
        @JavascriptInterface
        public void finished() {
        }

        @Override
        @JavascriptInterface
        public void reloadUrl() {
            Log.v(TAG, "################reloadUrl");
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.reload();
                }
            });

        }

        @Override
        @JavascriptInterface
        public void toHomepage() {
            startActivity(new Intent(FusionAction.HOME_ACTION));
        }

        @Override
        @JavascriptInterface
        public void jumpMap(String latitude, String longitude) {
            //跳转到高德地图导航
        }

        @Override
        @JavascriptInterface
        public String getLocation() {
            SharedPreferences mSp = context.getSharedPreferences("location_sp",
                    Context.MODE_PRIVATE);
            String latitiude = mSp.getString("latitiude", null);
            String longitude = mSp.getString("longitude", null);
            String city = mSp.getString("city", null);
            String province = mSp.getString("province", null);
            return latitiude + "," + longitude + "," + province + "," + city;
        }
    }
}

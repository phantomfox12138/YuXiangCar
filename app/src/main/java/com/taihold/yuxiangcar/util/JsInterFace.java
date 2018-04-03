package com.taihold.yuxiangcar.util;

import android.webkit.JavascriptInterface;

/**
 * Created by jxy on 2018/4/3.
 */

public interface JsInterFace {
    @JavascriptInterface
    public void openNewActivity(String title, String url);//跳转新的Activity

    @JavascriptInterface
    public void openTransTitleActivity(String title, String url);//打开透明标题栏页签

    @JavascriptInterface
    public String getUserName();//获取登录用户名

    @JavascriptInterface
    public void openLogin();//跳转登录

    @JavascriptInterface
    public String getUserId();//获取登录用户id

    @JavascriptInterface
    public void setTitle(String title);//设置标题

    @JavascriptInterface
    public void dialModel(final String phone1, String phone2);//拨打电话

    @JavascriptInterface
    public void finish();//关闭页签

    @JavascriptInterface
    public void jumpMap(String latitude, String longitude);////打开地图

    @JavascriptInterface
    public void backToHomepage();////回到首页
    @JavascriptInterface
    public void getLocation();//获取用户的当前位置
}

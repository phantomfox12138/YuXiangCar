package com.taihold.yuxiangcar.util;

import android.webkit.JavascriptInterface;

/**
 * Created by jxy on 2018/4/3.
 */

public interface JsInterface {
    @JavascriptInterface
    public void toHomepage();//回到首页
    @JavascriptInterface
    public void jumpMap(String latitude, String longitude);////打开地图
    @JavascriptInterface
    public String getLocation();//获取用户的当前位置
    @JavascriptInterface
    public void dialModel(String phone);//拨打电话
    @JavascriptInterface
    public String getUserId();//获取登录用户id
    @JavascriptInterface
    public void openNewActivity(String title, String url);//跳转新的Activity
    @JavascriptInterface
    public String getUserName();//获取登录用户名
    @JavascriptInterface
    public void goBack();//不关闭当前页面，返回上个页面
    @JavascriptInterface
    public void openLogin();//跳到登录页面
    @JavascriptInterface
    public void openTransTitleActivity(String url);//打开新页面（针对要有透明导航栏的页面）
    @JavascriptInterface
    public void setTitle(String title);//设置标题
    @JavascriptInterface
    public void finish11();//关闭页签
    @JavascriptInterface
    public void weiPayFor();//微信支付
    @JavascriptInterface
    public void alipayPayFor();//支付宝支付
    @JavascriptInterface
    public void bankPayFor();//银联支付
    @JavascriptInterface
    public  void toReviewList(String id);//跳到评价页面
    @JavascriptInterface
    public  void toPublishCar();//跳转到新增二手车页面
    @JavascriptInterface
    public  void toEditCar(String id);//跳转到跳转到修改二手车页面
    @JavascriptInterface
    public void  isCollect(int type);//carDetail.html的收藏按钮
    @JavascriptInterface
    public void toReviewPage(String imgUrl,String id);//跳转到添加评价页面
    @JavascriptInterface
    public String getMinePosition();//获取当前详细定位。准确到街道roadRescue.html
    @JavascriptInterface
    public void modifyTitle(String title);//修改导航栏title
    @JavascriptInterface
    public  void toShare();//分享弹框
    @JavascriptInterface
    public void finished();//关闭当前页面，相当于替换

}

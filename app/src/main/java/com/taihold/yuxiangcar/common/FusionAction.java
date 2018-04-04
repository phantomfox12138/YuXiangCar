package com.taihold.yuxiangcar.common;

/**
 * Created by niufan on 18/3/28.
 */

public class FusionAction {
    public static final int BASE_CODE = 0xff;

    public static final String HOME_ACTION = "com.taihold.yuxiang.HOME_ACTION";

    public static final String LOGIN_ACTION = "com.taihold.yuxiang.LOGIN_ACTION";

    public static final String PROFILE_ACTION = "com.taihold.yuxiang.PROFILE_ACTION";

    public static final String ADDRESS_LIST = "com.taihold.yuxiang.ADDRESS_LIST";

    public static final String ADDRESS_EDIT = "com.taihold.yuxiang.EDIT_ADDRESS";

    public static final String SETTING_ACTION = "com.taihold.yuxiang.SETTING_ACTION";

    public static final String EDIT_NICKNAME = "com.taihold.yuxiang.EDIT_NICKNAME";

    public static final String IMAGE_VALIDE_ACTION = "com.taihold.yuxiang.IMAGE_VERIFY";

    public static final String REGIST_ACTION = "com.taihold.yuxiang.REGIST_ACTION";

    public static final String WEB_ACTION = "com.taihold.yuxiang.WEB_ACTION";



    public interface LoginExtra {
        String USERNAME = "username";

        String IMAGE_VALIDE = "image_valide";

        int IMAGE_CODE = BASE_CODE - 1;
    }

    public interface WEB_KEY{
        String URL ="web_url";
        String TITLE ="web_title";
        String VERTICLEMANTAINMENU="verticleMantainMenu.html";//汽修目录
        String VERTICLEDETAILLIST="verticleDetailList.html";//汽修列表
        String CARBEAUTYDETAIL="carBeautyDetail.html";//店铺详情
        String CARBEAUTYORDERSUBMIT="carBeautyOrderSubmit";//需要调支付
        String ROADRESCUEMENU="roadRescueMenu.html";//道路救援目录
        String ROADRESCUE="roadRescue.html";//
        String CARBEAUTY="carBeauty.html";//
        String REPLACEMENTTIRES="replacementTires.html";//
        String TYRELIST="tyreList.html";
        String TYREDETAIL="tyreDetail.html";
        String AUTOPARTSORDERSUBMIT="autoPartsOrderSubmit.html";//调用支付
        String AUTOPARTS="autoParts.html";
        String SECONDHANDCAR="secondHandCar.html";
        String CARDETAIL="carDetail.html";
        String SUBMITSUCCEED="submitSucceed.html";
        String STORELIST="storeList.html";
        String ADDRESSMGR="addressMgr.html";
        String ADDADDRESS="addAddress.html";
        String myorder="myOrder.html";
        String ORDERDETAIL2="orderDetail2.html";
        String ORDERDETAIL="orderDetail.html";
        String ORDERDETAIL3="orderDetail3.html";
        String MINECAR="mineCar.html";//
        String MINESECONDHANDCAR="mineSecondHandCar.html";//
        String MYCOLLECTION="myCollection.html";//
        String cardCoupons="cardCoupons.html";//
    }
}

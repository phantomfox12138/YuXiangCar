package com.taihold.yuxiangcar.logic;

/**
 * Created by niufan on 18/3/29.
 */

public class HttpHelper
{
    public static final String HTTP_HEADER = "http://car.dagegou.com";
    
    public static final String REQUEST_VALIDE_IMAGE = "/car/validateCode/image";
    
    //test
    public static final String REQUEST_SMS_CODE_TEST = "/car/sms/test";
    
    public static final String REQUEST_SMS_CODE = "/car/sms/register";
    
    /**
     * 验证短信验证码
     */
    public static final String REQUEST_VALIDE_SMS_CODE = "/car/sms/validate";
    
    /**
     * 用户注册
     */
    public static final String REQUEST_USER_REGIST = "/car/login/register";
    
    /**
     * 用户登录
     */
    public static final String REQUEST_USER_LOGIN = "/car/login/login";
}

package com.taihold.yuxiangcar.common;

/**
 * Created by niufan on 18/3/28.
 */

public class FusionAction
{
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
    
    public interface LoginExtra
    {
        String USERNAME = "username";
        
        String IMAGE_VALIDE = "image_valide";
        
        int IMAGE_CODE = BASE_CODE - 1;
    }
}

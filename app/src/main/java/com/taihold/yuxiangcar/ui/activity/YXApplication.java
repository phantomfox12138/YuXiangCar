package com.taihold.yuxiangcar.ui.activity;

import android.app.Application;

import com.yanzhenjie.nohttp.NoHttp;

/**
 * Created by niufan on 18/3/29.
 */

public class YXApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        
        NoHttp.initialize(this);
    }
}

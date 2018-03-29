package com.taihold.yuxiangcar.ui.activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.taihold.yuxiangcar.logic.BaseLogic;
import com.taihold.yuxiangcar.logic.HomeLogic;

/**
 * Created by niufan on 18/3/29.
 */

public abstract class BaseActivity extends FragmentActivity
{
    private Map<Class, BaseLogic> mLogicMap = new HashMap<>();
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        addLogic();
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }
    
    protected BaseLogic getLogic(Class clazz)
    {
        return mLogicMap.get(clazz);
    }
    
    private void addLogic()
    {
        HomeLogic home = new HomeLogic(this);
        
        mLogicMap.put(HomeLogic.class, home);
    }
}

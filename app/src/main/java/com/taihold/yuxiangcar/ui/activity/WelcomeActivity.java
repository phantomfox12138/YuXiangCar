package com.taihold.yuxiangcar.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        new Thread()
        {
            
            @Override
            public void run()
            {
                super.run();
                
                try
                {
                    sleep(2500);
                    
                    if (!AndPermission.hasPermission(WelcomeActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS))
                    {
                        AndPermission.with(WelcomeActivity.this)
                                .permission(Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)
                                .callback(new PermissionListener()
                                {
                                    @Override
                                    public void onSucceed(
                                            int requestCode,
                                            @NonNull List<String> grantPermissions)
                                    {
                                        startActivity(new Intent(
                                                FusionAction.HOME_ACTION));
                                        finish();
                                    }
                                    
                                    @Override
                                    public void onFailed(
                                            int requestCode,
                                            @NonNull List<String> deniedPermissions)
                                    {
                                        return;
                                    }
                                })
                                .start();
                    }
                    else
                    {
                        startActivity(new Intent(FusionAction.HOME_ACTION));
                        finish();
                    }
                    
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

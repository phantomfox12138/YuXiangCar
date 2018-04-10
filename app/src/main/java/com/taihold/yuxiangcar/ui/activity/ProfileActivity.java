package com.taihold.yuxiangcar.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.taihold.yuxiangcar.logic.HttpHelper;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yongchun.library.view.ImageSelectorActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity
{
    
    private View mIconView;
    
    private String mUserIconPath;
    
    private CircleImageView mUserIcon;
    
    private View mAddrBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        initView();
    }
    
    private void initView()
    {
        mIconView = findViewById(R.id.icon_btn);
        mUserIcon = findViewById(R.id.user_icon);
        mAddrBtn = findViewById(R.id.address_btn);
        
        mAddrBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                intent.putExtra(FusionAction.WEB_KEY.URL,
                        HttpHelper.HTTP_WEBURL
                                + FusionAction.WEB_KEY.ADDRESSMGR);
                intent.putExtra(FusionAction.WEB_KEY.TITLE, "收货地址");
                startActivity(intent);
            }
        });
        
        findViewById(R.id.nickname_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(FusionAction.EDIT_NICKNAME));
            }
        });
        
        mIconView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!AndPermission.hasPermission(ProfileActivity.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    AndPermission.with(ProfileActivity.this)
                            .permission(Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .callback(new PermissionListener()
                            {
                                @Override
                                public void onSucceed(int requestCode,
                                        List<String> grantPermissions)
                                {
                                    ImageSelectorActivity.start(ProfileActivity.this,
                                            1,
                                            ImageSelectorActivity.MODE_SINGLE,
                                            true,
                                            true,
                                            false);
                                }
                                
                                @Override
                                public void onFailed(int requestCode,
                                        List<String> deniedPermissions)
                                {
                                    
                                }
                            })
                            .start();
                }
                else
                {
                    ImageSelectorActivity.start(ProfileActivity.this,
                            1,
                            ImageSelectorActivity.MODE_SINGLE,
                            true,
                            true,
                            false);
                }
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK
                && requestCode == ImageSelectorActivity.REQUEST_IMAGE)
        {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            
            //            Log.d(TAG, "image = " + images.get(0));
            
            mUserIconPath = images.get(0);
            
            mUserIcon.setImageBitmap(BitmapFactory.decodeFile(mUserIconPath));
            //            mHomeLogic.uploadDeliveryImg(mUserIconPath);
            
        }
    }
}

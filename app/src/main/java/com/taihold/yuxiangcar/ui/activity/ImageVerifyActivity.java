package com.taihold.yuxiangcar.ui.activity;

import static com.taihold.yuxiangcar.common.FusionAction.LoginExtra.IMAGE_VALIDE;
import static com.taihold.yuxiangcar.common.FusionAction.LoginExtra.USERNAME;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.logic.HomeLogic;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

public class ImageVerifyActivity extends BaseActivity
{
    
    private EditText mImgConfirmEdit;
    
    private ImageView mConfirmImg;
    
    private TextInputLayout mImgConfirmTil;
    
    private Button mImgVerifyConfirmBtn;
    
    private HomeLogic mLoginlogic;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_verify);
        
        mLoginlogic = (HomeLogic) getLogic(HomeLogic.class);
        
        initView();
    }
    
    private void initView()
    {
        
        final String username = getIntent().getStringExtra(USERNAME);
        
        mImgConfirmEdit = findViewById(R.id.img_confirm_edit);
        mConfirmImg = findViewById(R.id.img_confirm_iv);
        mImgVerifyConfirmBtn = findViewById(R.id.image_verify_confirm_btn);
        mImgConfirmTil = findViewById(R.id.img_confirm_til);
        
        getImageValide(username);
        
        mConfirmImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                
                getImageValide(username);
            }
        });
        
        mImgVerifyConfirmBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                
                if (TextUtils.isEmpty(mImgConfirmEdit.getText().toString()))
                {
                    //                    mImgConfirmTil.setError(getString(R.string.regist_image_confirm_code_error));
                    return;
                }
                
                Intent intent = getIntent().putExtra(USERNAME, username)
                        .putExtra(IMAGE_VALIDE,
                                mImgConfirmEdit.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                
            }
        });
    }
    
    private void getImageValide(String username)
    {
        mLoginlogic.requestImageValide(username,
                new OnResponseListener<Bitmap>()
                {
                    @Override
                    public void onStart(int what)
                    {
                        
                    }
                    
                    @Override
                    public void onSucceed(int what, Response<Bitmap> response)
                    {
                        Bitmap bitmap = response.get();
                        
                        if (null != bitmap)
                        {
                            mConfirmImg.setImageBitmap(bitmap);
                        }
                    }
                    
                    @Override
                    public void onFailed(int what, Response<Bitmap> response)
                    {
                        
                    }
                    
                    @Override
                    public void onFinish(int what)
                    {
                        
                    }
                });
    }
    
}

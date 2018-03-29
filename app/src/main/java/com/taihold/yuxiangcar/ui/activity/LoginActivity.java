package com.taihold.yuxiangcar.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.taihold.yuxiangcar.logic.HomeLogic;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONObject;

import static com.taihold.yuxiangcar.common.FusionAction.LoginExtra.IMAGE_VALIDE;

public class LoginActivity extends BaseActivity
{
    private static final String TAG = "LoginActivity";
    
    private TextView mSmsBtn;
    
    private EditText mPhoneEdit;
    
    private HomeLogic mHomeLogic;
    
    private String mImageValide;
    
    private TimeCount mCountDown;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        mHomeLogic = (HomeLogic) getLogic(HomeLogic.class);
        
        initView();
    }
    
    private void initView()
    {
        mSmsBtn = findViewById(R.id.sms_btn);
        mPhoneEdit = findViewById(R.id.username_edit);
        
        mCountDown = new TimeCount(60000, 1000);
        
        mSmsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String phone = mPhoneEdit.getText().toString();
                
                Intent intent = new Intent(FusionAction.IMAGE_VALIDE_ACTION);
                intent.putExtra(FusionAction.LoginExtra.USERNAME, phone);
                
                startActivityForResult(intent,
                        FusionAction.LoginExtra.IMAGE_CODE);
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case FusionAction.LoginExtra.IMAGE_CODE:
                    
                    mImageValide = data.getStringExtra(IMAGE_VALIDE);
                    
                    mCountDown.start();
                    
                    String phone = mPhoneEdit.getText().toString();
                    mHomeLogic.requestSmsCode(phone,
                            mImageValide,
                            new OnResponseListener<JSONObject>()
                            {
                                @Override
                                public void onStart(int what)
                                {
                                    
                                }
                                
                                @Override
                                public void onSucceed(int what,
                                        Response<JSONObject> response)
                                {
                                    JSONObject result = response.get();
                                    
                                    Log.d(TAG,
                                            "login result = "
                                                    + result.toString());
                                }
                                
                                @Override
                                public void onFailed(int what,
                                        Response<JSONObject> response)
                                {
                                    Log.d(TAG,
                                            "login result = "
                                                    + response.responseCode());
                                }
                                
                                @Override
                                public void onFinish(int what)
                                {
                                    
                                }
                            });
                    
                    break;
            }
        }
    }
    
    class TimeCount extends CountDownTimer
    {
        public TimeCount(long millisInFuture, long countDownInterval)
        {
            //参数依次为总时长,和计时的时间间隔
            super(millisInFuture, countDownInterval);
        }
        
        @Override
        public void onFinish()
        {
            //计时完毕时触发  
            mSmsBtn.setText(R.string.regist_send_sms_code);
            mSmsBtn.setEnabled(true);
        }
        
        @Override
        public void onTick(long millisUntilFinished)
        {
            //计时过程显示  
            mSmsBtn.setEnabled(false);
            mSmsBtn.setText(millisUntilFinished / 1000
                    + getResources().getString(R.string.regist_sms_count_down));
        }
    }
}

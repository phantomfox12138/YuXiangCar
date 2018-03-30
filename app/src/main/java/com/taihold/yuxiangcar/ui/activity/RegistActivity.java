package com.taihold.yuxiangcar.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.taihold.yuxiangcar.logic.HomeLogic;
import com.taihold.yuxiangcar.util.JsonUtil;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import static com.taihold.yuxiangcar.common.FusionAction.LoginExtra.IMAGE_VALIDE;

public class RegistActivity extends BaseActivity
{
    private static final String TAG = "RegistActivity";
    
    private EditText mPhoneEdit;
    
    private TextView mSmsBtn;
    
    private TimeCount mCountDown;
    
    private String mImageValide;
    
    private HomeLogic mHomeLogic;
    
    private EditText mSmsValideEdit;
    
    private EditText mPwdValideEdit;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        
        mHomeLogic = (HomeLogic) getLogic(HomeLogic.class);
        
        initView();
    }
    
    private void initView()
    {
        mSmsBtn = findViewById(R.id.sms_btn);
        mPhoneEdit = findViewById(R.id.username_edit);
        mSmsValideEdit = findViewById(R.id.valide_edit);
        mPwdValideEdit = findViewById(R.id.password_edit);
        
        mCountDown = new TimeCount(60000, 1000);
        
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        
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
        
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String phone = mPhoneEdit.getText().toString();
                String smsCode = mSmsValideEdit.getText().toString();
                final String pwd = mPwdValideEdit.getText().toString();
                
                mHomeLogic.valideSmsCode(phone,
                        smsCode,
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
                                
                                try
                                {
                                    if (JsonUtil.isSuccess(result))
                                    {
                                        if (result.has("data"))
                                        {
                                            String sid = result.getString("data");
                                            
                                            mHomeLogic.userRegist(sid,
                                                    phone,
                                                    pwd,
                                                    new OnResponseListener<JSONObject>()
                                                    {
                                                        @Override
                                                        public void onStart(
                                                                int what)
                                                        {
                                                            
                                                        }
                                                        
                                                        @Override
                                                        public void onSucceed(
                                                                int what,
                                                                Response<JSONObject> response)
                                                        {
                                                            try
                                                            {
                                                                if (JsonUtil.isSuccess(response.get()))
                                                                {
                                                                    Toast.makeText(RegistActivity.this,
                                                                            "注册成功",
                                                                            Toast.LENGTH_SHORT)
                                                                            .show();
                                                                    
                                                                    finish();
                                                                }
                                                            }
                                                            catch (JSONException e)
                                                            {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                        
                                                        @Override
                                                        public void onFailed(
                                                                int what,
                                                                Response<JSONObject> response)
                                                        {
                                                            
                                                        }
                                                        
                                                        @Override
                                                        public void onFinish(
                                                                int what)
                                                        {
                                                            
                                                        }
                                                    });
                                        }
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            
                            @Override
                            public void onFailed(int what,
                                    Response<JSONObject> response)
                            {
                                
                            }
                            
                            @Override
                            public void onFinish(int what)
                            {
                                
                            }
                        });
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

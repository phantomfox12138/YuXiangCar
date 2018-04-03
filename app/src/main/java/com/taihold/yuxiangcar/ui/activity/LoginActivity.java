package com.taihold.yuxiangcar.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.taihold.yuxiangcar.logic.HomeLogic;
import com.taihold.yuxiangcar.util.JsonUtil;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    private HomeLogic mHomelogic;

    private EditText mUserNameEdit;

    private EditText mPwdEdit;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mHomelogic = (HomeLogic) getLogic(HomeLogic.class);

        initView();
    }

    private void initView() {
        mUserNameEdit = findViewById(R.id.username_edit);
        mPwdEdit = findViewById(R.id.password_edit);
        sharedPreferences = getSharedPreferences("YuXData", MODE_PRIVATE);
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.to_regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FusionAction.REGIST_ACTION));
            }
        });

        findViewById(R.id.to_fpwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mUserNameEdit.getText().toString();
                String pwd = mPwdEdit.getText().toString();

                mHomelogic.userLogin(phone,
                        pwd,
                        new OnResponseListener<JSONObject>() {
                            @Override
                            public void onStart(int what) {

                            }

                            @Override
                            public void onSucceed(int what,
                                                  Response<JSONObject> response) {
                                JSONObject result = response.get();

                                try {
                                    if (JsonUtil.isSuccess(result)) {
                                        if (result.has("data")) {
                                            JSONObject data = result.getJSONObject("data");

                                            String name = data.getString("name");
                                            String sid = data.getString("sid");
                                            Log.v(TAG,"############返回当前的sid####"+sid);
                                            sharedPreferences.edit()
                                                    .putString("sid", sid)
                                                    .putString("loginName",
                                                            mUserNameEdit.getText().toString())
                                                    .commit();
                                            Log.d(TAG, "login result = "
                                                    + result.toString());

                                            Toast.makeText(LoginActivity.this,
                                                    result.getString("message"),
                                                    Toast.LENGTH_SHORT)
                                                    .show();

                                            finish();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailed(int what,
                                                 Response<JSONObject> response) {

                            }

                            @Override
                            public void onFinish(int what) {

                            }
                        });
            }
        });

    }

}

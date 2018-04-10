package com.taihold.yuxiangcar.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.taihold.yuxiangcar.logic.HttpHelper;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment
{
    private View mRootView;
    
    private View mUserView;
    private LinearLayout myCardLayout,toPayLayout,toServiceLayout,toSureLayout,mySecondCarLayout,myCollectLayout;
    private RelativeLayout myOrderLayout;
    private SharedPreferences sharedPreferences;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        mRootView = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        
        return mRootView;
    }
    
    private void initView()
    {
        sharedPreferences = getActivity().getSharedPreferences("YuXData", MODE_PRIVATE);
        mUserView = mRootView.findViewById(R.id.user_view);
        mySecondCarLayout=mRootView.findViewById(R.id.my_second_car);
        myCollectLayout=mRootView.findViewById(R.id.my_collect);
        myCardLayout=mRootView.findViewById(R.id.my_card_layout);
        myOrderLayout=mRootView.findViewById(R.id.my_order_layout);
        toPayLayout=mRootView.findViewById(R.id.to_pay_layout);
        toServiceLayout=mRootView.findViewById(R.id.to_service_layout);
        toSureLayout=mRootView.findViewById(R.id.to_sure_layout);
        mUserView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(FusionAction.PROFILE_ACTION));
            }
        });
        mRootView.findViewById(R.id.setting_btn)
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        startActivity(new Intent(FusionAction.SETTING_ACTION));
                    }
                });

        myCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(FusionAction.WEB_ACTION);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL + FusionAction.WEB_KEY.cardCoupons);
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "卡券");
                    startActivity(intent);
                }
            }
        });

        myOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(FusionAction.WEB_ACTION);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL + FusionAction.WEB_KEY.myorder+"?status=0");
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "我的订单");
                    startActivity(intent);
                }
            }
        });
        toPayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(FusionAction.WEB_ACTION);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL + FusionAction.WEB_KEY.myorder+"?status=1");
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "待付款");
                    startActivity(intent);
                }
            }
        });
        toServiceLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(FusionAction.WEB_ACTION);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL + FusionAction.WEB_KEY.myorder+"?status=2");
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "待服务");
                    startActivity(intent);
                }
            }
        });

        toSureLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(FusionAction.WEB_ACTION);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL + FusionAction.WEB_KEY.myorder+"?status=3");
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "待确认");
                    startActivity(intent);
                }
            }
        });

        mySecondCarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(FusionAction.WEB_ACTION);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL + FusionAction.WEB_KEY.SECONDHANDCAR);
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "二手车");
                    startActivity(intent);
                }
            }
        });
        myCollectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(FusionAction.WEB_ACTION);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL + FusionAction.WEB_KEY.MYCOLLECTION);
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "我的收藏");
                    startActivity(intent);
                }
            }
        });
    }
    
}

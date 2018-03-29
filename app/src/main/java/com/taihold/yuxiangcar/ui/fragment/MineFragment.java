package com.taihold.yuxiangcar.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment
{
    private View mRootView;
    
    private View mUserView;
    
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
        mUserView = mRootView.findViewById(R.id.user_view);
        
        mUserView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(FusionAction.LOGIN_ACTION));
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
    }
    
}

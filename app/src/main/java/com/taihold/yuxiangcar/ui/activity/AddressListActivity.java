package com.taihold.yuxiangcar.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.List;

public class AddressListActivity extends AppCompatActivity
{
    private SwipeMenuRecyclerView mAddrList;
    
    private AddressListAdapter mAddrAdapter;
    
    private TextView mAddAddrBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        
        initView();
    }
    
    private void initView()
    {
        mAddrList = findViewById(R.id.address_list);
        mAddAddrBtn = findViewById(R.id.add_address_btn);
        
        mAddrList.setLayoutManager(new LinearLayoutManager(this));
        mAddrAdapter = new AddressListAdapter();
        
        mAddrList.addItemDecoration(new DefaultItemDecoration(
                Color.parseColor("#f1f2f4"), 1, 20, 2));
        
        mAddrList.setAdapter(mAddrAdapter);
        
        mAddAddrBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(FusionAction.ADDRESS_EDIT));
            }
        });
        
    }
    
    class AddressListAdapter extends RecyclerView.Adapter<AddressHolder>
    {
        
        @Override
        public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            return new AddressHolder(
                    LayoutInflater.from(AddressListActivity.this)
                            .inflate(R.layout.address_item, null));
        }
        
        @Override
        public void onBindViewHolder(AddressHolder holder, int position)
        {
            
        }
        
        @Override
        public int getItemCount()
        {
            return 3;
        }
    }
    
    class AddressHolder extends RecyclerView.ViewHolder
    {
        
        public AddressHolder(View itemView)
        {
            super(itemView);
        }
    }
}

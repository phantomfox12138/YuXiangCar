package com.taihold.yuxiangcar.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.taihold.yuxiangcar.R;

public class EditAddrActivity extends AppCompatActivity
{
    private View mDirectionBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_addr);
        
        initView();
    }
    
    private void initView()
    {
        mDirectionBtn = findViewById(R.id.direction_btn);
        
        findViewById(R.id.add_address_back).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        
        mDirectionBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(
                        EditAddrActivity.this, new OnOptionsSelectListener()
                        {
                            @Override
                            public void onOptionsSelect(int options1,
                                    int options2, int options3, View v)
                            {
                                //返回的分别是三个级别的选中位置
                                //                                String tx = options1Items.get(options1)
                                //                                        .getPickerViewText()
                                //                                        + options2Items.get(options1)
                                //                                                .get(options2)
                                //                                        + options3Items.get(options1)
                                //                                                .get(options2)
                                //                                                .get(options3);
                                //                                
                                //                                Toast.makeText(JsonDataActivity.this,
                                //                                        tx,
                                //                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                
                .setTitleText("城市选择")
                        .setDividerColor(Color.BLACK)
                        .setTextColorCenter(Color.BLACK)
                        //设置选中项文字颜色
                        .setContentTextSize(20)
                        .build();
                
                /*pvOptions.setPicker(options1Items);//一级选择器
                pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
                //                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                pvOptions.show();
            }
        });
    }
}

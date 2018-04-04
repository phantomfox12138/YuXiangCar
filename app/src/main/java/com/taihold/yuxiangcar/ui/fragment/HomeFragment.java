package com.taihold.yuxiangcar.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.paradoxie.autoscrolltextview.VerticalTextview;
import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.taihold.yuxiangcar.logic.HttpHelper;
import com.taihold.yuxiangcar.model.StarModel;
import com.taihold.yuxiangcar.ui.activity.WebActivity;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
{
    private View mRootView;
    
    private SliderLayout mHomeSlider;
    
    private VerticalTextview mInfoText;
    
    private SwipeMenuRecyclerView mStarList;
    
    private List<StarModel> mStarDataList;
    private RelativeLayout automobile_layout,road_rescue_layout,
            cosmetology_layout,change_the_tires_layout,
            auto_parts_layout,used_car_layout,reserved_parking_layout,car_friend_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        initView();
        
        return mRootView;
    }
    
    private void initView()
    {
        mHomeSlider = mRootView.findViewById(R.id.slider);
        mInfoText = mRootView.findViewById(R.id.home_information);
        mStarList = mRootView.findViewById(R.id.star_list);

        automobile_layout=mRootView.findViewById(R.id.automobile_layout);
        road_rescue_layout=mRootView.findViewById(R.id.road_rescue_layout);
        cosmetology_layout=mRootView.findViewById(R.id.cosmetology_layout);
        change_the_tires_layout=mRootView.findViewById(R.id.change_the_tires_layout);
        used_car_layout=mRootView.findViewById(R.id.used_car_layout);
        auto_parts_layout=mRootView.findViewById(R.id.auto_parts_layout);
        reserved_parking_layout=mRootView.findViewById(R.id.reserved_parking_layout);
        car_friend_layout=mRootView.findViewById(R.id.car_friend_layout);
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.mipmap.hannibal);
        file_maps.put("Big Bang Theory", R.mipmap.bigbang);
        file_maps.put("House of Cards", R.mipmap.house);
        file_maps.put("Game of Thrones", R.mipmap.game_of_thrones);
        
        for (String name : file_maps.keySet())
        {
            DefaultSliderView textSliderView = new DefaultSliderView(
                    getActivity());
            // initialize a SliderLayout
            textSliderView.description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            
            mHomeSlider.addSlider(textSliderView);
        }
        mHomeSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mHomeSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mHomeSlider.setCustomAnimation(new DescriptionAnimation());
        mHomeSlider.setDuration(3000);
        
        //mInfoText.setTextList(titleList);//加入显示内容,集合类型
        mInfoText.setText(26, 5, Color.RED);//设置属性,具体跟踪源码
        mInfoText.setTextStillTime(3000);//设置停留时长间隔
        mInfoText.setAnimTime(300);//设置进入和退出的时间间隔
        //对单条文字的点击监听
        mInfoText.setOnItemClickListener(new VerticalTextview.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
            }
        });
        //汽修
        automobile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra(FusionAction.WEB_KEY.URL, HttpHelper.HTTP_WEBURL+FusionAction.WEB_KEY.VERTICLEMANTAINMENU);
                intent.putExtra(FusionAction.WEB_KEY.TITLE,"汽修");
                startActivity(intent);
            }
        });
        //道路救援
        road_rescue_layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra(FusionAction.WEB_KEY.URL,HttpHelper.HTTP_WEBURL+FusionAction.WEB_KEY.ROADRESCUEMENU);
                intent.putExtra(FusionAction.WEB_KEY.TITLE,"道路救援");
                startActivity(intent);

            }
        });
        //汽车美容
        cosmetology_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FusionAction.WEB_ACTION);
                intent.putExtra(FusionAction.WEB_KEY.URL,HttpHelper.HTTP_WEBURL+FusionAction.WEB_KEY.CARBEAUTY);
                intent.putExtra(FusionAction.WEB_KEY.TITLE,"汽车美容");
                startActivity(intent);
            }
        });
        //更换轮胎
        change_the_tires_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra(FusionAction.WEB_KEY.URL,HttpHelper.HTTP_WEBURL+FusionAction.WEB_KEY.REPLACEMENTTIRES);
                intent.putExtra(FusionAction.WEB_KEY.TITLE,"更换轮胎");
                startActivity(intent);
            }
        });
        //汽车配件
        auto_parts_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra(FusionAction.WEB_KEY.URL,HttpHelper.HTTP_WEBURL+FusionAction.WEB_KEY.AUTOPARTS);
                intent.putExtra(FusionAction.WEB_KEY.TITLE,"汽车配件");
                startActivity(intent);
            }
        });
        //二手车
        used_car_layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra(FusionAction.WEB_KEY.URL,HttpHelper.HTTP_WEBURL+FusionAction.WEB_KEY.SECONDHANDCAR);
                intent.putExtra(FusionAction.WEB_KEY.TITLE,"二手车");
                startActivity(intent);
            }
        });
        //预约停车
        reserved_parking_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //车友宝典
        car_friend_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        
    }
    
    class StarListAdapter extends RecyclerView.Adapter<StarHolder>
    {
        private Context context;
        
        @Override
        public StarHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            return new StarHolder(LayoutInflater.from(context)
                    .inflate(R.layout.star_item, null));
        }
        
        @Override
        public void onBindViewHolder(StarHolder holder, int position)
        {
            
        }
        
        @Override
        public int getItemCount()
        {
            return null != mStarDataList ? mStarDataList.size() : 0;
        }
    }
    
    class StarHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        
        TextView title;
        
        public StarHolder(View itemView)
        {
            super(itemView);
            
            image = itemView.findViewById(R.id.star_item_image);
            title = itemView.findViewById(R.id.star_item_title);
        }
    }
}

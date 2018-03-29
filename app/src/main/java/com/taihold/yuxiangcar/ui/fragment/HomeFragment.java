package com.taihold.yuxiangcar.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.paradoxie.autoscrolltextview.VerticalTextview;
import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.model.StarModel;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.HashMap;
import java.util.List;

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

package com.taihold.yuxiangcar.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.paradoxie.autoscrolltextview.VerticalTextview;
import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.taihold.yuxiangcar.logic.HttpHelper;
import com.taihold.yuxiangcar.model.StarModel;
import com.taihold.yuxiangcar.ui.activity.WebActivity;
import com.taihold.yuxiangcar.util.Utlity;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private View mRootView;

    private SliderLayout mHomeSlider;

    private VerticalTextview mInfoText;

    private SwipeMenuRecyclerView mStarList;

    private List<StarModel> mStarDataList;

    private RelativeLayout mAutomobile;

    private RelativeLayout mRoadRescue;

    private RelativeLayout mCosmetology;

    private RelativeLayout mChangeTires;

    private RelativeLayout mAutoParts;

    private RelativeLayout mUsedCar;

    private RelativeLayout mReservedPark;

    private RelativeLayout mCarFriend;

    //    声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private double mLatitiude;

    private double mLongitude;
    private SharedPreferences sharedPreferences;

    //    声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    mLatitiude = aMapLocation.getLatitude();//获取纬度
                    mLongitude = aMapLocation.getLongitude();//获取经度
                    //获取省市
                    Utlity.saveCityAndProvince(getActivity(),
                            aMapLocation.getCity(),
                            aMapLocation.getProvince(), mLatitiude, mLatitiude, aMapLocation.getAddress());

                    Log.d(TAG, "latitiude = " + mLatitiude + " longitude = "
                            + mLongitude);

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);

        initView();

        return mRootView;
    }

    private void initView() {
        //初始化定位
        mLocationClient = new AMapLocationClient(
                getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();

        mHomeSlider = mRootView.findViewById(R.id.slider);
        mInfoText = mRootView.findViewById(R.id.home_information);
        mStarList = mRootView.findViewById(R.id.star_list);

        mAutomobile = mRootView.findViewById(R.id.automobile_layout);
        mRoadRescue = mRootView.findViewById(R.id.road_rescue_layout);
        mCosmetology = mRootView.findViewById(R.id.cosmetology_layout);
        mChangeTires = mRootView.findViewById(R.id.change_the_tires_layout);
        mUsedCar = mRootView.findViewById(R.id.used_car_layout);
        mAutoParts = mRootView.findViewById(R.id.auto_parts_layout);
        mReservedPark = mRootView.findViewById(R.id.reserved_parking_layout);
        mCarFriend = mRootView.findViewById(R.id.car_friend_layout);
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.mipmap.hannibal);
        file_maps.put("Big Bang Theory", R.mipmap.bigbang);
        file_maps.put("House of Cards", R.mipmap.house);
        file_maps.put("Game of Thrones", R.mipmap.game_of_thrones);

        for (String name : file_maps.keySet()) {
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
        mInfoText.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
        sharedPreferences = getActivity().getSharedPreferences("YuXData", MODE_PRIVATE);
        //汽修
        mAutomobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL
                                    + FusionAction.WEB_KEY.VERTICLEMANTAINMENU);
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "汽修");
                    startActivity(intent);
                }
            }
        });
        //道路救援
        mRoadRescue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL
                                    + FusionAction.WEB_KEY.ROADRESCUEMENU);
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "道路救援");
                    startActivity(intent);
                }

            }
        });
        //汽车美容
        mCosmetology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(FusionAction.WEB_ACTION);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL + FusionAction.WEB_KEY.CARBEAUTY);
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "汽车美容");
                    startActivity(intent);
                }

            }
        });
        //更换轮胎
        mChangeTires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL
                                    + FusionAction.WEB_KEY.REPLACEMENTTIRES);
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "更换轮胎");
                    startActivity(intent);
                }

            }
        });
        //汽车配件
        mAutoParts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL + FusionAction.WEB_KEY.AUTOPARTS);
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "汽车配件");
                    startActivity(intent);
                }
            }
        });
        //二手车
        mUsedCar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("sid", null) == null) {
                    startActivity(new Intent(FusionAction.LOGIN_ACTION));
                } else {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra(FusionAction.WEB_KEY.URL,
                            HttpHelper.HTTP_WEBURL
                                    + FusionAction.WEB_KEY.SECONDHANDCAR);
                    intent.putExtra(FusionAction.WEB_KEY.TITLE, "二手车");
                    startActivity(intent);
                }
            }
        });
        //预约停车
        mReservedPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //车友宝典
        mCarFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    class StarListAdapter extends RecyclerView.Adapter<StarHolder> {
        private Context context;

        @Override
        public StarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StarHolder(LayoutInflater.from(context)
                    .inflate(R.layout.star_item, null));
        }

        @Override
        public void onBindViewHolder(StarHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return null != mStarDataList ? mStarDataList.size() : 0;
        }
    }

    class StarHolder extends RecyclerView.ViewHolder {
        ImageView image;

        TextView title;

        public StarHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.star_item_image);
            title = itemView.findViewById(R.id.star_item_title);
        }
    }
}

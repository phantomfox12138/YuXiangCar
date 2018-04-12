package com.taihold.yuxiangcar.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.taihold.yuxiangcar.R;
import com.taihold.yuxiangcar.common.FusionAction;
import com.taihold.yuxiangcar.ui.fragment.HomeFragment;
import com.taihold.yuxiangcar.ui.fragment.MineFragment;
import com.taihold.yuxiangcar.ui.fragment.StoreFragment;

public class HomeActivity extends AppCompatActivity {
    private static String TAG = "HomeActivity";
    private ViewPager mHomePager;
    private BottomNavigationBar mHomeNavigation;
    private List<Fragment> mFragments;
    private SharedPreferences sharedPreferences;
    private BottomNavigationBar.OnTabSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {
            sharedPreferences = getSharedPreferences("YuXData", MODE_PRIVATE);
            if (sharedPreferences.getString("sid", null) == null) {
                HomeActivity.this.startActivityForResult(new Intent(FusionAction.LOGIN_ACTION), position);
            } else {
                mHomePager.setCurrentItem(position);
            }
        }

        @Override
        public void onTabUnselected(int position) {
        }

        @Override
        public void onTabReselected(int position) {
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mHomePager.setCurrentItem(requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences("YuXData", MODE_PRIVATE);
        mHomePager = findViewById(R.id.home_pager);
        mHomeNavigation = findViewById(R.id.navigation);
        mHomeNavigation.setActiveColor(R.color.colorPrimary);
        mHomeNavigation.setBarBackgroundColor(android.R.color.white);
        mHomeNavigation.setFirstSelectedPosition(0);

        mHomeNavigation.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        mHomeNavigation.addItem(new BottomNavigationItem(R.mipmap.home_56,
                getString(R.string.title_home)))
                .addItem(new BottomNavigationItem(R.mipmap.store,
                        getString(R.string.title_store)))
                .addItem(new BottomNavigationItem(R.mipmap.my,
                        getString(R.string.title_profile)))
                .initialise();


        mHomeNavigation.setTabSelectedListener(mOnNavigationItemSelectedListener);

        mFragments = new ArrayList<>();

        mFragments.add(new HomeFragment());
        mFragments.add(new StoreFragment());
        mFragments.add(new MineFragment());

        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(
                getSupportFragmentManager());

        mHomePager.setAdapter(adapter);
        mHomePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mHomeNavigation.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

        public HomeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

}

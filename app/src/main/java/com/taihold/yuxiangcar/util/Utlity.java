package com.taihold.yuxiangcar.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by niufan on 18/4/8.
 */

public class Utlity
{
    public static void saveCityAndProvince(Context context, String city,
            String province,double mLatitiude,double mLongitude,String address)
    {
        SharedPreferences mSp = context.getSharedPreferences("location_sp",
                Context.MODE_PRIVATE);
        mSp.edit()
                .putString("city", city)
                .putString("province", province)
                .putString("latitiude",mLatitiude+"")
                .putString("longitude",mLongitude+"")
                .putString("detailAddress",address+"")
                .commit();
    }
}

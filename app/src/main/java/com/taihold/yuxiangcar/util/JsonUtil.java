package com.taihold.yuxiangcar.util;

import android.text.InputFilter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by niufan on 18/3/30.
 */

public class JsonUtil
{
    public static boolean isSuccess(JSONObject jsonObject) throws JSONException
    {
        if (jsonObject.has("status"))
        {
            return jsonObject.getString("status").equals("200");
        }
        
        return false;
    }
}

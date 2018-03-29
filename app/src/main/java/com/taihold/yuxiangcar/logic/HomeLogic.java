package com.taihold.yuxiangcar.logic;

import android.content.Context;
import android.graphics.Bitmap;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;

import org.json.JSONObject;

/**
 * Created by niufan on 18/3/29.
 */

public class HomeLogic extends BaseLogic
{
    private Context mContext;
    
    public HomeLogic(Context context)
    {
        super();
        this.mContext = context;
    }
    
    public void requestImageValide(String phone,
            OnResponseListener<Bitmap> listener)
    {
        Request<Bitmap> requestBitmap = NoHttp.createImageRequest(HttpHelper.HTTP_HEADER
                + HttpHelper.REQUEST_VALIDE_IMAGE);
        
        requestBitmap.add("loginName", phone);
        requestBitmap.add("width", 700);
        requestBitmap.add("height", 260);
        requestBitmap.add("font", 200);
        
        mQueue.add(1, requestBitmap, listener);
    }
    
    public void requestSmsCode(String phone, String imageCode,
            OnResponseListener<JSONObject> listener)
    {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(HttpHelper.HTTP_HEADER
                + HttpHelper.REQUEST_SMS_CODE_TEST,
                RequestMethod.POST);
        
        request.add("phone", phone);
        request.add("imageCode", imageCode);
        
        mQueue.add(0, request, listener);
    }
}

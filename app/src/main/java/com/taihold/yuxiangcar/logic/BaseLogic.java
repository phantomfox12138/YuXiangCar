package com.taihold.yuxiangcar.logic;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;

/**
 * Created by niufan on 18/3/29.
 */

public abstract class BaseLogic
{
    protected RequestQueue mQueue;
    
    public BaseLogic()
    {
        mQueue = NoHttp.newRequestQueue();
    }
    
}

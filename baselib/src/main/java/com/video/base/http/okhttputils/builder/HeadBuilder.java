package com.video.base.http.okhttputils.builder;

import com.video.base.http.okhttputils.OkHttpUtils;
import com.video.base.http.okhttputils.request.OtherRequest;
import com.video.base.http.okhttputils.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}

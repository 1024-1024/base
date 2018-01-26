package com.video.base.http.okhttp;

import java.io.IOException;

import okhttp3.Call;

/**
 * @author zhangweilong
 * @date 2018/1/26
 */

public interface HttpResultListener<T extends BaseModel> {
    void success(T t);
    void fail(Call call, IOException e);
}

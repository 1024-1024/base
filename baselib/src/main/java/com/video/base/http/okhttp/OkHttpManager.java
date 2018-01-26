package com.video.base.http.okhttp;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author zhangweilong
 * @date 2018/1/26
 */

public class OkHttpManager {

    private static OkHttpClient mOkHttpClient;
    private static OkHttpManager manager;

    private Handler mainHandler;

    private OkHttpManager() {
        mOkHttpClient = initOkHttpClient();
        mainHandler = new Handler(Looper.myLooper());
    }

    private static OkHttpClient initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(null);
        return builder.build();
    }

    public static OkHttpManager getOkHttpClient() {
        if (manager == null) {
            synchronized (OkHttpManager.class) {
                if (manager == null) {
                    manager = new OkHttpManager();
                }
            }
        }
        return manager;
    }

    /**
     * simple异步请求
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends BaseModel> void sendRequest(String url, Class<T> clazz, HttpResultListener<T> callback) {
        sendRequest(url, HttpMethod.GET, null, clazz, callback);
    }

    /**
     * 异步请求，传参数
     *
     * @param url
     * @param method
     * @param requestBody
     * @param clazz
     * @param callback
     * @param <T>
     */
    public <T extends BaseModel> void sendRequest(String url, String method, RequestBody requestBody, final Class<T> clazz, final HttpResultListener<T> callback) {
        Request request = getRequest(url, method, requestBody);
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.fail(call, e);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String responseStr = response.body().toString();
                        T object = null;
                        try {
                            object = clazz.newInstance();
                            object.parseData(responseStr);
                            final T finalObject = object;
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (callback != null) {
                                        callback.success(finalObject);
                                    }
                                }
                            });

                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        object.parseData(responseStr);

                    }
                }
            }
        });
    }

    private Request getRequest(String url, String method, RequestBody requestBody) {
        Request request;
        if (HttpMethod.GET.equals(method)) {
            request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
        } else if (HttpMethod.POST.equals(method)) {
            request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .method(method, requestBody)
                    .build();
        }
        return request;
    }


}

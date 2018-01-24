package com.video.base.http.retrofit_rx.Api;

/**
 * 回调信息统一封装类
 * Created by WZG on 2016/7/16.
 */
public class BaseResultEntity {
    //  判断标示
    private int ret;
    //    提示信息
    private String msg;
    //显示数据（用户需要关心的数据）
    private String data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }
}

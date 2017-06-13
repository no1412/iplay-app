package com.ll.iplay;

import android.app.Application;
import android.content.Context;

import com.ll.iplay.common.Constants;
import com.ll.iplay.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ll on 2017/5/5.
 * 自定义Application
 */

public class MyApplication  extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        HttpUtil.sendOkHttpRequest(Constants.REQUEST_PREFIX, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Constants.REQUEST_PREFIX = "http://172.20.84.101:8080/iplay/";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public static Context getContext() {
        return context;
    }
}

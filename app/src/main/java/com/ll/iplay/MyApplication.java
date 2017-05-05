package com.ll.iplay;

import android.app.Application;
import android.content.Context;

/**
 * Created by ll on 2017/5/5.
 * 自定义Application
 */

public class MyApplication  extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}

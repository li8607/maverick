package com.maverick;

import android.app.Application;
import android.content.Context;

import com.android.wonderokhttp.http.HttpUtil;

/**
 * Created by ll on 2017/5/22.
 */
public class MainApp extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}

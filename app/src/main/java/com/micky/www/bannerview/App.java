package com.micky.www.bannerview;

import android.app.Application;

import com.micky.www.bannerviewmodule.Banner;

/**
 * Created by Administrator on 2018/12/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Banner.init(this);
    }
}

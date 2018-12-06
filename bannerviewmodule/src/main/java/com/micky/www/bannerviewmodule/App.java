package com.micky.www.bannerviewmodule;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Micky on 2018/12/6.
 * Banner必须初始化的东西
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 自带的图片处理框架
        Fresco.initialize(this);
    }
}

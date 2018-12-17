package com.micky.www.bannerviewmodule;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Micky on 2018/12/6.
 * Banner必须初始化的东西
 */

public class Banner {

    public static void init(Context context)
    {
        Fresco.initialize(context);
    }

}

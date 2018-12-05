package com.micky.www.bannerviewmodule;

import android.view.View;

/**
 * Created by Micky on 2018/12/5.
 * 适配器回掉接口
 */

public interface BannerAdapterCallBack<T> {
    void onCallBack(View view, T t);
}

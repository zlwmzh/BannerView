package com.micky.www.bannerviewmodule;

/**
 * Created by Micky on 2018/12/5.
 * 参数配置
 */

public class BannerConfig {

    // 默认的广告Item项
    public static final int DEFAULT_ITEM_BANNER = -0x01;
    // 默认布局类型
    public static final int DEFAULT_ITEM_BANNER_TYPE = 0x00;

    // 每张Banner停留的默认时间
    public static final int DELAY_TIME = 2 * 1000;
    public static final float SPEED = 150f;
    public static final boolean AUTO_PLAY_OPEN = true;
    public static final boolean AUTO_PLAY_CLOSE = false;

    // 指示器的位置
    public static final int LEFT = 0x05;
    public static final int CENTER = 0x06;
    public static final int RIGHT = 0x07;

    // 用户是否触摸
    public static final boolean USER_TOUCHING = true;
    public static final boolean USER_NOT_TOUCH = false;

}

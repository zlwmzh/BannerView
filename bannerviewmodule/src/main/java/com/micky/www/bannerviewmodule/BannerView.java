package com.micky.www.bannerviewmodule;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

/**
 * Created by Micky on 2018/12/5.
 * BannerView
 */

public class BannerView extends FrameLayout{

    protected static final String TAG = "BannerView";

    // 用于实现viewpager功能
    protected RecyclerView mRecyclerView;
    // 指示器填充区域
    protected LinearLayout mIndicatorContainer;
    // 指示器默认布局
    protected RadioGroup mDefaultIndicatorView;
    // 上下文环境
    private Context context;

    public BannerView(@NonNull Context context) {
        this(context,null);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context,attrs);
    }

    /**
     * 加载布局、属性、查找控件等
     * @param context
     * @param attrs
     */
    protected void initView(Context context, AttributeSet attrs)
    {
        // 获取属性
        // TODO
        // 添加主布局、查找控件
        LayoutInflater.from(context).inflate(R.layout.view_bannerview,this,false);
        mRecyclerView = findViewById(R.id.banner_recyclerview);
        mIndicatorContainer = findViewById(R.id.indicator_container);

       // 解析默认的指示器布局
        mDefaultIndicatorView = (RadioGroup) LayoutInflater.from(context).inflate(R.layout.view_default_indicator,mIndicatorContainer,false);
    }
}

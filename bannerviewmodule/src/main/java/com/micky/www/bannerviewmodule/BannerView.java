package com.micky.www.bannerviewmodule;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Micky on 2018/12/5.
 * BannerView
 */

public class BannerView<T> extends FrameLayout {

    protected static final String TAG = "BannerView";

    // 用于实现viewpager功能
    protected RecyclerView mRecyclerView;
    // 指示器填充区域
    protected LinearLayout mIndicatorContainer;
    // 指示器默认布局
    protected RadioGroup mDefaultIndicatorView;
    // 上下文环境
    protected Context context;
    // 适配器
    protected BannerAdapter<T> mBannerAdapter;
    // 数据源
    protected List<T> mList;
    // 当前布局的类型
    protected int mCurrentItemLayout = BannerConfig.DEFAULT_ITEM_BANNER_TYPE;

    // 回掉接口
    protected BannerAdapterCallBack<T> mBannerCallBack;

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
        LayoutInflater.from(context).inflate(R.layout.view_bannerview,this);
        mRecyclerView = findViewById(R.id.banner_recyclerview);
        mIndicatorContainer = findViewById(R.id.indicator_container);

       // 解析默认的指示器布局
        mDefaultIndicatorView = (RadioGroup) LayoutInflater.from(context).inflate(R.layout.view_default_indicator,mIndicatorContainer,false);

        initList();
    }

    /**
     *  初始化列表
     */
    protected void initList()
    {
        mList = new ArrayList<>();
        mBannerAdapter = new BannerAdapter<T>(context,mList);
        // 设置横向滚动
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mBannerAdapter);
        // 设置一次翻一项
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     *
     * @param list
     */
    public BannerView setBannerLoader(List<T> list)
    {

       return this;
    }

    /**
     * 设置数据源
     * @param list
     * @return
     */
    public BannerView setListData(List<T> list)
    {
        mList.clear();
        mList.addAll(list);
        return this;
    }

    /**
     * 追加数据源
     * @param list
     * @return
     */
    public BannerView addListData(List<T> list)
    {
        mList.addAll(list);
        return this;
    }

    public BannerView<T> setCallBack(BannerAdapterCallBack<T> callBack)
    {
        this.mBannerCallBack = callBack;
        mBannerAdapter.setCallBack(mBannerCallBack);
        return this;
    }

    /**
     * 开启轮播
     * @return
     */
    public BannerView start()
    {
        mBannerAdapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 创建指示器
     */
    protected void createIndicator()
    {
        // 移除所有的指示器
        mDefaultIndicatorView.removeAllViews();
        for (T t : mList)
        {

        }
        return;
    }
}

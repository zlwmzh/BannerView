package com.micky.www.bannerviewmodule;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
    // 当前广告的最大数量
    protected int maxBannerCount;
    // 单个指示器宽度
    protected int mIndicatorWidth = 64;
    // 单个指示器高度
    protected int mIndicatorHeight = 64;
    // 每个指示器之间的间距
    protected int mIndicatorMargin = 10;
    // 指示器的位置 居中、左右
    private int mIndicatorGravity = BannerConfig.CENTER;
    // 指示器Drawable的资源id
    protected int mIndicatorDrawableId = R.drawable.selector_indicator;
    // 回掉接口
    protected BannerAdapterCallBack<T> mBannerCallBack;
    // 每一张广告停留时间
    protected  int mDelayTime = BannerConfig.DELAY_TIME;
    // 设置滑动速度
    protected float mSmoothSpeed = BannerConfig.SPEED;
    // 当前广告的位置
    protected int mCurrentBannerPosition = 0;
    // 是否自动播放Banner
    protected boolean isAutoPlay = BannerConfig.AUTO_PLAY_OPEN;
    // 用户是否触摸BannerView
    protected boolean isTouchView = BannerConfig.USER_NOT_TOUCH;

    protected WeakHandler handler = new WeakHandler();


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
        getTypeArray(context,attrs);
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
     * 获取自定义view 属性
     * @param context
     * @param attrs
     */
    protected void getTypeArray(Context context, AttributeSet attrs)
    {
        if (attrs == null)
        {
            return;
        }
        // 获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorheight,mIndicatorHeight);
        mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorwidth,mIndicatorWidth);
        mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatormargin,mIndicatorMargin);
        mIndicatorGravity = typedArray.getInt(R.styleable.BannerView_indicatorgravity,BannerConfig.CENTER);
        mIndicatorDrawableId = typedArray.getResourceId(R.styleable.BannerView_indicatordrawable,mIndicatorDrawableId);
        mDelayTime = typedArray.getInt(R.styleable.BannerView_delaytime,mDelayTime);
        mSmoothSpeed = typedArray.getFloat(R.styleable.BannerView_speed,BannerConfig.SPEED);
        isAutoPlay = typedArray.getBoolean(R.styleable.BannerView_isautoplay,isAutoPlay);
        typedArray.recycle();
    }

    /**
     *  初始化列表
     */
    protected void initList()
    {
        mList = new ArrayList<>();
        mBannerAdapter = new BannerAdapter<T>(context,mList);
        // 设置横向滚动
        LinearLayoutManager layoutManager = new LinearLayoutManager(context)
        {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
               // super.smoothScrollToPosition(recyclerView, state, position);
                // 此处用户设置滑动速度
                LinearSmoothScroller smoothScroller =
                        new LinearSmoothScroller(recyclerView.getContext()) {
                            // 返回：滑过1px时经历的时间(ms)。
                            @Override
                            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                                return mSmoothSpeed / displayMetrics.densityDpi;
                            }
                        };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mBannerAdapter);
        // 设置一次翻一项
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper()
        {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                // 当前滑动的项
                int target = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
                // 联动指示器
                moveIndicator(target);
                return target;
            }
        };
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);

    }

    /**
     * 切换指示器
     * @param position 切换指示器位置
     */
    protected void moveIndicator(int position)
    {
        // 设置当前位置
        mCurrentBannerPosition = position;
        if (position < 0)
        {
            // 选中位置不正确的话，不切换指示器
            return;
        }
        // 获取对应位置的指示器
        RadioButton rb = (RadioButton) mDefaultIndicatorView.getChildAt(position%maxBannerCount);
        // 设置为选中状态
        rb.setChecked(true);
    }

    /**
     * 设置单个指示器的宽高
     * @param width
     * @param height
     * @return
     */
    public BannerView setIndicatorWH(int width, int height)
    {
        this.mIndicatorWidth = width;
        this.mIndicatorHeight = height;
        return this;
    }

    /**
     *  设置单个指示器之间的间距
     * @param margin
     * @return
     */
    public BannerView setIndicatorMargin(int margin)
    {
        this.mIndicatorMargin = margin;
        return this;
    }

    /**
     *  设置指示器的样式；你需要在出入一个drawable文件。定义好状态
     * @param indicatorDrawableId
     * @return
     */
    public BannerView setIndicatorDrawableId(int indicatorDrawableId)
    {
        this.mIndicatorDrawableId = indicatorDrawableId;
        return this;
    }

    /**
     *  设置每张广告停留时间
     * @param delayTime
     * @return
     */
    public BannerView setDelayTime(int delayTime)
    {
        this.mDelayTime = delayTime;
        return this;
    }

    /**
     * 设置滑动速度
     * @param speed
     * @return
     */
    public BannerView setSpeend(float speed)
    {
        this.mSmoothSpeed = speed;
        return this;
    }

    /**
     *  设置是否开启自动播放
     * @param isAutoPlay
     * @return
     */
    public BannerView setIsAutoPlay(boolean isAutoPlay)
    {
        this.isAutoPlay = isAutoPlay;
        return this;
    }

    /**
     *  设置自定义Banner布局
     * @param bannerLayout
     * @return
     */
    public BannerView setBannerLayout(int bannerLayout)
    {
        mBannerAdapter.setLayoutId(bannerLayout);
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
        // 计算广告的最大数量
        maxBannerCount = mList.size();
        // 创建指示器
        createIndicator();
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
        // 判断是否自动播放
        if (isAutoPlay)
        {
            startAutoPlay();
        }
        return this;
    }

    /**
     * 开始自动轮播
     * @return
     */
    public BannerView startAutoPlay()
    {
        handler.removeCallbacks(task);
        handler.postDelayed(task,mDelayTime);
        return this;
    }

    /**
     * 暂停轮播图
     * @return
     */
    public BannerView pauseAutoPlay()
    {
        isAutoPlay = BannerConfig.AUTO_PLAY_CLOSE;
        return this;
    }

    /**
     * 重新开始播放
     * @return
     */
    public BannerView continueAutoPlay()
    {
        isAutoPlay = BannerConfig.AUTO_PLAY_OPEN;
        return this;
    }

    /**
     * 停止自动播放
     * @return
     */
    public BannerView stopAutoPlay()
    {
        handler.removeCallbacks(task);
        return this;
    }

    /**
     *  设置指示器的位置
     * @param gravity
     * @return
     */
    public BannerView setIndicatorGravity(int gravity)
    {
        switch (gravity)
        {
            case BannerConfig.LEFT:
                this.mIndicatorGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case BannerConfig.CENTER:
                this.mIndicatorGravity = Gravity.CENTER;
                break;
            case BannerConfig.RIGHT:
                this.mIndicatorGravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        return this;
    }


    /**
     * 创建指示器
     */
    protected void createIndicator()
    {
        // 设置指示器位置
        setIndicatorGravity(mIndicatorGravity);
        mIndicatorContainer.setGravity(mIndicatorGravity);
        // 移除底部布局的所有view
        mIndicatorContainer.removeAllViews();
        // 添加默认的指示器布局
        mIndicatorContainer.addView(mDefaultIndicatorView);
        // 移除所有的指示器
        mDefaultIndicatorView.removeAllViews();

        // 指示器的大小
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(mIndicatorWidth,mIndicatorHeight);
        layoutParams.setMargins(mIndicatorMargin,0,0,0);
        // for循环数据源，添加指示器
        for (int i = 0 ; i < mList.size() ; i++)
        {
            // 实例化一个指示器
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setBackground(getResources().getDrawable(mIndicatorDrawableId));
            radioButton.setLayoutParams(layoutParams);
            radioButton.setButtonDrawable(null);
            mDefaultIndicatorView.addView(radioButton);
        }
        // 设置第一项被选中
        moveIndicator(0);
        return;
    }

    protected final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay)
            {
                if (maxBannerCount < 1)
                {
                    // 当前没有广告
                    return;
                }
                // 处理自动轮询
                mCurrentBannerPosition ++;
                Log.d(TAG,"当前位置："+mCurrentBannerPosition+"总大小："+maxBannerCount);
                mRecyclerView.smoothScrollToPosition(mCurrentBannerPosition);
                moveIndicator(mCurrentBannerPosition);
            }
            // 继续轮询
            handler.postDelayed(this,mDelayTime);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoPlay)
        {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                // 设置用户触摸离开
                isTouchView = BannerConfig.USER_NOT_TOUCH;
                startAutoPlay();
            } else if (action == MotionEvent.ACTION_DOWN) {
                // 设置用户触摸
                isTouchView = BannerConfig.USER_TOUCHING;
                stopAutoPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void releaseBanner() {
        handler.removeCallbacksAndMessages(null);
    }
}

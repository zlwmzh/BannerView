package com.micky.www.bannerviewmodule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Micky on 2018/12/5.
 * Banner适配器
 */

public class BannerAdapter<T> extends RecyclerView.Adapter{
    // 数据源
    protected List<T> mList;
    // 上下文环境
    protected Context context;
    // 布局id
    private int mLayoutId;
    // 回掉接口
    private BannerAdapterCallBack<T> mCallBack;

    public BannerAdapter(Context context, List<T> list)
    {
       this(context,list,BannerConfig.DEFAULT_ITEM_BANNER);
    }

    public BannerAdapter(Context context, List<T> list, int itemLayoutId)
    {
        this.context = context;
        this.mList = list;
        this.mLayoutId = itemLayoutId;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutId == BannerConfig.DEFAULT_ITEM_BANNER)
        {
            // 加载默认的
            return new DefaultViewHolder(LayoutInflater.from(context).inflate(R.layout.item_default_banner,parent,false));
        }
        return new RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(mLayoutId,parent,false)){};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       T t = mList.get(position%mList.size());
       if (mCallBack == null)
       {
           Toast.makeText(context,"请接入回掉监听",Toast.LENGTH_LONG).show();
           return;
       }
       mCallBack.onCallBack(holder.itemView,t);
    }

    @Override
    public int getItemCount() {
        return mList == null || mList.size() == 0 ? 0 : Integer.MAX_VALUE;
    }

    /**
     * 设置回掉监听
     * @param callBack
     */
    public void setCallBack(BannerAdapterCallBack<T> callBack)
    {
        this.mCallBack = callBack;
    }

    /**
     *  设置自定义布局id
     * @param layoutId
     */
    public void setLayoutId(int layoutId)
    {
        this.mLayoutId = layoutId;
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        public DefaultViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView;
        }
    }
}

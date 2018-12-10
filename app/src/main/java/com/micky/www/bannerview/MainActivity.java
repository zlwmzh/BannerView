package com.micky.www.bannerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.micky.www.bannerviewmodule.BannerAdapterCallBack;
import com.micky.www.bannerviewmodule.BannerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BannerView<String> mBannerView;
    private BannerView<String> mBannerView2;
    private BannerView<BannerBean1> mBannerView3;
    private List<String> mList;
    private List<BannerBean1> mListForB3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBannerView = findViewById(R.id.bannerview);
        mBannerView2 = findViewById(R.id.bannerview_2);
        mBannerView3 = findViewById(R.id.bannerview_3);
        mBannerView.setCallBack(new BannerAdapterCallBack<String>() {
            @Override
            public void onCallBack(View view, String s) {
                SimpleDraweeView  simpleDraweeView = (SimpleDraweeView) view;
                simpleDraweeView.setImageURI(s);
            }
        });


        mBannerView2.setCallBack(new BannerAdapterCallBack<String>() {
            @Override
            public void onCallBack(View view, String s) {
                SimpleDraweeView  simpleDraweeView = (SimpleDraweeView) view;
                simpleDraweeView.setImageURI(s);
            }
        });


        // 自定义广告布局
        mBannerView3.setCallBack(new BannerAdapterCallBack<BannerBean1>() {
            @Override
            public void onCallBack(View view, BannerBean1 bannerBean1) {
                SimpleDraweeView simpleDraweeView = view.findViewById(R.id.item_simleDraweeview);
                TextView textView = view.findViewById(R.id.item_title);
                simpleDraweeView.setImageURI(bannerBean1.getCover());
                textView.setText(bannerBean1.getTitle());
            }
        });
        mBannerView3.setBannerLayout(R.layout.item_banner_1);
        addTest();
    }

    private void addTest()
    {
        mList = new ArrayList<>();
        mList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2847793733,3567113485&fm=26&gp=0.jpg");
        mList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2387104529,2501926905&fm=26&gp=0.jpg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544097283099&di=f5e533e3c28f70bc99318e9133d3975c&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01faab595b3d69a8012193a355bbde.jpg%402o.jpg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544097325388&di=d8e4237987082a2a1ea7f7d0a77265fa&imgtype=0&src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69362f54316f48692e5865686c5858614f715473335f3035313331372e6a7067.jpg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544692107&di=2796e41279fbd54d7a2a43a70a2c7f9b&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01724a58772f59a8012060c8ada29f.png%401280w_1l_2o_100sh.png");

        mBannerView.setListData(mList);
        mBannerView.start();

        mBannerView2.setListData(mList);
        mBannerView2.start();


        mListForB3 = new ArrayList<>();
        mListForB3.add(new BannerBean1("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2847793733,3567113485&fm=26&gp=0.jpg","我若不勇敢"));
        mListForB3.add(new BannerBean1("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2387104529,2501926905&fm=26&gp=0.jpg","2"));
        mListForB3.add(new BannerBean1("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544097283099&di=f5e533e3c28f70bc99318e9133d3975c&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01faab595b3d69a8012193a355bbde.jpg%402o.jpg","3"));
        mListForB3.add(new BannerBean1("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544097325388&di=d8e4237987082a2a1ea7f7d0a77265fa&imgtype=0&src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69362f54316f48692e5865686c5858614f715473335f3035313331372e6a7067.jpg","4"));
        mListForB3.add(new BannerBean1("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544692107&di=2796e41279fbd54d7a2a43a70a2c7f9b&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01724a58772f59a8012060c8ada29f.png%401280w_1l_2o_100sh.png","4"));
        mBannerView3.setListData(mListForB3);
        mBannerView3.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBannerView.pauseAutoPlay();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mBannerView.continueAutoPlay();
    }
}

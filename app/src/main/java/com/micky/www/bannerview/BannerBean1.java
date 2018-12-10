package com.micky.www.bannerview;

/**
 * Created by Micky on 2018/12/10.
 */

public class BannerBean1 {
    private String cover;
    private String title;

    public BannerBean1(String cover, String title) {
        this.cover = cover;
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

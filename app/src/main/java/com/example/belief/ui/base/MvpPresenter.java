package com.example.belief.ui.base;


import android.widget.ImageView;

/*
P层通用接口
* */
public interface MvpPresenter <V extends MvpView> {

    //初始化state
    void onAttach(V mvpView);

    //销毁View层引用
    void onDetach();

    //下载图片
    void downPic(String url, ImageView imageView);

    void downPicToCircle(String url, ImageView imageView);

    //上传图片
    //下载图片
    void uploadPic(ImageView imageView, String filename);
}

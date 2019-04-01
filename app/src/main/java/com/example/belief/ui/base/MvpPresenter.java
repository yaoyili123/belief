package com.example.belief.ui.base;

/*
P层通用接口
* */
public interface MvpPresenter <V extends MvpView> {

    //初始化state
    void onAttach(V mvpView);

    //销毁View层引用
    void onDetach();
}

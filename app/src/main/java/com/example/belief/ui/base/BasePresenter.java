package com.example.belief.ui.base;

import com.example.belief.data.DataManager;

import javax.inject.Inject;

/*
* P层基类，Presenter需要持有的state: M层接口、V层组件引用
* work：修改全局状态、给View的UI层传数据，下达View修改UI的指令（更改UI、显示后台数据错误等）
* */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mMvpView;

    private DataManager mDataManager;

    @Inject
    public BasePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mMvpView = null;
    }
}

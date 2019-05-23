package com.example.belief.ui.base;

import com.example.belief.data.DataManager;
import com.example.belief.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/*
* P层基类，Presenter需要持有的state: M层接口、V层组件引用
* work：修改全局状态、给View的UI层传数据，下达View修改UI的指令（更改UI、显示后台数据错误等）
* */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mMvpView;

    private final DataManager mDataManager;

    private final SchedulerProvider mSchedulerProvider;

    //统一处理订阅，一个Observable管理器
    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public BasePresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = compositeDisposable;
    }


    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

}

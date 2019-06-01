package com.example.belief.ui.base;

import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.belief.data.DataManager;
import com.example.belief.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;

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

    @Override
    public void downPic(String url, ImageView imageView) {
        if (url == null || url.isEmpty())
            return;
        //获取用户头像
        getCompositeDisposable().add(getDataManager().downPic(url)
                .map((ResponseBody responseBody) -> BitmapFactory.decodeStream(responseBody.byteStream()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((bitmap) -> {
                    imageView.setImageBitmap(bitmap);
                }, (e) -> {
                    getMvpView().onError("图片加载失败");
                    getMvpView().handleApiError(e);
                }));
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

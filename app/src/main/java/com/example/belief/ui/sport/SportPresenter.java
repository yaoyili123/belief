package com.example.belief.ui.sport;

import android.graphics.BitmapFactory;

import com.example.belief.data.DataManager;
import com.example.belief.data.network.model.UserAuth;
import com.example.belief.ui.base.BasePresenter;
import com.example.belief.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;

public class SportPresenter<V extends SportMvpView> extends BasePresenter<V>
        implements SportMvpPresenter<V> {

    @Inject
    public SportPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    //准备主页数据
    @Override
    public void onMainViewPrepared(UserAuth userAuth, String imageName) {
        if (!imageName.isEmpty()) {
            //获取用户头像
            getCompositeDisposable().add(getDataManager().downPic(imageName)
                    .map((ResponseBody responseBody) -> BitmapFactory.decodeStream(responseBody.byteStream()))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe((bitmap) -> {
                        ((SportMainFragment)getMvpView()).userHead.setImageBitmap(bitmap);
                    }, (e) -> {
                        getMvpView().onError("图片加载失败");
                        getMvpView().handleApiError(e);
                    }));
        }
        //获取用户总卡路里
        getCompositeDisposable().add(getDataManager().getTotalKcal(userAuth.getUid())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((res) -> {
                    //成功逻辑
                    ((SportMainFragment)getMvpView()).sportTimeSum.setText(res + " 卡路里");
                    }, (e) -> {
                        getMvpView().handleApiError(e);
                    }
                ));
    }

    @Override
    public void getJoinedClasses(int uid) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getJoinedClasses(uid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((res) -> {
                        //成功逻辑
                        getMvpView().hideLoading();
                        ((ManageUserClassActivity)getMvpView()).setData(res);
                    }, (e) -> {
                        getMvpView().hideLoading();
                        getMvpView().handleApiError(e);
                    }
                ));
    }
}

package com.example.belief.ui.comm;

import com.example.belief.data.DataManager;
import com.example.belief.data.network.model.ApiFault;
import com.example.belief.data.network.model.RequestShare;
import com.example.belief.ui.base.BasePresenter;
import com.example.belief.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CommPresenter<V extends CommMvpView> extends BasePresenter<V>
        implements CommMvpPresenter<V> {

    @Inject
    public CommPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getCommListData() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getShareList()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((res) -> {
                            //成功逻辑
                            getMvpView().hideLoading();
                            ((CommMainFragment)getMvpView()).setData(res);
                        }, (e) -> {
                            getMvpView().hideLoading();
                            getMvpView().handleApiError(e);
                        }
                ));
    }

    @Override
    public void publishShare(RequestShare share) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().publishShare(share)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((data) -> {
                            //成功逻辑
                            getMvpView().hideLoading();
                            getMvpView().onError("修改成功");
                            //退出到列表
                            getMvpView().Back();
                        }, (throwable) -> {
                            //失败逻辑
                            throwable.printStackTrace();
                            getMvpView().hideLoading();
                            if (throwable instanceof ApiFault) {
                                ApiFault fault = (ApiFault)throwable;
                                getMvpView().onError(fault.getMessage());
                                return;
                            }
                            getMvpView().handleApiError(throwable);
                        }
                ));
    }

    public void getShareDetail(int sid) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getShareDetail(sid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((res) -> {
                            //成功逻辑
                            getMvpView().hideLoading();
                            ((CommDetailActivity)getMvpView()).setData(res);
                        }, (e) -> {
                            getMvpView().hideLoading();
                            getMvpView().handleApiError(e);
                        }
                ));
    }
}

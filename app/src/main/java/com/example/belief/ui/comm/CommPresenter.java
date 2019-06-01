package com.example.belief.ui.comm;

import com.example.belief.data.DataManager;
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
}

package com.example.belief.ui.user;

import com.example.belief.data.DataManager;
import com.example.belief.data.network.model.ApiFault;
import com.example.belief.data.network.model.UserAuth;
import com.example.belief.data.network.model.UserInfo;
import com.example.belief.ui.base.BasePresenter;
import com.example.belief.utils.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserPresenter<V extends UserMvpView> extends BasePresenter<V>
        implements UserMvpPresenter<V> {

    @Inject
    public UserPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void login(String username, String password) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().login(new UserAuth(username, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((map) -> {
                    //成功逻辑
                    getMvpView().hideLoading();
                    getMvpView().showMessage("登陆成功");
                    int uid = ((Number) map.get("uid")).intValue();
                    UserAuth userAuth = new UserAuth(uid, username, password);
                    Map args = new HashMap<String, Object>();
                    args.put("userAuth", userAuth);
                    args.put("photo_url", map.get("photo_url"));
                    getMvpView().openMainActivity(args);

                }, (throwable) -> {
                    //失败逻辑
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

    @Override
    public void register(String username, String password) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().register(new UserAuth(username, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((map) -> {
                            //成功逻辑
                            getMvpView().hideLoading();
                            getMvpView().showMessage("注册成功");
                            int uid = ((Number) map.get("uid")).intValue();
                            UserAuth userAuth = new UserAuth(uid, username, password);
                            Map args = new HashMap<String, Object>();
                            args.put("userAuth", userAuth);
                            getMvpView().openMainActivity(args);

                        }, (throwable) -> {
                            //失败逻辑
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


    //注册
    //显示用户个人信息
    public void getUserInfo(int uid) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getUserInfo(uid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((data) -> {
                            //成功逻辑
                            getMvpView().hideLoading();
                            getMvpView().setData(data);
                        }, (throwable) -> {
                            throwable.printStackTrace();
                            //失败逻辑
                            getMvpView().hideLoading();
                            if (throwable instanceof ApiFault) {
                                ApiFault fault = (ApiFault) throwable;
                                getMvpView().onError(fault.getMessage());
                                return;
                            }
                            getMvpView().handleApiError(throwable);
                        }
                ));
    }

    public void updateUserInfo(UserInfo info) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().updateUserInfo(info)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((data) -> {
                        //成功逻辑
                        getMvpView().hideLoading();
                        getMvpView().onError("修改成功");
                    }, (throwable) -> {
                        //失败逻辑
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

    @Override
    public void getSportInfo(int uid) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getSportInfo(uid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((data) -> {
                            //成功逻辑
                            getMvpView().hideLoading();
                    ((UserMainFragment)getMvpView()).setSportInfo(data);
                        }, (throwable) -> {
                            throwable.printStackTrace();
                            //失败逻辑
                            getMvpView().hideLoading();
                            if (throwable instanceof ApiFault) {
                                ApiFault fault = (ApiFault) throwable;
                                getMvpView().onError(fault.getMessage());
                                return;
                            }
                            getMvpView().handleApiError(throwable);
                        }
                ));
    }

    //修改个人信息
    //显示卡路里趋势
    //显示用户收藏的食谱
}

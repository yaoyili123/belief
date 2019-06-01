package com.example.belief.ui.sport;

import android.widget.SimpleAdapter;

import com.example.belief.data.DataManager;
import com.example.belief.data.network.model.ApiFault;
import com.example.belief.data.network.model.UserAuth;
import com.example.belief.ui.base.BasePresenter;
import com.example.belief.utils.FileUtils;
import com.example.belief.utils.rx.SchedulerProvider;
import com.flyco.dialog.widget.NormalDialog;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SportPresenter<V extends SportMvpView> extends BasePresenter<V>
        implements SportMvpPresenter<V> {

    @Inject
    public SportPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    //准备主页数据
    @Override
    public void onMainViewPrepared(UserAuth userAuth, String imageName) {
        //获取用户头像
        downPic(imageName, ((SportMainFragment)getMvpView()).userHead);
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
    public void getSportClass(int scid, int uid) {
        getCompositeDisposable().add(getDataManager().getSportClass(scid, uid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((res) -> {
                            //成功逻辑
                            ((ClassDetailActivity)getMvpView()).setData(res);
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
                        ((ShowClassActivity)getMvpView()).setData(res);
                    }, (e) -> {
                        getMvpView().hideLoading();
                        getMvpView().handleApiError(e);
                    }
                ));
    }

    @Override
    public void getAllClasses() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getAllClasses()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((res) -> {
                            //成功逻辑
                            getMvpView().hideLoading();
                            ((ShowClassActivity)getMvpView()).setData(res);
                        }, (e) -> {
                            getMvpView().hideLoading();
                            getMvpView().handleApiError(e);
                        }
                ));
    }

    //删除参加的课程
    public void deleteJoinedClass(int uid, int scid, int pos,
                                  List showList, List classList, NormalDialog dialog, SimpleAdapter adapter){
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().deleteJoinedClass(uid, scid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((map) -> {
                    //成功逻辑
                    getMvpView().hideLoading();
                    getMvpView().showMessage("删除成功");
                    showList.remove(pos);
                    classList.remove(pos);
                    dialog.dismiss();
                    adapter.notifyDataSetChanged();

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
    public void getSportActions(int scid) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getSportActions(scid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((res) -> {
                        //成功逻辑
                        getMvpView().hideLoading();
                        ((StartSportActivity)getMvpView()).setData(res);
                    }, (e) -> {
                        getMvpView().hideLoading();
                        getMvpView().handleApiError(e);
                    }
                ));
    }

    public void downPicGIF(String url) {
        if (url == null || url.isEmpty())
            return;
        //获取用户头像
        getCompositeDisposable().add(getDataManager().downPic(url)
                .map(body -> {
                    FileUtils.createFileWithByte(body.bytes(), url);
                    return url;
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((fileName) -> {
//                    Long gifLen = body.contentLength();
//                    Log.d("Sport", "GIF图片大小" + gifLen);
//                    ByteBuffer bytes = new ByteBuffer();
//                    BufferedInputStream bis =
//                            new BufferedInputStream(body.byteStream(), gifLen.intValue());
                    ((StartSportActivity)getMvpView()).setActionImgs(url);
                }, (e) -> {
                    e.printStackTrace();
                    getMvpView().onError("图片加载失败");
                    getMvpView().handleApiError(e);
                }));
    }

    @Override
    public void addClassToUser(int scid, int uid) {
        getCompositeDisposable().add(getDataManager().addClassToUser(uid, scid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((map) -> {

                        }, (throwable) -> {
                            throwable.printStackTrace();
                        }
                ));
    }

    @Override
    public void settleKcal(int uid, int kcal, int time) {
        getCompositeDisposable().add(getDataManager().settleKcal(uid, kcal, time)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((map) -> {

                        }, (throwable) -> {
                            throwable.printStackTrace();
                        }
                ));
    }
}

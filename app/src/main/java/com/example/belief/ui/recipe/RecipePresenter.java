package com.example.belief.ui.recipe;

import com.example.belief.data.DataManager;
import com.example.belief.data.network.model.ApiFault;
import com.example.belief.ui.base.BasePresenter;
import com.example.belief.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class RecipePresenter<V extends RecipeMvpView> extends BasePresenter<V> implements RecipeMvpPresenter<V> {

    @Inject
    public RecipePresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getRecipeDetail(int rid) {
        getCompositeDisposable().add(getDataManager().getRecipeDetail(rid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((res) -> {
                            //成功逻辑
                            ((RecipeDetailActivity)getMvpView()).setData(res);
                        }, (e) -> {
                            e.printStackTrace();
                            getMvpView().handleApiError(e);
                        }
                ));
    }

    @Override
    public void getRecipesByType(int tid, int uid) {
        getCompositeDisposable().add(getDataManager().getRecipesByType(tid, uid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((res) -> {
                            //成功逻辑
                            ((RecipeListActivity)getMvpView()).setData(res);
                        }, (e) -> {
                            e.printStackTrace();
                            getMvpView().handleApiError(e);
                        }
                ));
    }

    @Override
    public void getFoods() {
        getCompositeDisposable().add(getDataManager().getFoods()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((res) -> {
                    ((FoodListActivity)getMvpView()).setData(res);
                }, (e) -> {
                    e.printStackTrace();
                    getMvpView().handleApiError(e);
                }
                ));
    }

    @Override
    public void addRecipe(int uid, int rid) {
        getCompositeDisposable().add(getDataManager().addRecipe(uid, rid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((data) -> {
                            //成功逻辑
//                            getMvpView().showMessage("收藏成功");
                        }, (throwable) -> {
                            //失败逻辑
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
    public void deleteRecipe(int uid, int rid) {
        getCompositeDisposable().add(getDataManager().deleteRecipe(uid, rid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((data) -> {
                            //成功逻辑
//                            getMvpView().showMessage("收藏成功");
                        }, (throwable) -> {
                            //失败逻辑
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
    public void getFoodDetail(int fid) {
        getCompositeDisposable().add(getDataManager().getFoodDetail(fid)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((res) -> {
                        //成功逻辑
                        ((FoodDetailActivity)getMvpView()).setData(res);
                    }, (e) -> {
                        e.printStackTrace();
                        getMvpView().handleApiError(e);
                    }
                ));
    }
}

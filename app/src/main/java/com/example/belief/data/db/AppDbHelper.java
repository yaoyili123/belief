package com.example.belief.data.db;

import android.content.Context;

import com.example.belief.MvpApp;
import com.example.belief.data.db.model.DaoSession;
import com.example.belief.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(@ApplicationContext Context context) {
        mDaoSession = ((MvpApp)context).getDaoSession();
    }

//    @Override
//    public Observable<List<SportClass>> getAllSportClasses() {
//        return Observable.fromCallable(() -> mDaoSession.getSportClassDao().loadAll());
//    }

    @Override
    public <T> Observable<Object> getAllData (Class<T> type) {
        try {
            switch (type.getCanonicalName()) {
                case "SportClass":
                    return Observable.fromCallable(() -> mDaoSession.getSportClassDao().loadAll());
                case "RecipeType":
                    return Observable.fromCallable(() -> mDaoSession.getRecipeTypeDao().loadAll());
                case "Food":
                    return Observable.fromCallable(() -> mDaoSession.getFoodDao().loadAll());
                case "Recipe":
                    return Observable.fromCallable(() -> mDaoSession.getRecipeDao().loadAll());
                default:
                    throw new RuntimeException("getAllData Type Error");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> Observable<Object> getDataById(Class<T> type, Long id) {

        try {
            switch (type.getCanonicalName()) {
                case "SportClass":
                    return Observable.fromCallable(() -> mDaoSession.getSportClassDao().loadByRowId(id));
                case "RecipeType":
                    return Observable.fromCallable(() -> mDaoSession.getRecipeTypeDao().loadByRowId(id));
                case "Food":
                    return Observable.fromCallable(() -> mDaoSession.getFoodDao().loadByRowId(id));
                case "Recipe":
                    return Observable.fromCallable(() -> mDaoSession.getRecipeDao().loadByRowId(id));
                default:
                    throw new RuntimeException("getDataById Type Error");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

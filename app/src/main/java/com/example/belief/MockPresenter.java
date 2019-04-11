package com.example.belief;

import com.example.belief.data.DataManager;
import com.example.belief.data.db.model.DaoSession;
import com.example.belief.data.db.model.Food;
import com.example.belief.data.db.model.Recipe;
import com.example.belief.data.db.model.SportClass;
import com.example.belief.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/*
* 假P层接口，用于测试M层接口
* */
public class MockPresenter {

    private final DataManager mDataManager;

    private final SchedulerProvider mSchedulerProvider;

    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public MockPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = compositeDisposable;
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

    public void onDetach() {
        mCompositeDisposable.dispose();
    }

    //添加测试数据
    public static void mockInsert(DaoSession session) {
        session.getSportClassDao().insert(new SportClass((long)1, "腹肌训练", (long)15,
                (long)241, (long)0, (long)0, "www", "人鱼线的PDD"));
        session.getSportClassDao().insert(new SportClass((long)2, "鸡鸡训练", (long)15,
                (long)241, (long)0, (long)0, "www", "人鱼线的PDD"));

        session.getFoodDao().insert(new Food((long)0, "pdd", (long)0, "HAHA", "HAHA"));
        session.getFoodDao().insert(new Food((long)1, "wzs", (long)0, "HAHA", "HAHA"));

        session.getRecipeDao().insert(new Recipe((long)0, (long)0, "pdd", (long)0, "HAHAH"));
        session.getRecipeDao().insert(new Recipe((long)0, (long)0, "wzs", (long)0, "HAHAH"));

    }

    public <T> void getAllData(Class<T> type) {

        getCompositeDisposable().add(getDataManager()
                .getAllClientData(type)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((Object list) -> {
                    List<T> tmp = (List<T>) list;
                    System.out.print("getAllData() result: " + tmp.size());
                }));
    }
    
}

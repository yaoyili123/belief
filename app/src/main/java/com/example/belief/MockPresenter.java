package com.example.belief;

import com.example.belief.data.DataManager;
import com.example.belief.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/*
* 假P层接口，用于测试M层接口，暂时不会放在源代码当中
* */
public class MockPresenter {

    private final DataManager mDataManager;

    private final SchedulerProvider mSchedulerProvider;

    //统一处理订阅，一个Observable管理器
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
        //防止UI销毁导致的内存泄漏
        mCompositeDisposable.dispose();
    }


//    public void getJoinedClasses(int uid) {
//        getCompositeDisposable().add(getDataManager().getJoinedClasses(uid)
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe((List<Integer> list) -> {
//                    String log = "Uid : " + uid + "JoinedClass: ";
//                    for (int cid : list) {
//                        log += cid;
//                    }
//                    Log.d("getJoinedClasses", log);
//                }, (t) -> {
//                    Log.e("getJoinedClasses","error: "
//                            + t.getMessage());
//                }));
//    }

}

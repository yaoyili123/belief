package com.example.belief.di.module;

import android.app.Activity;
import android.content.Context;

import com.example.belief.MockPresenter;
import com.example.belief.di.ActivityContext;
import com.example.belief.ui.sport.SportMvpPresenter;
import com.example.belief.ui.sport.SportMvpView;
import com.example.belief.ui.sport.SportPresenter;
import com.example.belief.ui.user.UserMvpPresenter;
import com.example.belief.ui.user.UserMvpView;
import com.example.belief.ui.user.UserPresenter;
import com.example.belief.utils.rx.AppSchedulerProvider;
import com.example.belief.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/*
* 提供V层、P层以及依赖的provider
* 可以看出凡是V层所持有的依赖（P层组件）都是PerActivity scope的
* */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    //以下的类不需要@PerActivity，因为它们本身就依赖activity实例
    @Provides
    //指定该方法产生的bean的作用域为ActivityContext，提供活动生命周期的bean
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    //FIXME：我猜 有参数的应该是直接去用Contructor进行注入，再这里只是为了声明Scope
    @Provides
//    @PerActivity 这个类不作为V层组件的依赖，仅用来测试
    MockPresenter provideMockPresenter(
            MockPresenter presenter) {
        return presenter;
    }

    @Provides
    UserMvpPresenter<UserMvpView>
    privideUserPresenter(UserPresenter<UserMvpView> presenter) {
        return presenter;
    }

    @Provides
    SportMvpPresenter<SportMvpView>
    privideSportPresenter(SportPresenter<SportMvpView> presenter) {
        return presenter;
    }
}
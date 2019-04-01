package com.example.belief.di.module;

import android.app.Activity;
import android.content.Context;

import com.example.belief.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    //以下的类不需要@PerActivity，因为它们本身就依赖activity实例
    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }
}
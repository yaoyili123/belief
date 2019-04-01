package com.example.belief;

import android.app.Application;
import android.content.Context;

import com.example.belief.data.DataManager;
import com.example.belief.di.component.ApplicationComponent;
import com.example.belief.di.module.ApplicationModule;
import com.example.belief.di.component.DaggerApplicationComponent;

import javax.inject.Inject;
/*
*Application会在所有android components之前被创建，一般用于初始化全局状态
* */

public class MvpApp extends Application {

    @Inject
    DataManager dataManager;

    private ApplicationComponent applicationComponent;

    //用于提供自己实例对象的静态方法
    public static MvpApp get(Context context) {
        return (MvpApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}

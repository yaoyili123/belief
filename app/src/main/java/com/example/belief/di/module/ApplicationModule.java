package com.example.belief.di.module;

import android.app.Application;
import android.content.Context;

import com.example.belief.data.network.ApiHelper;
import com.example.belief.di.ApplicationContext;
import com.example.belief.utils.RetrofitServiceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//提供ApplicationContext限定符下的bean定义
/*每个Module类中的每一个会Provides都会生成一个Fectory<Return_type>实现类
*   里面有两个方法 Return_type get() 和  Fectory<Return_type> create方法
*   其中create方法用来给Component生成类使用
*/
/*
* 提供一些全局的依赖
* */
@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper() {
        return RetrofitServiceManager.getInstance().create(ApiHelper.class);
    }
}
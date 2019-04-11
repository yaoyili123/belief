package com.example.belief.di.component;

import android.app.Application;
import android.content.Context;

import com.example.belief.MvpApp;
import com.example.belief.data.DataManager;
import com.example.belief.data.db.DbHelper;
import com.example.belief.di.ApplicationContext;
import com.example.belief.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

//Component用于建立和graph之间的binding，不建立仍然可以实现DI，目的是为了给类成员进行注入,
//这些类往往无法通过constructor和method进行注入，比如Android API，必须通过Component进行连接

//所谓binding就是component和graph之间的一种关系，同时binding还可以设置scope

/*根据生成的代码，所有@Singleton的bean class（都提供了@Inject的constructor，因此可以作为Provider）
    都会在其生成的实现类中，以Provider的形式存在
    Provider也是一种Fectory，由module中的provides标记的生成类提供
*/
@Singleton //其所有Bean都是单例

//modules是一种声明binding的方法
@Component(modules = ApplicationModule.class)
//这个类会被dragger2实现，用于访问依赖图，它一般会在Android的主程序中被创建
public interface ApplicationComponent {

    //若member var通过@inject被注入，必须通过使用该方法来告诉dragger以扫描（通过实现接口的方式）
    //其他方式则不需要
    void inject(MvpApp mvpApp);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    DbHelper getDbHelper();
}
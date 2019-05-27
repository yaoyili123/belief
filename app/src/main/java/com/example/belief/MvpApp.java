package com.example.belief;

import android.app.Application;
import android.content.Context;

import com.example.belief.data.DataManager;
import com.example.belief.data.network.model.UserAuth;
import com.example.belief.di.component.ApplicationComponent;
import com.example.belief.di.component.DaggerApplicationComponent;
import com.example.belief.di.module.ApplicationModule;
import com.example.belief.ui.base.MvpView;
import com.example.belief.utils.ToastUtils;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
/*
*Application会在所有android components之前被创建，一般用于初始化全局状态
* */
/*TODO: greenDao的helper会与dagger和rxjava一起使用，复习一下
* rxjava看下mvp示例里面的那篇文章，然后只需要搞懂示例里面用到的API即可
* dagger看下知乎哪篇文章以及示例的用法即可
* 然后编写Helper，然后进行单测
* */
public class MvpApp extends Application {

    @Inject
    DataManager dataManager;

    private UserAuth curUser;

    private String userHead;

    //DI连接器，用来对Application的成员进行注入
    private ApplicationComponent applicationComponent;

    public Map<Integer, String> classType;

    public Map<Integer, String> classLevel;

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

        ToastUtils.init(this);

        Logger.addLogAdapter(new AndroidLogAdapter());

        //创建fragmentation悬浮球
//        Fragmentation.builder()
//                .stackViewMode(Fragmentation.BUBBLE)
//                .debug(BuildConfig.DEBUG).install();
        ToastUtils.init(this);

        //启动DEBUG工具
        Stetho.initializeWithDefaults(this);

        //初始化全局变量
        initConst();
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    //是否登陆，没有就跳转
    public void checkLogined(MvpView context) {
        if (curUser == null)
        context.toLogin();
    }

    public boolean isLogined(MvpView context) {
        return curUser != null;
    }

    public UserAuth getCurUser() {
        return curUser;
    }

    public void setCurUser(UserAuth curUser) {
        this.curUser = curUser;
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }

    public void setComponent(ApplicationComponent ac) {
        applicationComponent = ac;
    }

    private void initConst() {
        classType = new HashMap<>();
        classLevel = new HashMap<>();

        classLevel.put(0, "初级");
        classLevel.put(1, "中级");
        classLevel.put(2, "高级");

        classType.put(0, "瑜伽");
        classType.put(1, "核心");
        classType.put(2, "上肢");
        classType.put(3, "基础");
    }
}
package com.example.belief;

import android.app.Application;
import android.content.Context;

import com.example.belief.data.DataManager;
import com.example.belief.data.db.model.DaoMaster;
import com.example.belief.data.db.model.DaoSession;
import com.example.belief.di.component.ApplicationComponent;
import com.example.belief.di.component.DaggerApplicationComponent;
import com.example.belief.di.module.ApplicationModule;
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

    private DaoSession mDaoSession;

    //DI连接器，用来对Application的成员进行注入
    private ApplicationComponent applicationComponent;

    public Map<Long, String> classType;

    public Map<Long, String> classLevel;

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
//        ToastUtils.init(this);
//        SQLiteStudioService.instance().start(this);

        //启动DEBUG工具
        Stetho.initializeWithDefaults(this);

        //使用SQL脚本更新数据库
////        updateDbByScript();

        //初始化全局变量
        initConst();

//        AndroidNetworking.initialize(getApplicationContext());

        mDaoSession = new DaoMaster(
                new DaoMaster.DevOpenHelper(this, "belief.db").getWritableDb()).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }

    public void setComponent(ApplicationComponent ac) {
        applicationComponent = ac;
    }

//    private void updateDbByScript() {
//
//        //每次使用时，version加一即可
//        SQLiteDatabase db = new FileHelper(this, "belief.db", 7)
//                .getWritableDatabase();
//
//        try {
//            InputStream in = getAssets().open("belief-client.sql");
//            String sqlUpdate = null;
//            try {
//                sqlUpdate = FileUtils.readTextFromSDcard(in);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            String[] s = sqlUpdate.split(";");
//            for (int i = 0; i < s.length; i++) {
//                if (!TextUtils.isEmpty(s[i])) {
//                    db.execSQL(s[i]);
//                }
//            }
//            in.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void initConst() {
        classType = new HashMap<>();
        classLevel = new HashMap<>();

        classLevel.put((long)0, "初级");
        classLevel.put((long)1, "中级");
        classLevel.put((long)2, "高级");

        classType.put((long)0, "瑜伽");
        classType.put((long)1, "核心");
        classType.put((long)2, "上肢");
    }
}

////用于使用SQL脚本对数据库进行更新
//class FileHelper extends SQLiteOpenHelper {
//
//    public FileHelper(Context context,
//                    String dbName,
//                    Integer version) {
//        super(context, dbName, null, version);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
//}

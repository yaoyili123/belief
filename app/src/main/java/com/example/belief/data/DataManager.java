package com.example.belief.data;

import android.content.Context;

import com.example.belief.data.db.DbHelper;
import com.example.belief.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/*
* 作为所有数据访问代码的proxy接口，由P层调用
* M层代码总接口
* */

@Singleton
public class DataManager {

    private Context mContext;
    private DbHelper mDbHelper;

    @Inject
    public DataManager(@ApplicationContext Context context,
                       DbHelper dbHelper) {
        mContext = context;
        mDbHelper = dbHelper;
    }

    public <T> Observable<Object> getAllClientData(Class<T> type) {
        return mDbHelper.getAllData(type);
    }

    public <T> Observable<Object> getClientDataById(Class<T> type, Long id) {
        return mDbHelper.getDataById(type, id);
    }
}
package com.example.belief.data;

import android.content.Context;

import com.example.belief.data.db.DbHelper;
import com.example.belief.data.network.ApiHelper;
import com.example.belief.data.network.model.RequestShare;
import com.example.belief.data.network.model.ResponseWrapper;
import com.example.belief.data.network.model.ShareInfo;
import com.example.belief.data.network.model.UserAuth;
import com.example.belief.data.network.model.UserInfo;
import com.example.belief.data.network.model.UserKcalTrend;
import com.example.belief.data.network.model.UserSportInfo;
import com.example.belief.di.ApplicationContext;
import com.example.belief.utils.RetrofitServiceManager;

import java.util.List;
import java.util.Map;

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
    private ApiHelper mApiHelper;

    @Inject
    public DataManager(@ApplicationContext Context context,
                       DbHelper dbHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mApiHelper = RetrofitServiceManager.getInstance().create(ApiHelper.class);
    }

    //取出网络请求中的data，若出错则抛出异常由Subscriber处理
    public <T> Observable<T> payLoad(Observable<ResponseWrapper<T>> observable) {
        return observable.map((ResponseWrapper<T> response) -> {
            if (!response.isSuccess())
                throw new RuntimeException(response.getMessage());
            return response.getData();
        });
    }

    public <T> Observable<Object> getAllClientData(Class<T> type) {
        return mDbHelper.getAllData(type);
    }

    public <T> Observable<Object> getClientDataById(Class<T> type, Long id) {
        return mDbHelper.getDataById(type, id);
    }

    public Observable<List<Integer>> getJoinedClasses(int uid) {
        return payLoad(mApiHelper.getJoinedClasses(uid));
    }

    public Observable<Integer> getTotalKcal(int uid) {
        return payLoad(mApiHelper.getTotalKcal(uid));
    }

    Observable<Map> addClassToUser(int uid, List<Integer> classList) {
        return payLoad(mApiHelper.addClassToUser(uid, classList));
    }

    Observable<Map> settleKcal( int uid, int kcal, int time){
        return payLoad(mApiHelper.settleKcal(uid, kcal, time));
    }

    Observable<Map> register(UserAuth userAuth){

        return payLoad(mApiHelper.register(userAuth));
    }

    Observable<List<UserSportInfo>> getSportInfo(int uid){

        return payLoad(mApiHelper.getSportInfo(uid));
    }

    Observable<Map> updateUserInfo(UserInfo userInfo){

        return payLoad(mApiHelper.updateUserInfo(userInfo));
    }

    Observable<UserInfo> getUserInfo(Integer uid){

        return payLoad(mApiHelper.getUserInfo(uid));
    }

    Observable<List<UserKcalTrend>> getKcalTrand(int uid, int type){
        return payLoad(mApiHelper.getKcalTrand(uid, type));
    }

    Observable<List<ShareInfo>> getShareList(){

        return payLoad(mApiHelper.getShareList());
    }

    Observable<Map> publishShare(RequestShare share){

        return payLoad(mApiHelper.publishShare(share));
    }

    Observable<List<Integer>> getRecipes(int uid){

        return payLoad(mApiHelper.getRecipes(uid));
    }

    Observable<Map> addRecipe(int uid, int rid){

        return payLoad(mApiHelper.addRecipe(uid, rid));
    }
    
    Observable<Map> deleteRecipe(int uid, int rid){

        return payLoad(mApiHelper.deleteRecipe(uid, rid));
    }
}
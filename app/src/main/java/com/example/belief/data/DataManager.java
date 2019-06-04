package com.example.belief.data;

import android.content.Context;
import android.util.Log;

import com.example.belief.data.network.ApiHelper;
import com.example.belief.data.network.model.ApiFault;
import com.example.belief.data.network.model.Food;
import com.example.belief.data.network.model.Recipe;
import com.example.belief.data.network.model.RecipeType;
import com.example.belief.data.network.model.RequestShare;
import com.example.belief.data.network.model.ResponseWrapper;
import com.example.belief.data.network.model.ShareInfoResponse;
import com.example.belief.data.network.model.SportAction;
import com.example.belief.data.network.model.SportClass;
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
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Path;

/*
* 作为所有数据访问代码的proxy接口，由P层调用
* M层代码总接口
* */

@Singleton
public class DataManager {

    private Context mContext;
    private ApiHelper mApiHelper;

    @Inject
    public DataManager(@ApplicationContext Context context,
                       ApiHelper apiHelper) {
        mContext = context;
        mApiHelper = apiHelper;
    }

    //取出网络请求中的data，若出错则抛出异常由Subscriber处理
    public <T> Observable<T> payLoad(Observable<ResponseWrapper<T>> observable) {
        return observable.map((ResponseWrapper<T> response) -> {
            if (!response.isSuccess())
                throw new ApiFault(response.getStatus(), response.getMessage());
            return response.getData();
        });
    }

    //取出网络请求中的data，若出错则抛出异常由Subscriber处理
    public <T> Single<T> payLoad(Single<ResponseWrapper<T>> observable) {
        return observable.map((ResponseWrapper<T> response) -> {
            if (!response.isSuccess())
                throw new ApiFault(response.getStatus(), response.getMessage());
            return response.getData();
        });
    }

    public Observable<Map> UploadFile(RequestBody image, String filename) {
        Log.d("MyLog", "文件名:"+ filename);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", filename, image);

        return payLoad(mApiHelper.UploadFile(body));
    }

    public Single<ResponseBody>  downPic(String fileName) {
        return mApiHelper.downPic(RetrofitServiceManager.BASE_URL + "/" + fileName);
    }

    public Observable<List<SportAction>> getSportActions(int scid) {
        return payLoad(mApiHelper.getSportActions(scid));
    }

    public Single<Map> getSportClass(int scid, int uid) {
        return payLoad(mApiHelper.getSportClass(scid, uid));
    }

    public Observable<List<SportClass>> getAllClasses() {
        return payLoad(mApiHelper.getAllClasses());
    }

    public Observable<List<SportClass>> getJoinedClasses(int uid) {
        return payLoad(mApiHelper.getJoinedClasses(uid));
    }

    public Observable<Integer> getTotalKcal(int uid) {
        return payLoad(mApiHelper.getTotalKcal(uid));
    }

    public Single<Map> addClassToUser(int uid, int scid) {
        return payLoad(mApiHelper.addClassToUser(uid, scid));
    }

    public Single<Map> deleteJoinedClass(int uid, int scid){
        return payLoad(mApiHelper.deleteJoinedClass(uid, scid));
    }

    public Single<Map> settleKcal( int uid, int kcal, int time){
        return payLoad(mApiHelper.settleKcal(uid, kcal, time));
    }

    public Single<Map<String, Object>> register(UserAuth userAuth){

        return payLoad(mApiHelper.register(userAuth));
    }

    public Single<Map<String, Object>> login(UserAuth userAuth){

        return payLoad(mApiHelper.login(userAuth));
    }

    public Observable<List<UserSportInfo>> getSportInfo(int uid){

        return payLoad(mApiHelper.getSportInfo(uid));
    }

    public Single<Map> updateUserInfo(UserInfo userInfo){

        return payLoad(mApiHelper.updateUserInfo(userInfo));
    }

    public Observable<UserInfo> getUserInfo(Integer uid){

        return payLoad(mApiHelper.getUserInfo(uid));
    }

    public Observable<List<UserKcalTrend>> getKcalTrand(int uid, int type){
        return payLoad(mApiHelper.getKcalTrand(uid, type));
    }

    public Observable<List<ShareInfoResponse>> getShareList(){

        return payLoad(mApiHelper.getShareList());
    }

    public Observable<ShareInfoResponse> getShareDetail(int sid) {
        return payLoad(mApiHelper.getShareDetail(sid));
    }

    public Single<Map> publishShare(RequestShare share){

        return payLoad(mApiHelper.publishShare(share));
    }

    public Observable<List<Recipe>> getRecipesByUser(int uid){

        return payLoad(mApiHelper.getRecipesByUser(uid));
    }

    public Single<Map> addRecipe(int uid, int rid){

        return payLoad(mApiHelper.addRecipe(uid, rid));
    }

    public Single<Map> deleteRecipe(int uid, int rid){
        return payLoad(mApiHelper.deleteRecipe(uid, rid));
    }

    public Observable<List<Food>> getFoods() {
        return payLoad(mApiHelper.getFoods());
    }

    public Observable<List<RecipeType>> getRecipeType() {
        return payLoad(mApiHelper.getRecipeType());
    }

    public Observable<List<Recipe>> getRecipesByType(@Path("tid")int tid) {
        return payLoad(mApiHelper.getRecipesByType(tid));
    }
}
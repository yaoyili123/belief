package com.example.belief.data.network;

import com.example.belief.data.network.model.RequestShare;
import com.example.belief.data.network.model.ResponseWrapper;
import com.example.belief.data.network.model.ShareInfo;
import com.example.belief.data.network.model.UserAuth;
import com.example.belief.data.network.model.UserInfo;
import com.example.belief.data.network.model.UserKcalTrend;
import com.example.belief.data.network.model.UserSportInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiHelper {

    @GET("sport/class/{uid}")
    Observable<ResponseWrapper<List<Integer>>> getJoinedClasses(@Path("uid") int uid);

    @GET("sport/total_time/{uid}")
    Observable<ResponseWrapper<Integer>> getTotalKcal(@Path("uid") int uid);

    @POST("sport/add_class/{uid}")
    Observable<ResponseWrapper<Map>> addClassToUser(@Path("uid") int uid, @Body List<Integer> classList);

    @PUT("sport/settle/{uid}/{kcal}/{time}")
    Observable<ResponseWrapper<Map>> settleKcal(@Path("uid") int uid, @Path("kcal") int kcal,
               @Path("time") int time);

    @GET("user/register")
    Observable<ResponseWrapper<Map>> register(@Body UserAuth userAuth);

    @GET("user/sport_info/{uid}")
    Observable<ResponseWrapper<List<UserSportInfo>>> getSportInfo(@Path("uid") int uid);

    @PUT("user/user_info")
    Observable<ResponseWrapper<Map>> updateUserInfo(@Body UserInfo userInfo);

    @GET("user/user_info/{uid}")
    Observable<ResponseWrapper<UserInfo>> getUserInfo(@Path("uid") Integer uid);

    @GET("user/kcal_trend/{uid}/{type}")
    Observable<ResponseWrapper<List<UserKcalTrend>>>
    getKcalTrand(@Path("uid")int uid, @Path("type")int type);

    @GET("comm/share_list")
    Observable<ResponseWrapper<List<ShareInfo>>> getShareList();

    @POST("comm/share")
    Observable<ResponseWrapper<Map>> publishShare(@Body RequestShare share);

    @GET("recipe/collects/{uid}")
    Observable<ResponseWrapper<List<Integer>>> getRecipes(@Path("uid")int uid);

    @PUT("recipe/collects/{uid}/{rid}")
    Observable<ResponseWrapper<Map>> addRecipe(@Path("uid")int uid, @Path("rid")int rid);

    @DELETE("recipe/collects/{uid}/{rid}")
    Observable<ResponseWrapper<Map>> deleteRecipe(@Path("uid")int uid, @Path("rid")int rid);
}

package com.example.belief.data.network;

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

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

//当返回值为Map时，由于Map也是集合的一种，
// 使用Observerable时每次处理某个元素时，onNext就会结束
// 因此应该使用Single
public interface ApiHelper {

    //下载图片
    @Streaming
    @GET
    Single<ResponseBody> downPic(@Url String fileUrl);

    //上传图片并更新
    @Multipart
    @POST("uploadFile")
    Observable<ResponseWrapper<Map>> UploadFile(@Part MultipartBody.Part image);

    @GET("sport/actions/{scid}")
    Observable<ResponseWrapper<List<SportAction>>> getSportActions(@Path("scid") int scid);

    @GET("sport/show_class")
    Observable<ResponseWrapper<List<SportClass>>> getAllClasses();

    @GET("sport/show_class/{uid}/{scid}")
    Single<ResponseWrapper<Map>> getSportClass(@Path("scid") int scid, @Path("uid") int uid);

    @GET("sport/joined_class/{uid}")
    Observable<ResponseWrapper<List<SportClass>>> getJoinedClasses(@Path("uid") int uid);

    @GET("sport/total_time/{uid}")
    Observable<ResponseWrapper<Integer>> getTotalKcal(@Path("uid") int uid);

    @PUT("sport/add_class/{uid}/{scid}")
    Single<ResponseWrapper<Map>> addClassToUser(@Path("uid") int uid, @Path("scid") int scid);

    @DELETE("sport/del_class/{uid}/{scid}")
    Single<ResponseWrapper<Map>> deleteJoinedClass(@Path("uid") int uid, @Path("scid") int scid);

    @PUT("sport/settle/{uid}/{kcal}/{time}")
    Single<ResponseWrapper<Map>> settleKcal(@Path("uid") int uid, @Path("kcal") int kcal,
               @Path("time") int time);

    @POST("user/login")
    Single<ResponseWrapper<Map<String, Object>>> login(@Body UserAuth userAuth);

    @POST("user/register")
    Single<ResponseWrapper<Map<String, Object>>> register(@Body UserAuth userAuth);

    @GET("user/sport_info/{uid}")
    Observable<ResponseWrapper<List<UserSportInfo>>> getSportInfo(@Path("uid") int uid);

    @PUT("user/user_info/update")
    Single<ResponseWrapper<Map>> updateUserInfo(@Body UserInfo userInfo);

    @GET("user/user_info/{uid}")
    Observable<ResponseWrapper<UserInfo>> getUserInfo(@Path("uid") Integer uid);

    @GET("user/kcal_trend/{uid}/{type}")
    Observable<ResponseWrapper<List<UserKcalTrend>>>
    getKcalTrand(@Path("uid")int uid, @Path("type")int type);

    @GET("comm/share_list")
    Observable<ResponseWrapper<List<ShareInfoResponse>>> getShareList();

    @GET("comm/share_detail/{sid}")
    Observable<ResponseWrapper<ShareInfoResponse>> getShareDetail(@Path("sid") Integer sid);

    @POST("comm/share")
    Single<ResponseWrapper<Map>> publishShare(@Body RequestShare share);

    @GET("recipe/collects/{uid}")
    Observable<ResponseWrapper<List<Recipe>>> getRecipesByUser(@Path("uid")int uid);

    @PUT("recipe/collects/{uid}/{rid}")
    Single<ResponseWrapper<Map>> addRecipe(@Path("uid")int uid, @Path("rid")int rid);

    @DELETE("recipe/collects/{uid}/{rid}")
    Single<ResponseWrapper<Map>> deleteRecipe(@Path("uid")int uid, @Path("rid")int rid);

    @GET("recipe/food")
    Observable<ResponseWrapper<List<Food>>> getFoods();

    @GET("recipe/type")
    Observable<ResponseWrapper<List<RecipeType>>> getRecipeType();

    @GET("recipe/{tid}")
    Observable<ResponseWrapper<List<Recipe>>> getRecipesByType(@Path("tid")int tid);
}

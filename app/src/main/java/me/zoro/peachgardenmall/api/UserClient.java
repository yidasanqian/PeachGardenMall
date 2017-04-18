package me.zoro.peachgardenmall.api;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by dengfengdecao on 16/11/11.
 */

public interface UserClient {

    @POST("sms/captcha/{recPhoneNum}")
    Call<JsonObject> fetchCaptcha(@Path("recPhoneNum") String tel);

    @POST("User/register")
    Call<JsonObject> saveUser(@Body Map<String, Object> params);

    @POST("User/do_login")
    Call<JsonObject> login(@Body Map<String, String> params);

    // 获取用户详细信息
    @GET("User/get_user_info")
    Call<JsonObject> fetchUserInfo(@Query("userId") int userId);

    // 登出
    @GET("User/logout")
    Call<JsonObject> logout(@Query("userId") int userId);

    @GET("user/{username}")
    Call<JsonObject> getUserInfo(@Path("username") String username);

    //修改用户信息
    @POST("user")
    Call<JsonObject> userInfoRevise(@QueryMap Map<String, Object> params);

    @Multipart
    @POST("user")
    Call<JsonObject> uploadAvatar(@PartMap Map<String, RequestBody> partMap,
                                  @Part MultipartBody.Part file);


    //忘记密码
    @POST("anon/user/new/password")
    Call<JsonObject> forgetPassword(@QueryMap Map<String, Object> params);

    //修改密码
    @POST("user/new/password")
    Call<JsonObject> changePassword(@QueryMap Map<String, Object> params);


}

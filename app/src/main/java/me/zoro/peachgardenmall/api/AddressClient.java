package me.zoro.peachgardenmall.api;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by dengfengdecao on 17/4/24.
 */

public interface AddressClient {


    @POST("User/userAddress")
    Call<JsonObject> save(@Body Map<String, Object> address);

    @GET("User/getUserAddress")
    Call<JsonObject> get(@QueryMap Map<String, Object> params);

    @POST("User/setDefaultAddress")
    Call<JsonObject> setUpDefault(@Body Map<String, Object> params);

    @GET("User/getAddressById")
    Call<JsonObject> getById(@QueryMap Map<String, Integer> params);
}

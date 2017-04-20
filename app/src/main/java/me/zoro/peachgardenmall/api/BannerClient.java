package me.zoro.peachgardenmall.api;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by dengfengdecao on 17/4/20.
 */

public interface BannerClient {

    @GET("Index/getAd")
    Call<JsonObject> getBannerInfo(@QueryMap Map<String, Object> params);
}

package me.zoro.peachgardenmall.api;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by dengfengdecao on 17/5/11.
 */

public interface OrderClient {
    @GET
    Call<JsonObject> getOrders(@QueryMap Map<String, Integer> reqParams);
}

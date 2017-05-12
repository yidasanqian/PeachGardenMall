package me.zoro.peachgardenmall.api;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by dengfengdecao on 16/11/30.
 */

public interface PayClient {

    @POST("Order/addOrder")
    Call<JsonObject> getOrderInfo(@Body Map<String, Object> orderReqParams);
}

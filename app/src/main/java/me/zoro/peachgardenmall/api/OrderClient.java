package me.zoro.peachgardenmall.api;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by dengfengdecao on 17/5/11.
 */

public interface OrderClient {

    @GET("Order/getOrderByOrderSn")
    Call<JsonObject> getOrder(@QueryMap Map<String, Object> reqParams);

    @GET("Order/getOrderList")
    Call<JsonObject> getOrders(@QueryMap Map<String, Integer> reqParams);

    @POST("Order/confirmTakeOrder")
    Call<JsonObject> updateOrderStatus(@Body Map<String, Object> reqParams);

    @POST("Order/addComment")
    Call<JsonObject> evaluateGoodses(@Body Map<String, Object> reqParams);
}

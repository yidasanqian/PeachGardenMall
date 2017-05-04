package me.zoro.peachgardenmall.api;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by dengfengdecao on 17/5/4.
 */

public interface ShoppingCartClient {

    @POST("Cart/addCart")
    Call<JsonObject> addToShoppingCart(@Body Map<String, Object> params);

    @GET("Cart/getCartList")
    Call<JsonObject> getShoppingCartGoodses(@QueryMap Map<String, Integer> params);

    @POST("Cart/deleteCart")
    Call<JsonObject> deleteGoodsFromCart(@Body Map<String, Object> params);
}

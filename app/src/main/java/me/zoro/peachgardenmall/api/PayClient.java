package me.zoro.peachgardenmall.api;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by dengfengdecao on 16/11/30.
 */

public interface PayClient {

    @POST("anon/signatures")
    Call<ResponseBody> getOrderInfo(@QueryMap Map<String, String> reqParams);

    @FormUrlEncoded
    @POST("withdraw/requset")
    Call<JsonObject> withdrawals(@FieldMap Map<String, Object> reqParams);
}

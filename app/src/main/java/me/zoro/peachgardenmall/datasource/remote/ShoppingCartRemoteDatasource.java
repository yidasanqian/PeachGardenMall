package me.zoro.peachgardenmall.datasource.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

import me.zoro.peachgardenmall.api.ServiceGenerator;
import me.zoro.peachgardenmall.api.ShoppingCartClient;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.ShoppingCartDatasource;
import me.zoro.peachgardenmall.datasource.domain.Cart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by dengfengdecao on 17/5/4.
 */

public class ShoppingCartRemoteDatasource implements ShoppingCartDatasource {

    public static ShoppingCartRemoteDatasource sRemoteDatasource;

    private ShoppingCartClient mCartClient;

    private ShoppingCartRemoteDatasource(Context context) {
        mCartClient = ServiceGenerator.createService(context, ShoppingCartClient.class);
    }

    public static ShoppingCartRemoteDatasource getInstance(Context context) {
        if (sRemoteDatasource == null) {
            sRemoteDatasource = new ShoppingCartRemoteDatasource(context);
        }

        return sRemoteDatasource;
    }

    @Override
    public void addToShoppingCart(Map<String, Object> params, @NonNull final AddToShoppingCartCallback callback) {
        Call<JsonObject> call = mCartClient.addToShoppingCart(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null) {
                    callback.onAddFailure(Const.SERVER_UNAVAILABLE);
                } else if (bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onAddFailure(bodyJson.get(Const.MESSAGE).getAsString());
                } else {
                    callback.onAddSuccess(bodyJson.get(Const.MESSAGE).getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 添加商品到购物车请求出现异常", t);
                callback.onAddFailure(Const.SERVER_UNAVAILABLE);

            }
        });
    }

    @Override
    public void deleteFromShoppingCart(Map<String, Object> params, @NonNull final DeleteFromShoppingCartCallback callback) {
        Call<JsonObject> call = mCartClient.deleteGoodsFromCart(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null) {
                    callback.onDeleteFailure(Const.SERVER_UNAVAILABLE);
                } else if (bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onDeleteFailure(bodyJson.get(Const.MESSAGE).getAsString());
                } else {
                    callback.onDeleteSuccess(bodyJson.get(Const.MESSAGE).getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 从购物车删除商品请求出现异常", t);
                callback.onDeleteFailure(Const.SERVER_UNAVAILABLE);
            }
        });
    }

    @Override
    public void getShoppingCartGoodses(Map<String, Integer> params, @NonNull final GetShoppingCartGoodsesCallback callback) {
        Call<JsonObject> call = mCartClient.getShoppingCartGoodses(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null) {
                    callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
                } else if (bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE).getAsString());
                } else {
                    Gson gson = new GsonBuilder().setLenient().create();
                    JsonArray jsonArray = bodyJson.get(Const.RESULT).getAsJsonArray();
                    ArrayList<Cart> cartList = new ArrayList<Cart>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Cart cart = gson.fromJson(jsonArray.get(i), Cart.class);
                        cartList.add(cart);
                    }
                    callback.onGoodsesLoaded(cartList);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure:获取我的购物车内的所有商品请求出现异常", t);
                callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
            }
        });
    }
}

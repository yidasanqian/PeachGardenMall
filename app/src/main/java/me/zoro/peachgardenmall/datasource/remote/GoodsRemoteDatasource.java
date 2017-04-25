package me.zoro.peachgardenmall.datasource.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.zoro.peachgardenmall.api.GoodsClient;
import me.zoro.peachgardenmall.api.ServiceGenerator;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.GoodsDatasource;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.domain.GoodsCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dengfengdecao on 17/4/21.
 */

public class GoodsRemoteDatasource implements GoodsDatasource {

    private static final String TAG = "GoodsRemoteDatasource";
    private static GoodsRemoteDatasource sRemoteDatasource;

    private GoodsClient mGoodsClient;

    public GoodsRemoteDatasource(Context context) {
        mGoodsClient = ServiceGenerator.createService(context, GoodsClient.class);
    }

    public static GoodsRemoteDatasource getInstance(Context context) {
        if (sRemoteDatasource == null) {
            sRemoteDatasource = new GoodsRemoteDatasource(context);
        }

        return sRemoteDatasource;
    }

    @Override
    public void getGoodses(Map<String, Object> params, @NonNull final GetGoodsesCallback callback) {
        Call<JsonObject> call = mGoodsClient.getGoodes(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int code = bodyJson.get(Const.CODE).getAsInt();
                    if (code == 0) {
                        Gson gson = new GsonBuilder().setLenient().create();
                        JsonArray jsonArray = bodyJson.get(Const.RESULT).getAsJsonArray();
                        ArrayList<Goods> goodsList = new ArrayList<Goods>();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonObject json = jsonArray.get(i).getAsJsonObject();
                            Goods goods = gson.fromJson(json, Goods.class);
                            goodsList.add(goods);
                        }
                        callback.onGoodsesLoaded(goodsList);
                    } else {
                        callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE).getAsString());
                    }
                } else {
                    callback.onDataNotAvailable(Const.SERVER_AVALIABLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取商品信息出现异常", t);
                callback.onDataNotAvailable(Const.SERVER_AVALIABLE);
            }
        });
    }

    @Override
    public void getGoodsCategories(Map<String, Object> params, @NonNull final GetGoodsCategoriesCallback callback) {

        Call<JsonObject> call = mGoodsClient.getGoodsCategories(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int code = bodyJson.get(Const.CODE).getAsInt();
                    if (code == 0) {
                        Gson gson = new GsonBuilder().setLenient().create();
                        JsonArray jsonArray = bodyJson.get(Const.RESULT).getAsJsonArray();
                        List<GoodsCategory> categories = new ArrayList<GoodsCategory>();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonObject json = jsonArray.get(i).getAsJsonObject();
                            GoodsCategory category = gson.fromJson(json, GoodsCategory.class);
                            categories.add(category);
                        }
                        callback.onGoodsCategoriesLoaded(categories);
                    } else {
                        callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE).getAsString());
                    }
                } else {
                    callback.onDataNotAvailable(Const.SERVER_AVALIABLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取商品类别出现异常", t);
                callback.onDataNotAvailable(Const.SERVER_AVALIABLE);
            }
        });
    }

    @Override
    public void searchGoodses(Map<String, Object> params, @NonNull final SearchGoodsesCallback callback) {
        Call<JsonObject> call = mGoodsClient.searchGoodses(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int code = bodyJson.get(Const.CODE).getAsInt();
                    if (code == 0) {
                        Gson gson = new GsonBuilder().setLenient().create();
                        JsonArray jsonArray = bodyJson.get(Const.RESULT).getAsJsonArray();
                        ArrayList<Goods> goodsList = new ArrayList<Goods>();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonObject json = jsonArray.get(i).getAsJsonObject();
                            Goods goods = gson.fromJson(json, Goods.class);
                            goodsList.add(goods);
                        }
                        callback.onSearchSucces(goodsList);
                    } else {
                        callback.onSearchFailure(bodyJson.get(Const.MESSAGE).getAsString());
                    }
                } else {
                    callback.onSearchFailure(Const.SERVER_AVALIABLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 搜索商品失败", t);
                callback.onSearchFailure(Const.SERVER_AVALIABLE);
            }
        });
    }

    @Override
    public void getGoodsDetail(int goodsId, @NonNull final GetGoodsDetailCallback callback) {
        Call<JsonObject> call = mGoodsClient.getGoodsDetail(goodsId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null || bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onDataNotAvailable(Const.SERVER_AVALIABLE);
                } else {
                    Gson gson = new GsonBuilder().setLenient().create();
                    Goods goods = gson.fromJson(bodyJson.get(Const.RESULT), Goods.class);
                    callback.onGoodsLoaded(goods);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取商品详情异常", t);
                callback.onDataNotAvailable(Const.SERVER_AVALIABLE);
            }
        });
    }
}

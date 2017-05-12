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
import me.zoro.peachgardenmall.datasource.domain.Comment;
import me.zoro.peachgardenmall.datasource.domain.Freight;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.domain.GoodsCategory;
import me.zoro.peachgardenmall.datasource.domain.Promotion;
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
                    callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取商品信息出现异常", t);
                callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
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
                    callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取商品类别出现异常", t);
                callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
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
                    callback.onSearchFailure(Const.SERVER_UNAVAILABLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 搜索商品失败", t);
                callback.onSearchFailure(Const.SERVER_UNAVAILABLE);
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
                    callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
                } else {
                    Gson gson = new GsonBuilder().setLenient().create();
                    Goods goods = gson.fromJson(bodyJson.get(Const.RESULT), Goods.class);
                    callback.onGoodsLoaded(goods);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取商品详情异常", t);
                callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
            }
        });
    }

    @Override
    public void getStarGoodses(Map<String, Object> params, @NonNull final GetStarGoodsesCallback callback) {
        Call<JsonObject> call = mGoodsClient.getStarGoodses(params);
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
                    ArrayList<Goods> goodsList = new ArrayList<Goods>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject json = jsonArray.get(i).getAsJsonObject();
                        Goods goods = gson.fromJson(json, Goods.class);
                        goodsList.add(goods);
                    }
                    callback.onGoodsesLoaded(goodsList);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取收藏的商品信息出现异常", t);
                callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
            }
        });
    }

    @Override
    public void starGoods(Map<String, Object> params, @NonNull final StarGoodsCallback callback) {
        Call<JsonObject> call = mGoodsClient.starGoods(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null) {
                    callback.onStarFailure(Const.SERVER_UNAVAILABLE);
                } else if (bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onStarFailure(bodyJson.get(Const.MESSAGE).getAsString());
                } else {
                    callback.onStarSuccess(bodyJson.get(Const.MESSAGE).getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 收藏商品信息出现异常", t);
                callback.onStarFailure(Const.SERVER_UNAVAILABLE);
            }
        });
    }

    @Override
    public void getIsStar(Map<String, Integer> params, @NonNull final GetIsStarCallback callback) {
        Call<JsonObject> call = mGoodsClient.getIsStar(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null) {
                    callback.onFaillure(Const.SERVER_UNAVAILABLE);
                } else if (bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onFaillure(bodyJson.get(Const.MESSAGE).getAsString());
                } else {
                    JsonObject resultJson = bodyJson.get(Const.RESULT).getAsJsonObject();
                    callback.onSuccess(resultJson.get("isStar").getAsBoolean());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取用户是否收藏过该商品请求出现异常", t);
                callback.onFaillure(Const.SERVER_UNAVAILABLE);
            }
        });
    }


    @Override
    public void getFreight(@NonNull final GetFreightCallback callback) {
        Call<JsonObject> call = mGoodsClient.getFreight();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null) {
                    callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
                } else if (bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE).getAsString());
                } else {
                    JsonObject resultJson = bodyJson.get(Const.RESULT).getAsJsonObject();
                    Gson gson = new GsonBuilder().setLenient().create();
                    Freight freight = gson.fromJson(resultJson, Freight.class);
                    callback.onFreightLoaded(freight);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取运费请求出现异常", t);
                callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);

            }
        });
    }

    @Override
    public void getPromotion(@NonNull final GetPromotionsCallback callback) {
        Call<JsonObject> call = mGoodsClient.getPromotion();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null) {
                    callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
                } else if (bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE).getAsString());
                } else {
                    JsonArray array = bodyJson.get(Const.RESULT).getAsJsonArray();
                    ArrayList<Promotion> promotions = new ArrayList<>();
                    Gson gson = new GsonBuilder().setLenient().create();
                    for (int i = 0; i < array.size(); i++) {
                        Promotion promotion = gson.fromJson(array.get(i), Promotion.class);
                        promotions.add(promotion);
                    }
                    callback.onPromotionsLoaded(promotions);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取商品促销活动请求出现异常", t);
                callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
            }
        });
    }

    @Override
    public void getCommentByGoodsId(Map<String, Integer> params, @NonNull final GetCommentsCallback callback) {
        Call<JsonObject> call = mGoodsClient.getCommentsByGoodsId(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null) {
                    callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
                } else if (bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE).getAsString());
                } else {
                    JsonArray array = bodyJson.get(Const.RESULT).getAsJsonArray();
                    List<Comment> comments = new ArrayList<>();
                    Gson gson = new GsonBuilder().setLenient().create();
                    for (int i = 0; i < array.size(); i++) {
                        Comment comment = gson.fromJson(array.get(i), Comment.class);
                        comments.add(comment);
                    }
                    callback.onCommentsLoaded(comments);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取商品评论请求出现异常", t);
                callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
            }
        });
    }
}

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

import me.zoro.peachgardenmall.api.OrderClient;
import me.zoro.peachgardenmall.api.ServiceGenerator;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.OrderDatasource;
import me.zoro.peachgardenmall.datasource.domain.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dengfengdecao on 17/5/10.
 */

public class OrderRemoteDatasource implements OrderDatasource {
    private static final String TAG = "OrderRemoteDatasource";
    private OrderClient mOrderClient;

    public static OrderRemoteDatasource sOrderRemoteDatasource;

    private OrderRemoteDatasource(Context context) {
        mOrderClient = ServiceGenerator.createService(context, OrderClient.class);
    }

    public static OrderRemoteDatasource getInstance(Context context) {
        if (sOrderRemoteDatasource == null) {
            sOrderRemoteDatasource = new OrderRemoteDatasource(context);
        }

        return sOrderRemoteDatasource;
    }

    @Override
    public void getSignedOrderInfo(Map<String, Object> reqParams, @NonNull GetSignedOrderInfoCallback callback) {

    }

    @Override
    public void getOrders(final Map<String, Integer> reqParams, @NonNull final GetOrdersCallback callback) {
        Call<JsonObject> call = mOrderClient.getOrders(reqParams);
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
                    ArrayList<Order> orders = new ArrayList<Order>();
                    JsonArray resultJson = bodyJson.get(Const.RESULT).getAsJsonArray();
                    for (int i = 0; i < resultJson.size(); i++) {
                        Order order = gson.fromJson(resultJson.get(i), Order.class);
                        orders.add(order);
                    }

                    callback.onOrdersLoaded(orders);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 根据用户id分页获取用户订单列表时服务器异常", t);
                callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
            }
        });
    }

    @Override
    public void getOrderDetail(Map<String, Object> reqParams, @NonNull final GetOrderCallback callback) {
        Call<JsonObject> call = mOrderClient.getOrder(reqParams);
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
                    Order order = gson.fromJson(resultJson, Order.class);
                    callback.onOrderLoaded(order);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取订单详情时服务器异常", t);
                callback.onDataNotAvailable(Const.SERVER_UNAVAILABLE);
            }
        });
    }

    @Override
    public void updateOrderStatus(final Map<String, Object> reqParams, @NonNull final UpdateOrderStatusCallback callback) {
        Call<JsonObject> call = mOrderClient.updateOrderStatus(reqParams);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null) {
                    callback.onUpdateFailure(Const.SERVER_UNAVAILABLE);
                } else if (bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onUpdateFailure(bodyJson.get(Const.MESSAGE).getAsString());
                } else {
                    callback.onUpdateSuccess();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 更新订单状态时服务器异常", t);
                callback.onUpdateFailure(Const.SERVER_UNAVAILABLE);
            }
        });
    }

    @Override
    public void evaluateGoodses(Map<String, Object> reqParams, @NonNull final EvaluateGoodsesCallback callback) {
        Call<JsonObject> call = mOrderClient.evaluateGoodses(reqParams);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null) {
                    callback.onEvaluateFailure(Const.SERVER_UNAVAILABLE);
                } else if (bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onEvaluateFailure(bodyJson.get(Const.MESSAGE).getAsString());
                } else {
                    callback.onEvaluateSuccess(bodyJson.get(Const.MESSAGE).getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 订单商品评论时服务器异常", t);
                callback.onEvaluateFailure(Const.SERVER_UNAVAILABLE);

            }
        });
    }
}

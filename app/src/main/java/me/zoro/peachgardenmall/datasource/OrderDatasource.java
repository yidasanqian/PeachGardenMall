package me.zoro.peachgardenmall.datasource;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.Order;

/**
 * Created by dengfengdecao on 17/5/10.
 */

public interface OrderDatasource {


    void getSignedOrderInfo(Map<String, Object> reqParams, @NonNull GetSignedOrderInfoCallback callback);

    void getOrders(Map<String, Integer> reqParams, @NonNull GetOrdersCallback callback);

    void getOrderDetail(Map<String, Object> reqParams, @NonNull GetOrderCallback callback);

    void updateOrderStatus(Map<String, Object> reqParams, @NonNull UpdateOrderStatusCallback callback);

    void evaluateGoodses(Map<String, Object> reqParams, @NonNull EvaluateGoodsesCallback callback);

    interface EvaluateGoodsesCallback {
        void onEvaluateSuccess(String msg);

        void onEvaluateFailure(String msg);
    }

    interface UpdateOrderStatusCallback {
        void onUpdateSuccess();

        void onUpdateFailure(String msg);
    }

    interface GetOrderCallback {
        void onOrderLoaded(Order order);

        void onDataNotAvailable(String msg);
    }
    interface GetOrdersCallback {
        void onOrdersLoaded(ArrayList<Order> orders);

        void onDataNotAvailable(String msg);
    }

    interface GetSignedOrderInfoCallback {
        void onSignedOrderInfo(String signOrderInfo);

        void onSignedFailure(String msg);
    }
}

package me.zoro.peachgardenmall.datasource;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.Order;

/**
 * Created by dengfengdecao on 17/5/10.
 */

public class OrderRepository implements OrderDatasource {

    private static OrderRepository sOrderRepository;

    private OrderDatasource mRemoteOrderDatasource;

    private OrderRepository(OrderDatasource remoteOrderDatasource) {
        mRemoteOrderDatasource = remoteOrderDatasource;
    }

    public static OrderRepository getInstance(OrderDatasource remoteOrderDatasource) {
        if (sOrderRepository == null) {
            sOrderRepository = new OrderRepository(remoteOrderDatasource);
        }

        return sOrderRepository;
    }

    @Override
    public void getSignedOrderInfo(Map<String, Object> reqParams, @NonNull GetSignedOrderInfoCallback callback) {

    }

    @Override
    public void getOrders(Map<String, Integer> reqParams, @NonNull final GetOrdersCallback callback) {
        mRemoteOrderDatasource.getOrders(reqParams, new GetOrdersCallback() {
            @Override
            public void onOrdersLoaded(ArrayList<Order> orders) {
                callback.onOrdersLoaded(orders);
            }

            @Override
            public void onDataNotAvailable(String msg) {
                callback.onDataNotAvailable(msg);
            }
        });
    }
}

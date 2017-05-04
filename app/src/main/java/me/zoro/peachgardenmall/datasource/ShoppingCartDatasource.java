package me.zoro.peachgardenmall.datasource;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.Cart;

/**
 * Created by dengfengdecao on 17/5/4.
 */

public interface ShoppingCartDatasource {
    void addToShoppingCart(Map<String, Object> params, @NonNull AddToShoppingCartCallback callback);

    void deleteFromShoppingCart(Map<String, Object> params, @NonNull DeleteFromShoppingCartCallback callback);

    void getShoppingCartGoodses(Map<String, Integer> params, @NonNull GetShoppingCartGoodsesCallback callback);

    interface GetShoppingCartGoodsesCallback {
        void onGoodsesLoaded(List<Cart> carts);

        void onDataNotAvailable(String msg);
    }

    interface DeleteFromShoppingCartCallback {
        void onDeleteSuccess(String msg);

        void onDeleteFailure(String msg);
    }

    interface AddToShoppingCartCallback {
        void onAddSuccess(String msg);

        void onAddFailure(String msg);

    }
}

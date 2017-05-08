package me.zoro.peachgardenmall.datasource;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.Cart;

/**
 * Created by dengfengdecao on 17/5/4.
 */

public class ShoppingCartRepository implements ShoppingCartDatasource {

    public static ShoppingCartRepository sShoppingCartRepository;

    private ShoppingCartDatasource mRemoteDatasource;

    private ShoppingCartRepository(ShoppingCartDatasource remoteCartDatasource) {
        mRemoteDatasource = remoteCartDatasource;
    }

    public static ShoppingCartRepository getInstance(ShoppingCartDatasource remoteCartDatasource) {
        if (sShoppingCartRepository == null) {
            sShoppingCartRepository = new ShoppingCartRepository(remoteCartDatasource);
        }

        return sShoppingCartRepository;
    }

    @Override
    public void addToShoppingCart(Map<String, Object> params, @NonNull final AddToShoppingCartCallback callback) {
        mRemoteDatasource.addToShoppingCart(params, new AddToShoppingCartCallback() {
            @Override
            public void onAddSuccess(String msg) {
                callback.onAddSuccess(msg);
            }

            @Override
            public void onAddFailure(String errorMsg) {
                callback.onAddFailure(errorMsg);
            }
        });
    }

    @Override
    public void getShoppingCartGoodses(Map<String, Integer> params, @NonNull final GetShoppingCartGoodsesCallback callback) {
        mRemoteDatasource.getShoppingCartGoodses(params, new GetShoppingCartGoodsesCallback() {
            @Override
            public void onGoodsesLoaded(ArrayList<Cart> carts) {
                callback.onGoodsesLoaded(carts);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                callback.onDataNotAvailable(errorMsg);
            }
        });
    }

    @Override
    public void deleteFromShoppingCart(Map<String, Object> params, @NonNull final DeleteFromShoppingCartCallback callback) {
        mRemoteDatasource.deleteFromShoppingCart(params, new DeleteFromShoppingCartCallback() {
            @Override
            public void onDeleteSuccess(String msg) {
                callback.onDeleteSuccess(msg);
            }

            @Override
            public void onDeleteFailure(String msg) {
                callback.onDeleteFailure(msg);
            }
        });
    }
}

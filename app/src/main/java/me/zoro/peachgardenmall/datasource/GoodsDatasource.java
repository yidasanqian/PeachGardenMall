package me.zoro.peachgardenmall.datasource;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.domain.GoodsCategory;

/**
 * Created by dengfengdecao on 17/4/17.
 */

public interface GoodsDatasource {

    void getGoodses(Map<String, Object> params, @NonNull GetGoodsesCallback callback);

    void getGoodsCategories(Map<String, Object> params, @NonNull GetGoodsCategoriesCallback callback);

    void searchGoodses(Map<String, Object> params, @NonNull SearchGoodsesCallback callback);

    void getGoodsDetail(int goodsId, @NonNull GetGoodsDetailCallback callback);

    void getStarGoodses(Map<String, Object> params, @NonNull GetStarGoodsesCallback callback);

    void starGoods(Map<String, Object> params, @NonNull StarGoodsCallback callback);

    void addToShoppingCart(Map<String, Object> params, @NonNull AddToShoppingCartCallback callback);

    void deleteFromShoppingCart(Map<String, Object> params, @NonNull DeleteFromShoppingCartCallback callback);

    interface DeleteFromShoppingCartCallback {
        void onDeleteSuccess();

        void onDeleteFailure(String errorMsg);
    }

    interface AddToShoppingCartCallback {
        void onAddSuccess();

        void onAddFailure(String errorMsg);

    }

    interface StarGoodsCallback {
        void onStarSuccess();

        void onStarFailure(String errorMsg);
    }

    interface GetStarGoodsesCallback {
        void onGoodsesLoaded(ArrayList<Goods> goodses);

        void onDataNotAvailable(String errorMsg);
    }

    interface GetGoodsDetailCallback {
        void onGoodsLoaded(Goods goods);

        void onDataNotAvailable(String errorMsg);
    }

    interface GetGoodsesCallback {
        void onGoodsesLoaded(ArrayList<Goods> goodses);

        void onDataNotAvailable(String errorMsg);
    }

    interface GetGoodsCategoriesCallback {
        void onGoodsCategoriesLoaded(List<GoodsCategory> goodsCategories);

        void onDataNotAvailable(String errorMsg);
    }

    interface SearchGoodsesCallback {
        void onSearchSucces(ArrayList<Goods> goodses);

        void onSearchFailure(String msg);
    }
}

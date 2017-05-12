package me.zoro.peachgardenmall.datasource;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.Comment;
import me.zoro.peachgardenmall.datasource.domain.Freight;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.domain.GoodsCategory;
import me.zoro.peachgardenmall.datasource.domain.Promotion;

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

    void getIsStar(Map<String, Integer> params, @NonNull GetIsStarCallback callback);

    void getFreight(@NonNull GetFreightCallback callback);

    void getPromotion(@NonNull GetPromotionsCallback callback);

    void getCommentByGoodsId(Map<String, Integer> params, @NonNull GetCommentsCallback callback);

    interface GetCommentsCallback {
        void onCommentsLoaded(List<Comment> comments);

        void onDataNotAvailable(String msg);

    }

    interface GetPromotionsCallback {
        void onPromotionsLoaded(ArrayList<Promotion> promotions);

        void onDataNotAvailable(String msg);

    }

    interface GetFreightCallback {
        void onFreightLoaded(Freight freight);

        void onDataNotAvailable(String msg);
    }
    interface GetIsStarCallback {
        void onSuccess(boolean isStar);

        void onFaillure(String msg);
    }


    interface StarGoodsCallback {
        void onStarSuccess(String msg);

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

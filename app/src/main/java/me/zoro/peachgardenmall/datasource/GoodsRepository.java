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

public class GoodsRepository implements GoodsDatasource {

    private static GoodsRepository sRepository;

    private GoodsDatasource mRemoteDatasource;

    private GoodsRepository(GoodsDatasource remoteDatasource) {
        mRemoteDatasource = remoteDatasource;
    }

    public static GoodsRepository getInstance(GoodsDatasource remoteDatasource) {
        if (sRepository == null) {
            sRepository = new GoodsRepository(remoteDatasource);
        }

        return sRepository;
    }

    @Override
    public void getGoodses(Map<String, Object> params, @NonNull final GetGoodsesCallback callback) {
        mRemoteDatasource.getGoodses(params, new GetGoodsesCallback() {
            @Override
            public void onGoodsesLoaded(ArrayList<Goods> goodses) {
                callback.onGoodsesLoaded(goodses);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                callback.onDataNotAvailable(errorMsg);
            }
        });
    }

    @Override
    public void getGoodsCategories(Map<String, Object> params, @NonNull final GetGoodsCategoriesCallback callback) {
        mRemoteDatasource.getGoodsCategories(params, new GetGoodsCategoriesCallback() {
            @Override
            public void onGoodsCategoriesLoaded(List<GoodsCategory> goodsCategories) {
                callback.onGoodsCategoriesLoaded(goodsCategories);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                callback.onDataNotAvailable(errorMsg);
            }
        });
    }

    @Override
    public void searchGoodses(Map<String, Object> params, @NonNull final SearchGoodsesCallback callback) {
        mRemoteDatasource.searchGoodses(params, new SearchGoodsesCallback() {
            @Override
            public void onSearchSucces(ArrayList<Goods> goodses) {
                callback.onSearchSucces(goodses);
            }

            @Override
            public void onSearchFailure(String msg) {
                callback.onSearchFailure(msg);
            }
        });
    }

    @Override
    public void getGoodsDetail(int goodsId, @NonNull final GetGoodsDetailCallback callback) {
        mRemoteDatasource.getGoodsDetail(goodsId, new GetGoodsDetailCallback() {
            @Override
            public void onGoodsLoaded(Goods goods) {
                callback.onGoodsLoaded(goods);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                callback.onDataNotAvailable(errorMsg);
            }
        });
    }

    @Override
    public void getStarGoodses(Map<String, Object> params, @NonNull final GetStarGoodsesCallback callback) {
        mRemoteDatasource.getStarGoodses(params, new GetStarGoodsesCallback() {
            @Override
            public void onGoodsesLoaded(ArrayList<Goods> goodses) {
                callback.onGoodsesLoaded(goodses);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                callback.onDataNotAvailable(errorMsg);
            }
        });
    }

    @Override
    public void starGoods(Map<String, Object> params, @NonNull final StarGoodsCallback callback) {
        mRemoteDatasource.starGoods(params, new StarGoodsCallback() {
            @Override
            public void onStarSuccess(String msg) {
                callback.onStarSuccess(msg);
            }

            @Override
            public void onStarFailure(String errorMsg) {
                callback.onStarFailure(errorMsg);
            }
        });
    }

    @Override
    public void getIsStar(Map<String, Integer> params, @NonNull final GetIsStarCallback callback) {
        mRemoteDatasource.getIsStar(params, new GetIsStarCallback() {
            @Override
            public void onSuccess(boolean isStar) {
                callback.onSuccess(isStar);
            }

            @Override
            public void onFaillure(String msg) {
                callback.onFaillure(msg);
            }
        });
    }

    @Override
    public void getFreight(@NonNull final GetFreightCallback callback) {
        mRemoteDatasource.getFreight(new GetFreightCallback() {
            @Override
            public void onFreightLoaded(Freight freight) {
                callback.onFreightLoaded(freight);
            }

            @Override
            public void onDataNotAvailable(String msg) {
                callback.onDataNotAvailable(msg);
            }
        });
    }

    @Override
    public void getPromotion(@NonNull final GetPromotionsCallback callback) {
        mRemoteDatasource.getPromotion(new GetPromotionsCallback() {
            @Override
            public void onPromotionsLoaded(ArrayList<Promotion> promotions) {
                callback.onPromotionsLoaded(promotions);
            }

            @Override
            public void onDataNotAvailable(String msg) {
                callback.onDataNotAvailable(msg);
            }
        });
    }

    @Override
    public void getCommentByGoodsId(Map<String, Integer> params, @NonNull final GetCommentsCallback callback) {
        mRemoteDatasource.getCommentByGoodsId(params, new GetCommentsCallback() {
            @Override
            public void onCommentsLoaded(List<Comment> comments) {
                callback.onCommentsLoaded(comments);
            }

            @Override
            public void onDataNotAvailable(String msg) {
                callback.onDataNotAvailable(msg);
            }
        });
    }
}

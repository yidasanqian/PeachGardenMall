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
            public void onStarSuccess() {
                callback.onStarSuccess();
            }

            @Override
            public void onStarFailure(String errorMsg) {
                callback.onStarFailure(errorMsg);
            }
        });
    }

    @Override
    public void addToShoppingCart(Map<String, Object> params, @NonNull AddToShoppingCartCallback callback) {

    }

    @Override
    public void deleteFromShoppingCart(Map<String, Object> params, @NonNull DeleteFromShoppingCartCallback callback) {

    }
}

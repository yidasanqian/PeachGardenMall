package me.zoro.peachgardenmall.datasource;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.Goods;

/**
 * Created by dengfengdecao on 17/4/17.
 */

public interface GoodsDatasource {

    void getGoodses(Map<String, Object> params, @NonNull GetGoodsesCallback callback);

    interface GetGoodsesCallback {
        void onGoodsesLoaded(List<Goods> goodses);

        void onDataNotAvailable(String errorMsg);
    }
}

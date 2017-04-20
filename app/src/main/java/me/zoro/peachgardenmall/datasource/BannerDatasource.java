package me.zoro.peachgardenmall.datasource;

import android.support.annotation.NonNull;

import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.BannerInfo;

/**
 * Created by dengfengdecao on 17/4/20.
 */

public interface BannerDatasource {

    void getBannerInfo(Map<String, Object> params, @NonNull GetBannerInfoCallback callback);

    interface GetBannerInfoCallback {
        void onBannerLoaded(BannerInfo bannerInfo);

        void onDataNotAvoidable(String errorMsg);
    }
}

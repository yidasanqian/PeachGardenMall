package me.zoro.peachgardenmall.datasource;

import android.support.annotation.NonNull;

import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.BannerInfo;

/**
 * Created by dengfengdecao on 17/4/20.
 */

public class BannerRepository implements BannerDatasource {

    private static BannerRepository sBannerRepository;

    private BannerDatasource mRemoteDatasource;

    private BannerRepository(BannerDatasource remoteDatasource) {
        mRemoteDatasource = remoteDatasource;
    }

    public static BannerRepository getInstance(BannerDatasource remoteDatasource) {
        if (sBannerRepository == null) {
            sBannerRepository = new BannerRepository(remoteDatasource);
        }

        return sBannerRepository;
    }

    @Override
    public void getBannerInfo(Map<String, Object> params, @NonNull final GetBannerInfoCallback callback) {
        mRemoteDatasource.getBannerInfo(params, new GetBannerInfoCallback() {
            @Override
            public void onBannerLoaded(BannerInfo bannerInfo) {
                callback.onBannerLoaded(bannerInfo);
            }

            @Override
            public void onDataNotAvoidable(String errorMsg) {
                callback.onDataNotAvoidable(errorMsg);
            }
        });
    }
}

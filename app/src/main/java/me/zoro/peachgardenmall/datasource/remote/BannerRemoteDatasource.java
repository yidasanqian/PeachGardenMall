package me.zoro.peachgardenmall.datasource.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.Map;

import me.zoro.peachgardenmall.api.BannerClient;
import me.zoro.peachgardenmall.api.ServiceGenerator;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.BannerDatasource;
import me.zoro.peachgardenmall.datasource.domain.BannerInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dengfengdecao on 17/4/20.
 */

public class BannerRemoteDatasource implements BannerDatasource {

    private static final String TAG = "BannerRemoteDatasource";
    private BannerClient mClient;

    private static BannerRemoteDatasource sRemoteDatasource;

    public BannerRemoteDatasource(Context context) {
        mClient = ServiceGenerator.createService(context, BannerClient.class);
    }

    public static BannerRemoteDatasource getInstance(Context context) {
        if (sRemoteDatasource == null) {
            sRemoteDatasource = new BannerRemoteDatasource(context);
        }

        return sRemoteDatasource;
    }

    @Override
    public void getBannerInfo(Map<String, Object> params, @NonNull final GetBannerInfoCallback callback) {
        Call<JsonObject> call = mClient.getBannerInfo(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int code = bodyJson.get(Const.CODE).getAsInt();
                    if (code == 0) {
                        Gson gson = new GsonBuilder().setLenient().create();
                        BannerInfo bannerInfo = gson.fromJson(bodyJson.get(Const.RESULT), BannerInfo.class);
                        callback.onBannerLoaded(bannerInfo);
                    } else {
                        callback.onDataNotAvoidable(bodyJson.get(Const.MESSAGE).getAsString());
                    }
                } else {
                    callback.onDataNotAvoidable(Const.SERVER_AVALIABLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取广告异常", t);
                callback.onDataNotAvoidable(Const.SERVER_AVALIABLE);
            }
        });
    }
}

package me.zoro.peachgardenmall.datasource.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.zoro.peachgardenmall.api.AddressClient;
import me.zoro.peachgardenmall.api.ServiceGenerator;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.AddressDatasource;
import me.zoro.peachgardenmall.datasource.domain.Address;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dengfengdecao on 17/4/24.
 */

public class AddressRemoteDatasource implements AddressDatasource {

    private static final String TAG = "AddressRemoteDatasource";

    private AddressClient mAddressClient;

    private static AddressRemoteDatasource sAddressRemoteDatasource;

    public AddressRemoteDatasource(Context context) {
        mAddressClient = ServiceGenerator.createService(context, AddressClient.class);
    }

    public static AddressRemoteDatasource getInstance(Context context) {
        if (sAddressRemoteDatasource == null) {
            sAddressRemoteDatasource = new AddressRemoteDatasource(context);
        }

        return sAddressRemoteDatasource;
    }

    @Override
    public void save(Map<String, Object> params, @NonNull final AddCallback callback) {
        Call<JsonObject> call = mAddressClient.save(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null) {
                    callback.onSavedFailure(Const.SERVER_AVALIABLE);
                } else if (bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onSavedFailure(bodyJson.get(Const.MESSAGE).getAsString());
                } else {
                    callback.onSavedSuccess();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 保存地址异常", t);
                callback.onSavedFailure(Const.SERVER_AVALIABLE);
            }
        });
    }

    @Override
    public void get(Map<String, Object> params, @NonNull final GetCallback callback) {
        Call<JsonObject> call = mAddressClient.get(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null || bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onDataNotAvoidable();
                } else {
                    List<Address> addresses = new ArrayList<Address>();
                    JsonArray jsonArray = bodyJson.get(Const.RESULT).getAsJsonArray();
                    Gson gson = new GsonBuilder().setLenient().create();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Address address = gson.fromJson(jsonArray.get(i), Address.class);
                        addresses.add(address);
                    }
                    callback.onAddressesLoaded(addresses);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取地址异常", t);
                callback.onDataNotAvoidable();
            }
        });
    }

    @Override
    public void setupDefault(Map<String, Object> params, @NonNull final SetupDefaultCallback callback) {
        Call<JsonObject> call = mAddressClient.setUpDefault(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null || bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onSetupFailure();
                } else {
                    callback.onSetupSuccess();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 设置默认地址异常", t);
                callback.onSetupFailure();

            }
        });
    }

    @Override
    public void getById(Map<String, Integer> params, @NonNull final GetByIdCallback callback) {
        Call<JsonObject> call = mAddressClient.getById(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson == null || bodyJson.get(Const.CODE).getAsInt() != 0) {
                    callback.onDataNotAvoidable();
                } else {
                    Gson gson = new GsonBuilder().setLenient().create();
                    Address address = gson.fromJson(bodyJson.get(Const.RESULT), Address.class);
                    callback.onAddressLoaded(address);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 根据id获取地址异常", t);
                callback.onDataNotAvoidable();
            }
        });
    }
}

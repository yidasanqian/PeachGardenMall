package me.zoro.peachgardenmall.datasource;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.Address;

/**
 * Created by dengfengdecao on 17/4/24.
 */

public interface AddressDatasource {

    void save(Map<String, Object> params, @NonNull AddCallback callback);

    void get(Map<String, Object> params, @NonNull GetCallback callback);

    void setupDefault(Map<String, Object> params, @NonNull SetupDefaultCallback callback);

    void getById(int addrId, @NonNull GetByIdCallback callback);

    interface GetByIdCallback {
        void onAddressLoaded(Address address);

        void onDataNotAvoidable();
    }
    interface AddCallback {
        void onSavedSuccess();

        void onSavedFailure(String msg);
    }

    interface GetCallback {
        void onAddressesLoaded(List<Address> addresses);

        void onDataNotAvoidable();
    }

    interface SetupDefaultCallback {
        void onSetupSuccess();

        void onSetupFailure();
    }
}

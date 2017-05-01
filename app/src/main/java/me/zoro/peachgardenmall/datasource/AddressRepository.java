package me.zoro.peachgardenmall.datasource;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.Address;

/**
 * Created by dengfengdecao on 17/4/24.
 */

public class AddressRepository implements AddressDatasource {

    private AddressDatasource mRemoteDatasource;

    private static AddressRepository sAddressRepository;

    private AddressRepository(AddressDatasource remoteDatasource) {
        mRemoteDatasource = remoteDatasource;
    }

    public static AddressRepository getInstance(AddressDatasource remoteDatasource) {
        if (sAddressRepository == null) {
            sAddressRepository = new AddressRepository(remoteDatasource);
        }

        return sAddressRepository;
    }


    @Override
    public void save(Map<String, Object> params, @NonNull final AddCallback callback) {
        mRemoteDatasource.save(params, new AddCallback() {
            @Override
            public void onSavedSuccess() {
                callback.onSavedSuccess();
            }

            @Override
            public void onSavedFailure(String msg) {
                callback.onSavedFailure(msg);
            }
        });
    }

    @Override
    public void get(Map<String, Object> params, @NonNull final GetCallback callback) {
        mRemoteDatasource.get(params, new GetCallback() {
            @Override
            public void onAddressesLoaded(List<Address> addresses) {
                callback.onAddressesLoaded(addresses);
            }

            @Override
            public void onDataNotAvoidable() {
                callback.onDataNotAvoidable();
            }
        });
    }

    @Override
    public void setupDefault(Map<String, Object> params, @NonNull final SetupDefaultCallback callback) {
        mRemoteDatasource.setupDefault(params, new SetupDefaultCallback() {
            @Override
            public void onSetupSuccess() {
                callback.onSetupSuccess();
            }

            @Override
            public void onSetupFailure() {
                callback.onSetupFailure();
            }
        });
    }

    @Override
    public void getById(int addrId, @NonNull final GetByIdCallback callback) {
        mRemoteDatasource.getById(addrId, new GetByIdCallback() {
            @Override
            public void onAddressLoaded(Address address) {
                callback.onAddressLoaded(address);
            }

            @Override
            public void onDataNotAvoidable() {
                callback.onDataNotAvoidable();
            }
        });
    }
}

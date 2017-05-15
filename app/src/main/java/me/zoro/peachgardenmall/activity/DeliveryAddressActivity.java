package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.AddressRecyclerViewAdapter;
import me.zoro.peachgardenmall.datasource.AddressDatasource;
import me.zoro.peachgardenmall.datasource.AddressRepository;
import me.zoro.peachgardenmall.datasource.domain.Address;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.AddressRemoteDatasource;
import me.zoro.peachgardenmall.utils.CacheManager;

/**
 * Created by dengfengdecao on 17/4/22.
 */

public class DeliveryAddressActivity extends AppCompatActivity {

    private static final String TAG = "DeliveryAddressActivity";
    public static final int CREATE_ADDRESS_REQUEST = 1;
    public static final String DEFAULT_ADDRESS_EXTRA = "default_address";
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_create_address)
    Button mBtnCreateAddress;

    private AddressRepository mAddressRepository;

    private List<Address> mAddresses;
    private Address mDefaultAddr;
    private AddressRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mAddressRepository = AddressRepository.getInstance(AddressRemoteDatasource.getInstance(
                getApplicationContext())
        );

        mAddresses = new ArrayList<>();
        mRecyclerViewAdapter = new AddressRecyclerViewAdapter(mAddresses);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        boolean isSelected = getIntent().getBooleanExtra(CreateOrderActivity.SELECTED_CONTACT_ADDRESS, false);
        // 如果条件为true，表示从订单页面选择发货地址
        if (isSelected) {
            mRecyclerViewAdapter.setOnItemClickListener(new AddressRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, Address address) {
                    Intent data = new Intent();
                    data.putExtra(DEFAULT_ADDRESS_EXTRA, address);
                    setResult(RESULT_OK, data);
                    finish();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchAddressesTask().execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
       /* if (mDefaultAddr != null) {
            Intent data = new Intent();
            data.putExtra(DEFAULT_ADDRESS_EXTRA, mDefaultAddr);
            setResult(RESULT_OK, data);
        } else {
            setResult(RESULT_CANCELED);
        }*/
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @OnClick(R.id.btn_create_address)
    public void onViewClicked() {
        Intent intent = new Intent(this, CreateAddressActivity.class);
        startActivityForResult(intent, CREATE_ADDRESS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_ADDRESS_REQUEST && resultCode == RESULT_OK) {
            new FetchAddressesTask().execute();
        }
    }

    private class FetchAddressesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            UserInfo userInfo = CacheManager.getUserInfoFromCache(DeliveryAddressActivity.this);

            Map<String, Object> map = new HashMap<>();
            map.put("userId", userInfo.getUserId());
            mAddressRepository.get(map, new AddressDatasource.GetCallback() {
                @Override
                public void onAddressesLoaded(List<Address> addresses) {
                    int size = addresses.size();
                    if (size > 0) {
                        mRecyclerViewAdapter.replaceData(addresses);
                    }

                    for (int i = 0; i < size; i++) {
                        Address address = addresses.get(i);
                        if (address.isIsDefault()) {
                            mDefaultAddr = address;
                        }
                    }
                }

                @Override
                public void onDataNotAvoidable() {
                    showMessage(getString(R.string.data_not_avoidable));
                }
            });

            return null;
        }
    }

    private void showMessage(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}

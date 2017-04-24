package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.AddressRecyclerViewAdapter;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.AddressDatasource;
import me.zoro.peachgardenmall.datasource.AddressRepository;
import me.zoro.peachgardenmall.datasource.domain.Address;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.AddressRemoteDatasource;
import me.zoro.peachgardenmall.utils.CacheManager;

/**
 * Created by dengfengdecao on 17/4/10.
 */

public class CreateAddressActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "CreateAddressActivity";

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_right_txt)
    TextView mToolbarRightTxt;
    @BindView(R.id.name_et)
    EditText mNameEt;
    @BindView(R.id.phone_et)
    EditText mPhoneEt;
    @BindView(R.id.address_et)
    EditText mAddressEt;
    @BindView(R.id.detail_address_et)
    EditText mDetailAddressEt;
    @BindView(R.id.cb_set_default_addr)
    CheckBox mCbSetDefaultAddr;

    private AddressRepository mAddressRepository;
    /**
     * 是否设置为默认地址
     */
    private boolean mIsDefault;

    /**
     * 要修改的地址
     */
    private Address mAddress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_address);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mAddressRepository = AddressRepository.getInstance(AddressRemoteDatasource.getInstance(getApplicationContext()));

        mCbSetDefaultAddr.setOnCheckedChangeListener(this);

        mAddress = (Address) getIntent().getSerializableExtra(AddressRecyclerViewAdapter.ADDRESS_EXTRA);
        if (mAddress != null) {
            mNameEt.setText(mAddress.getConsignee());
            mPhoneEt.setText(mAddress.getMobile());
            String[] addr = mAddress.getAddress().split(",");
            mAddressEt.setText(addr[0]);
            mDetailAddressEt.setText(addr[1]);
            mCbSetDefaultAddr.setChecked(mAddress.isIsDefault());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @OnClick(R.id.toolbar_right_txt)
    public void onViewClicked() {
        // TODO: 17/4/10 保存地址
        UserInfo userInfo = (UserInfo) CacheManager.getInstance().get(Const.USER_INFO_CACHE_KEY);
        if (userInfo == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            String name = mNameEt.getText().toString().trim();
            String phone = mPhoneEt.getText().toString().trim();
            String address = mAddressEt.getText().toString().trim();
            String detailAddr = mDetailAddressEt.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                mNameEt.setError(getString(R.string.empty_value_msg));
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                mPhoneEt.setError(getString(R.string.empty_value_msg));
                return;
            }

            if (TextUtils.isEmpty(address)) {
                mAddressEt.setError(getString(R.string.empty_value_msg));
                return;
            }

            if (TextUtils.isEmpty(detailAddr)) {
                mDetailAddressEt.setError(getString(R.string.empty_value_msg));
                return;
            }

            Map<String, Object> params = new HashMap<>();
            // 如果参数包含字段addressId，则表示修改地址
            if (mAddress != null) {
                params.put("addressId", mAddress.getId());
            }
            params.put("userId", userInfo.getUserId());
            params.put("name", name);
            params.put("phone", phone);
            params.put("address", address + "," + detailAddr);
            params.put("isDefault", mIsDefault);
            new SavedAddrTask().execute(params);
        }
        Log.d(TAG, "onViewClicked: userinfo ==> " + userInfo);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mIsDefault = isChecked;
        Log.d(TAG, "onCheckedChanged: mIsDefault ==> " + mIsDefault);
    }

    private class SavedAddrTask extends AsyncTask<Map<String, Object>, Void, Void> {
        @Override
        protected Void doInBackground(Map<String, Object>... params) {
            Map<String, Object> map = params[0];
            mAddressRepository.save(map, new AddressDatasource.AddCallback() {
                @Override
                public void onSavedSuccess() {
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onSavedFailure(String msg) {
                    showMessage(msg);
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

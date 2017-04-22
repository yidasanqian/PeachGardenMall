package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
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

    /**
     * 是否设置为默认地址
     */
    private boolean mIsDefault;

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

        mCbSetDefaultAddr.setOnCheckedChangeListener(this);
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
        Log.d(TAG, "onViewClicked: userinfo ==> " + userInfo);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mIsDefault = isChecked;
        Log.d(TAG, "onCheckedChanged: mIsDefault ==> " + mIsDefault);
    }
}

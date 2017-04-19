package me.zoro.peachgardenmall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.UserDatasource;
import me.zoro.peachgardenmall.datasource.UserRepository;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.UserRemoteDatasource;
import me.zoro.peachgardenmall.fragment.MyFragment;
import me.zoro.peachgardenmall.utils.CacheManager;
import me.zoro.peachgardenmall.utils.PreferencesUtil;
import me.zoro.peachgardenmall.view.RichText;

/**
 * Created by dengfengdecao on 17/4/10.
 */

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.account_setting)
    RelativeLayout mAccountSetting;
    @BindView(R.id.clear_cache)
    RelativeLayout mClearCache;
    @BindView(R.id.order)
    RelativeLayout mOrder;
    @BindView(R.id.delivery_address)
    RelativeLayout mDeliveryAddress;
    @BindView(R.id.coupon)
    RelativeLayout mCoupon;
    @BindView(R.id.common_question)
    RelativeLayout mCommonQuestion;
    @BindView(R.id.about)
    RelativeLayout mAbout;
    @BindView(R.id.logout_btn)
    Button mLogoutBtn;
    @BindView(R.id.tv_cache_size)
    RichText mTvCacheSize;

    private UserRepository mUserRepository;

    private UserInfo mUserInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mUserRepository = UserRepository.getInstance(UserRemoteDatasource.getInstance(getApplicationContext()));


        if (getIntent() != null) {
            mUserInfo = (UserInfo) getIntent().getSerializableExtra(MyFragment.USERINFO_EXTRA);
        }

        if (mUserInfo == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        String cacheSize = CacheManager.getInstance().getTotalCacheSize(getApplicationContext());
        Log.d(TAG, "onCreate: 缓存大小" + cacheSize);
        mTvCacheSize.setText(cacheSize);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @OnClick({R.id.account_setting, R.id.clear_cache, R.id.order, R.id.delivery_address, R.id.coupon,
            R.id.common_question, R.id.about, R.id.logout_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.account_setting:
                Intent intent = new Intent(this, AccountSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.clear_cache:
                clearCache();
                break;
            case R.id.order:
                intent = new Intent(this, MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.delivery_address:
                break;
            case R.id.coupon:
                intent = new Intent(this, CouponActivity.class);
                startActivity(intent);
                break;
            case R.id.common_question:
                intent = new Intent(this, CommonQuestionActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.logout_btn:
                mUserRepository.logout(mUserInfo.getUserId(), new UserDatasource.LogoutCallback() {
                    @Override
                    public void onLogout() {
                        PreferencesUtil.getDefaultPreferences(SettingsActivity.this, Const.PREF_TOKEN)
                                .edit()
                                .clear()
                                .apply();
                        PreferencesUtil.getDefaultPreferences(SettingsActivity.this, Const.PREF_USER_INFO)
                                .edit()
                                .clear()
                                .apply();
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                break;
        }
    }

    private void clearCache() {
        new AlertDialog.Builder(this)
                .setMessage("是否清除缓存")
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CacheManager.getInstance().clearCache(getApplicationContext());
                        String cacheSize = CacheManager.getInstance().getTotalCacheSize(getApplicationContext());
                        Log.d(TAG, "onClick: 清除缓存后，大小" + cacheSize);
                        mTvCacheSize.setText(cacheSize);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}

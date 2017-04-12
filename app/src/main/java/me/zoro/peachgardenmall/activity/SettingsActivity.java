package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;

/**
 * Created by dengfengdecao on 17/4/10.
 */

public class SettingsActivity extends AppCompatActivity {

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

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @OnClick({R.id.account_setting, R.id.clear_cache, R.id.order, R.id.delivery_address, R.id.coupon, R.id.common_question, R.id.about, R.id.logout_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.account_setting:
                Intent intent = new Intent(this, AccountSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.clear_cache:
                break;
            case R.id.order:
                intent = new Intent(this, MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.delivery_address:
                break;
            case R.id.coupon:
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
                break;
        }
    }
}

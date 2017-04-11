package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;

/**
 * Created by dengfengdecao on 17/4/10.
 */

public class CreateAddressActivity extends AppCompatActivity {

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
    @BindView(R.id.set_default_addr_rb)
    RadioButton mSetDefaultAddrRb;

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

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @OnClick(R.id.toolbar_right_txt)
    public void onViewClicked() {
        // TODO: 17/4/10 保存地址
    }
}

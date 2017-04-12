package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;

public class AccountSettingActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edit_password)
    RelativeLayout mEditPassword;
    @BindView(R.id.edit_phone)
    RelativeLayout mEditPhone;
    @BindView(R.id.edit_id_card)
    RelativeLayout mEditIdCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
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


    @OnClick({R.id.edit_password, R.id.edit_phone, R.id.edit_id_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // TODO: 17/4/12 账号设置
            case R.id.edit_password:
                break;
            case R.id.edit_phone:
                break;
            case R.id.edit_id_card:
                break;
        }
    }
}

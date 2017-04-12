package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.datasource.UserDatasource;
import me.zoro.peachgardenmall.datasource.UserRepository;
import me.zoro.peachgardenmall.datasource.remote.UserRemoteDatasource;

public class RegisterActivity extends AppCompatActivity {

    public static final String USERNAME_EXTRA = "username";
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_tel)
    TextInputEditText mEtTel;
    @BindView(R.id.et_password)
    TextInputEditText mEtPassword;
    @BindView(R.id.et_captcha)
    TextInputEditText mEtCaptcha;
    @BindView(R.id.btn_fetch_captcha)
    Button mBtnFetchCaptcha;
    @BindView(R.id.btn_login)
    Button mBtnRegister;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;

    private UserRepository mUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mUserRepository = UserRepository.getInstance(UserRemoteDatasource.getInstance(getApplicationContext()));
    }

    @OnClick({R.id.btn_fetch_captcha, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_fetch_captcha:
                // TODO: 17/4/11 获取验证码
                break;
            case R.id.btn_login:
                Map<String, Object> params = new HashMap<>();
                String phone = mEtTel.getText().toString();
                String password = mEtPassword.getText().toString();
                String captcha = mEtCaptcha.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    showMessage(getString(R.string.empty_phone_msg));
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showMessage(getString(R.string.empty_password_msg));
                    return;
                }
                if (TextUtils.isEmpty(captcha)) {
                    showMessage(getString(R.string.empty_captcha_msg));
                    return;
                }
                params.put("phone", phone);
                params.put("password", password);
                params.put("captcha", captcha);
                setLoadingIndicator(true);
                mUserRepository.registerNewUser(params, new UserDatasource.RegisterUserCallback() {
                    @Override
                    public void onRegisterSuccess(String username) {
                        setLoadingIndicator(false);
                        showMessage(getString(R.string.register_success_msg));

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra(USERNAME_EXTRA, username);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onRegisterFailure(String errorMsg) {
                        setLoadingIndicator(false);

                        showMessage(errorMsg);
                    }
                });
                break;
        }
    }

    private void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                //设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.register_progress_bar_title);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }

    private void showMessage(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}

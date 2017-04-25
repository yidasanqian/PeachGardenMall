package me.zoro.peachgardenmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
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
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.UserDatasource;
import me.zoro.peachgardenmall.datasource.UserRepository;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.UserRemoteDatasource;
import me.zoro.peachgardenmall.utils.CacheManager;
import me.zoro.peachgardenmall.utils.PreferencesUtil;

public class LoginActivity extends AppCompatActivity {

    public static final String USERINFO_EXTRA = "user_info";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_tel)
    TextInputEditText mEtTel;
    @BindView(R.id.et_password)
    TextInputEditText mEtPassword;
    @BindView(R.id.btn_login)
    Button mBtnRegister;
    @BindView(R.id.tv_forget_password)
    TextView mTvForgetPassword;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;

    private UserRepository mUserRepository;

    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        if (intent != null) {
            mUsername = intent.getStringExtra(RegisterActivity.USERNAME_EXTRA);
            mEtTel.setText(mUsername);
        }
        mUserRepository = UserRepository.getInstance(UserRemoteDatasource.getInstance(getApplicationContext()));

    }

    @OnClick({R.id.btn_login, R.id.tv_forget_password, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            // TODO: 17/4/11 忘记密码
            case R.id.tv_forget_password:
                break;
            case R.id.tv_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login() {
        Map<String, String> params = new HashMap<>();
        String phone = mEtTel.getText().toString();
        String password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            mEtTel.setError(getString(R.string.empty_phone_msg));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mEtPassword.setError(getString(R.string.empty_password_msg));
            return;
        }

        params.put("phone", phone);
        params.put("password", password);
        setLoadingIndicator(true);

        mUserRepository.login(params, new UserDatasource.LoginCallback() {
            @Override
            public void onLoginSuccess(UserInfo userInfo, String token) {
                setLoadingIndicator(false);
                showMessage(getString(R.string.login_success_msg));
                // 存入缓存
                CacheManager.getInstance().put(Const.USER_INFO_CACHE_KEY, userInfo);
                // 持久化token
                PreferencesUtil.persistentToken(LoginActivity.this, token);
                // 持久化用户信息
                PreferencesUtil.persistentUserInfo(LoginActivity.this, userInfo);
                // 设置返回的结果数据
                Intent data = new Intent(LoginActivity.this, MainActivity.class);
                data.putExtra(USERINFO_EXTRA, userInfo);
                data.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                setResult(Activity.RESULT_OK, data);
                startActivity(data);
                finish();
            }

            @Override
            public void onLoginFailure(String errorMsg) {
                setLoadingIndicator(false);
                showMessage(errorMsg);
            }
        });
    }


    private void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                //设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.login_progress_bar_title);
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

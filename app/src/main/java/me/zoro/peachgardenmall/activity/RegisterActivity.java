package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.datasource.UserRepository;
import me.zoro.peachgardenmall.datasource.remote.UserRemoteDatasource;

public class RegisterActivity extends AppCompatActivity {

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
    @BindView(R.id.btn_register)
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

    @OnClick({R.id.btn_fetch_captcha, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_fetch_captcha:
                // TODO: 17/4/11 获取验证码
                break;
            case R.id.btn_register:
                //mUserRepository.registerNewUser();
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

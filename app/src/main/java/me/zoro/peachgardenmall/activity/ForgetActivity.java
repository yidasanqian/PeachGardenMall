package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by dengfengdecao on 17/5/2.
 */

public class ForgetActivity extends AppCompatActivity {

    @BindView(R.id.et_tel)
    TextInputEditText mEtTel;
    @BindView(R.id.et_captcha)
    TextInputEditText mEtCaptcha;
    @BindView(R.id.btn_fetch_captcha)
    Button mBtnFetchCaptcha;
    @BindView(R.id.et_new_password)
    TextInputEditText mEtNewPassword;
    @BindView(R.id.btn_forget_submit)
    Button mBtnForgetSubmit;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private UserRepository mUserRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);

        mUserRepository = UserRepository.getInstance(UserRemoteDatasource.getInstance(
                getApplicationContext()
        ));
    }

    @OnClick({R.id.btn_fetch_captcha, R.id.btn_forget_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_fetch_captcha:
                String phone = mEtTel.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    mEtTel.setError(getString(R.string.empty_phone_msg));
                    return;
                }
                mUserRepository.fetchCaptcha(phone, new UserDatasource.GetCaptchaCallback() {
                    @Override
                    public void onFetchSuccess(String msg) {
                        showMessage(msg);
                    }

                    @Override
                    public void onFetchFailure(String msg) {
                        showMessage(msg);
                    }
                });
                break;
            case R.id.btn_forget_submit:
                phone = mEtTel.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    mEtTel.setError(getString(R.string.empty_phone_msg));
                    return;
                }
                String captcha = mEtCaptcha.getText().toString();
                if (TextUtils.isEmpty(captcha)) {
                    mEtCaptcha.setError(getString(R.string.empty_captcha_msg));
                    return;
                }
                String password = mEtNewPassword.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    mEtNewPassword.setError(getString(R.string.empty_password_msg));
                    return;
                }
                Map<String, Object> params = new HashMap<>();
                params.put("phone", phone);
                params.put("captcha", captcha);
                params.put("password", password);
                mUserRepository.forgetPassword(params, new UserDatasource.ForgetPasswordCallback() {
                    @Override
                    public void onForgetPasswordSuccess() {
                        showMessage(getString(R.string.success_modify_password_msg));
                    }

                    @Override
                    public void onForgetPasswordFailure(String errorMsg) {
                        showMessage(errorMsg);
                    }
                });
                break;
        }
    }

    private void showMessage(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}

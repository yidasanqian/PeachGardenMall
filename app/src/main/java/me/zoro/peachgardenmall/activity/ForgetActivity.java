package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_fetch_captcha, R.id.btn_forget_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_fetch_captcha:
                break;
            case R.id.btn_forget_submit:
                break;
        }
    }
}

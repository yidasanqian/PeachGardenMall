package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
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

public class EditIdCardActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_idcard)
    EditText mEtIdcard;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    private UserRepository mUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_idcard);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mUserRepository = UserRepository.getInstance(UserRemoteDatasource.getInstance(getApplicationContext()));

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        String idCard = mEtIdcard.getText().toString().trim();
        if (TextUtils.isEmpty(idCard)) {
            mEtIdcard.setError(getString(R.string.empty_idcard_msg));
            return;
        }

        UserInfo userInfo = (UserInfo) CacheManager.getInstance().get(Const.USER_INFO_CACHE_KEY);
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userInfo.getUserId());
        params.put("idCard", idCard);
        mUserRepository.changeIdCard(params, new UserDatasource.ChangeIdCardCallback() {
            @Override
            public void onChangeIdCardSuccess() {
                showMessage("身份证修改成功");
                finish();
            }

            @Override
            public void onChangeIdCardFailure(String errorMsg) {
                showMessage(errorMsg);
            }
        });

    }

    private void showMessage(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

}

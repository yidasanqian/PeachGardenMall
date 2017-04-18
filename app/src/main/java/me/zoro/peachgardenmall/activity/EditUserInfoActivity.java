package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.fragment.MyFragment;

public class EditUserInfoActivity extends AppCompatActivity {

    private static final String TAG = "EditUserInfoActivity";
    private static final int LOGIN_REQUEST_CODE = 1;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.edit_avatar)
    RelativeLayout mEditAvatar;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.edit_nickname)
    RelativeLayout mEditNickname;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.edit_sex)
    RelativeLayout mEditSex;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.edit_account)
    RelativeLayout mEditAccount;
    @BindView(R.id.tv_autograph)
    TextView mTvAutograph;
    @BindView(R.id.edit_autograph)
    RelativeLayout mEditAutograph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        if (getIntent() != null) {
            UserInfo userInfo = (UserInfo) getIntent().getSerializableExtra(MyFragment.USERINFO_EXTRA);
            if (userInfo != null) {
                invalidateUI(userInfo);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, EditUserInfoActivity.LOGIN_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE && resultCode == RESULT_OK) {
            UserInfo userInfo = (UserInfo) data.getSerializableExtra(MyFragment.USERINFO_EXTRA);
            if (userInfo != null) {
                invalidateUI(userInfo);
            }
        }
    }

    private void invalidateUI(UserInfo userInfo) {
        Picasso.with(this)
                .load(userInfo.getHeadPic())
                .fit()
                .into(mIvAvatar);
        mTvNickname.setText(userInfo.getNickname());
        mTvSex.setText(userInfo.getSex());
        mTvAccount.setText(userInfo.getMobile());
        if (TextUtils.isEmpty(userInfo.getAutograph())) {
            mTvAutograph.setText("未设置");
        } else {
            mTvAutograph.setText(userInfo.getAutograph());
        }
    }

    @OnClick({R.id.edit_avatar, R.id.edit_nickname, R.id.edit_sex, R.id.edit_account, R.id.edit_autograph})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // TODO: 17/4/12 编辑用户资料
            case R.id.edit_avatar:
                break;
            case R.id.edit_nickname:
                break;
            case R.id.edit_sex:
                break;
            case R.id.edit_account:
                break;
            case R.id.edit_autograph:
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

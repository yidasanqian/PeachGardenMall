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
import de.hdodenhof.circleimageview.CircleImageView;
import me.zoro.peachgardenmall.R;

public class EditUserInfoActivity extends AppCompatActivity {

    private static final String TAG = "EditUserInfoActivity";

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.edit_avatar)
    RelativeLayout mEditAvatar;
    @BindView(R.id.tv_nickname_lbl)
    TextView mTvNicknameLbl;
    @BindView(R.id.edit_nickname)
    RelativeLayout mEditNickname;
    @BindView(R.id.tv_sex_lbl)
    TextView mTvSexLbl;
    @BindView(R.id.edit_sex)
    RelativeLayout mEditSex;
    @BindView(R.id.tv_account_lbl)
    TextView mTvAccountLbl;
    @BindView(R.id.edit_account)
    RelativeLayout mEditAccount;
    @BindView(R.id.tv_autograph_lbl)
    TextView mTvAutographLbl;
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

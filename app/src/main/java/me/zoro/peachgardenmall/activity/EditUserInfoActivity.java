package me.zoro.peachgardenmall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.UserDatasource;
import me.zoro.peachgardenmall.datasource.UserRepository;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.UserRemoteDatasource;
import me.zoro.peachgardenmall.fragment.MyFragment;
import me.zoro.peachgardenmall.utils.CacheManager;
import me.zoro.peachgardenmall.utils.PreferencesUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditUserInfoActivity extends AppCompatActivity {

    private static final String TAG = "EditUserInfoActivity";
    private static final int LOGIN_REQUEST_CODE = 1;
    private static final int REQUEST_UPDATE_AVATAR = 2;

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

    private UserRepository mUserRepository;

    private UserInfo mUserInfo;

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

        mUserRepository = UserRepository.getInstance(UserRemoteDatasource.getInstance(getApplicationContext()));


        mUserInfo = (UserInfo) getIntent().getSerializableExtra(MyFragment.USERINFO_EXTRA);
        if (mUserInfo != null) {
            invalidateUI(mUserInfo);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, EditUserInfoActivity.LOGIN_REQUEST_CODE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mUserInfo != null) {
            mUserRepository.setDirty(true);
            new FetchUserInfoTask().execute(mUserInfo.getUserId());
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
        } else if (requestCode == REQUEST_UPDATE_AVATAR && resultCode == RESULT_OK) {
            setupAvatar(data);
        }
    }

    private void setupAvatar(Intent data) {
        Uri uri = data.getData();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        File file = null;
        if (cursor != null) {
            try {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    String path = cursor.getString(columnIndex);
                    file = new File(path);
                }
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "setupAvatar: 无效参数", e);
            } finally {
                cursor.close();
            }
        }

        if (file != null) {
            RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
            Map<String, Object> params = new HashMap<>();
            params.put("userId", mUserInfo.getUserId());
            params.put("avatar", MultipartBody.Part.createFormData("avatar", file.getName(), body));
            mUserRepository.uploadAvatar(params, new UserDatasource.UploadAvatarCallback() {
                @Override
                public void onUploaded(String avatarUrl) {
                    Picasso.with(EditUserInfoActivity.this)
                            .load(avatarUrl)
                            .fit()
                            .into(mIvAvatar);
                    mUserInfo.setHeadPic(avatarUrl);
                    CacheManager.getInstance().put(Const.USER_INFO_CACHE_KEY, mUserInfo);
                    PreferencesUtil.persistentUserInfo(EditUserInfoActivity.this, mUserInfo);
                }

                @Override
                public void onUploadFailure() {
                    showMessage("上传失败！");
                }
            });
        }
    }

    private void showMessage(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
        final EditText editText = new EditText(this);
        switch (view.getId()) {
            // TODO: 17/4/12 编辑用户资料
            case R.id.edit_avatar:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_UPDATE_AVATAR);
                break;
            case R.id.edit_nickname:
                String title = "修改昵称", key = "nickname";
                reviseUserInfo(editText, title, key);
                break;
            case R.id.edit_sex:
                title = "修改性别";
                key = "sex";
                reviseUserInfo(editText, title, key);
                break;
            case R.id.edit_account:
                break;
            case R.id.edit_autograph:
                title = "设置个性签名";
                key = "autograph";
                reviseUserInfo(editText, title, key);
                break;
        }
    }

    private void reviseUserInfo(final EditText editText, String title, final String key) {
        new AlertDialog.Builder(this)
                .setView(editText)
                .setTitle(title)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = editText.getText().toString();
                        if (TextUtils.isEmpty(value)) {
                            showMessage(getString(R.string.empty_value_msg));
                        } else {
                            Map<String, Object> params = new HashMap<>();
                            params.put("userId", mUserInfo.getUserId());
                            params.put(key, value);
                            mUserRepository.userInfoRevise(params, new UserDatasource.UserInfoReviseCallback() {
                                @Override
                                public void onUserInfoReviseSuccess() {
                                    mUserRepository.setDirty(true);
                                    new FetchUserInfoTask().execute(mUserInfo.getUserId());
                                    showMessage(getString(R.string.revise_success_msg));
                                }

                                @Override
                                public void onUserInfoReviseFailure(String errorMsg) {
                                    showMessage(errorMsg);
                                }
                            });
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    /**
     * 获取用户信息
     */
    private class FetchUserInfoTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            int userId = params[0];
            mUserRepository.fetchUserInfo(userId, new UserDatasource.GetUserInfoCallback() {
                @Override
                public void onUserInfoLoaded(final UserInfo userInfo) {
                    mUserInfo = userInfo;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            invalidateUI(userInfo);
                        }
                    });

                    PreferencesUtil.persistentUserInfo(EditUserInfoActivity.this, userInfo);
                }

                @Override
                public void onDataNotAvailable(String errorMsg) {
                    Log.w(TAG, "onDataNotAvailable: " + errorMsg);
                    if (Const.SERVER_UNAVAILABLE.equals(errorMsg)) {
                        showMessage(errorMsg);
                    }
                }
            });

            return null;
        }
    }
}

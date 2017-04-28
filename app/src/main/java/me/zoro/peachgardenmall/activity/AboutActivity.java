package me.zoro.peachgardenmall.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zoro.peachgardenmall.R;

public class AboutActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.tv_version_name)
    TextView mTvVersionName;
    @BindView(R.id.tv_nickname_lbl)
    TextView mTvNicknameLbl;
    @BindView(R.id.edit_business_cooperation)
    RelativeLayout mEditBusinessCooperation;
    @BindView(R.id.edit_terms_of_use)
    RelativeLayout mEditTermsOfUse;
    @BindView(R.id.edit_about_shop)
    RelativeLayout mEditAboutShop;
    @BindView(R.id.edit_about_app)
    RelativeLayout mEditAboutApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
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

    @OnClick({R.id.edit_business_cooperation, R.id.edit_terms_of_use, R.id.edit_about_shop, R.id.edit_about_app})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_business_cooperation:
                break;
            case R.id.edit_terms_of_use:
                break;
            case R.id.edit_about_shop:
                new AlertDialog.Builder(this)
                        .setTitle("关于商店")
                        .setMessage(Html.fromHtml(getString(R.string.about_shop)))
                        .setCancelable(true)
                        .setNegativeButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                break;
            case R.id.edit_about_app:
                new AlertDialog.Builder(this)
                        .setTitle("关于小布")
                        .setMessage(Html.fromHtml(getString(R.string.about_app)))
                        .setCancelable(true)
                        .setNegativeButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                break;
        }
    }
}

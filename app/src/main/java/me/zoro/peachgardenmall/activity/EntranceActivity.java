package me.zoro.peachgardenmall.activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.utils.NetworkUtil;
import me.zoro.peachgardenmall.utils.PreferencesUtil;

/**
 * app入口：显示闪屏页和引导页
 */
public class EntranceActivity extends AppCompatActivity {

    private static final String TAG = "EntranceActivity";
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static final String IS_FIRST = "is_first";
    @BindView(R.id.iv_entrance)
    ImageView mIvEntrance;

    private boolean mIsFirst;    // 判断是否第一次打开软件
    private int mCountDown; // 倒计时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 如果网络可用则判断是否第一次进入，如果是第一次则进入欢迎界面
        if (NetworkUtil.isNetworkConnected(this)) {
            mIsFirst = PreferencesUtil.getDefaultPreferences(this, Const.PREF_CONFIG)
                    .getBoolean(IS_FIRST, true);

            // 停留1s
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mCountDown > 0) {
                        handler.removeCallbacks(this);

                        // 如果第一次，则进入引导页WelcomeActivity,否则进入主页MainActivity
                        if (mIsFirst) {
                            Intent intent = new Intent(EntranceActivity.this, WelcomeActivity.class);
                            PreferencesUtil.getDefaultPreferences(EntranceActivity.this, Const.PREF_CONFIG)
                                    .edit()
                                    .putBoolean(IS_FIRST, false)
                                    .apply();
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(EntranceActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        mCountDown++;
                        handler.postDelayed(this, 1000);
                    }
                }
            }, 1000);

        } else {
            // 如果网络不可用，则弹出对话框，对网络进行设置
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("没有可用的网络!")
                    .setMessage("是否对网络进行设置?")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = null;
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    intent = new Intent(Settings.ACTION_SETTINGS);
                                } else {
                                    intent = new Intent();
                                    ComponentName comp = new ComponentName(
                                            "com.android.settings",
                                            "com.android.settings.WirelessSettings");
                                    intent.setComponent(comp);
                                    intent.setAction("android.intent.action.VIEW");
                                }
                                EntranceActivity.this.startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EntranceActivity.this.finish();
                        }
                    })
                    .create()
                    .show();


        }

    }
}

package me.zoro.peachgardenmall.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.MainFragmentPagerAdapter;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.fragment.HomeFragment;
import me.zoro.peachgardenmall.fragment.MallFragment;
import me.zoro.peachgardenmall.fragment.MyFragment;
import me.zoro.peachgardenmall.utils.PreferencesUtil;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener, MyFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    private static final int PERMISSION_REQUEST_CODE = 1;

    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;
    MenuItem mPrevMenuItem;

    FragmentManager mFragmentManager;
    /**
     * 未授权的运行时权限
     */
    private ArrayList<String> mPermissions;


    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        requestPermissions();

        Intent intent = getIntent();
        if (intent != null) {
            mUserInfo = (UserInfo) intent.getSerializableExtra(LoginActivity.USERINFO_EXTRA);
        }

        mBottomNavigation.setOnNavigationItemSelectedListener(this);
        mViewpager.addOnPageChangeListener(this);
        setupViewPager(mViewpager);

    }

    @TargetApi(M)
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= M) {
            mPermissions = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // 未授权，显示该权限的用途说明，如果应用没有获得对应权限,则添加到列表中,准备批量申请
                mPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission((Manifest.permission.ACCESS_FINE_LOCATION))
                    != PackageManager.PERMISSION_GRANTED) {

                mPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission((Manifest.permission.ACCESS_COARSE_LOCATION))
                    != PackageManager.PERMISSION_GRANTED) {

                mPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (checkSelfPermission((Manifest.permission.READ_PHONE_STATE))
                    != PackageManager.PERMISSION_GRANTED) {

                mPermissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                mPermissions.add(Manifest.permission.READ_CONTACTS);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                mPermissions.add(Manifest.permission.WRITE_CONTACTS);
            }
            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                mPermissions.add(Manifest.permission.SEND_SMS);
            }
            if (checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                mPermissions.add(Manifest.permission.RECEIVE_SMS);
            }
            if (checkSelfPermission(Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                mPermissions.add(Manifest.permission.READ_SMS);
            }
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                mPermissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                mPermissions.add(Manifest.permission.CAMERA);
            }

            /**
             *  申请的权限被拒绝后,可以在此提示用户打开权限
             */
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.d(TAG, "requestPermissions: 写外部存储权限被禁止");
            }
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG, "requestPermissions: 精确定位权限被禁止");
            }
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Log.d(TAG, "requestPermissions: 模糊定位权限被禁止");
            }
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                Log.d(TAG, "requestPermissions: 读取手机状态权限被禁止");
            }
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                Log.d(TAG, "requestPermissions: 读取联系人权限被禁止");
            }
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                Log.d(TAG, "requestPermissions: 写联系人权限被禁止");
            }
            if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                Log.d(TAG, "requestPermissions: 发送短信权限被禁止");
            }
            if (shouldShowRequestPermissionRationale(Manifest.permission.RECEIVE_SMS)) {
                Log.d(TAG, "requestPermissions: 接收短信权限被禁止");
            }
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)) {
                Log.d(TAG, "requestPermissions: 读取短信权限被禁止");
            }
            if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                Log.d(TAG, "requestPermissions: 读取短信权限被禁止");
            }
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Log.d(TAG, "requestPermissions: 读取短信权限被禁止");
            }

            if (mPermissions.size() > 0) {
                requestPermissions(mPermissions.toArray(new String[mPermissions.size()]),
                        PERMISSION_REQUEST_CODE);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            int index = 0;

            for (int gr :
                    grantResults) {
                if (index < grantResults.length) {
                    if (gr == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: 授权成功的权限==> " + mPermissions.get(index));
                    } else {
                        // Permission Denied
                        Log.d(TAG, "onRequestPermissionsResult: 被拒绝的权限 ==> " + mPermissions.get(index));
                    }
                }
                index++;
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        String token = PreferencesUtil.getTokenFromPref(this);
        if (TextUtils.isEmpty(token)) {
            mUserInfo = null;
            Toast.makeText(this, "您未登录", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupViewPager(ViewPager viewpager) {
        mFragmentManager = getSupportFragmentManager();
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(mFragmentManager);
        HomeFragment homeFragment = HomeFragment.newInstance(getText(R.string.home_title).toString());
        MallFragment mallFragment = MallFragment.newInstance(getText(R.string.mall_title).toString());
        MyFragment myFragment = MyFragment.newInstance();

        adapter.addFragment(homeFragment);
        adapter.addFragment(mallFragment);
        adapter.addFragment(myFragment);
        viewpager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_title:
                mViewpager.setCurrentItem(0);
                break;
            case R.id.mall_title:
                mViewpager.setCurrentItem(1);
                break;
            case R.id.my_title:
                mViewpager.setCurrentItem(2);
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mPrevMenuItem != null) {
            mPrevMenuItem.setChecked(false);
        } else {
            mBottomNavigation.getMenu().getItem(0).setChecked(false);
        }
        mBottomNavigation.getMenu().getItem(position).setChecked(true);
        mPrevMenuItem = mBottomNavigation.getMenu().getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onUserInfoLoaded(UserInfo userInfo) {
        mUserInfo = userInfo;
    }
}

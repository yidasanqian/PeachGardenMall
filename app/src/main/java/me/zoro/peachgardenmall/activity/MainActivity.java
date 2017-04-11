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

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;
    MenuItem mPrevMenuItem;
    FragmentManager mFragmentManager;

    private static final int PERMISSION_REQUEST_CODE = 1;
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

        mBottomNavigation.setOnNavigationItemSelectedListener(this);
        mViewpager.addOnPageChangeListener(this);
        setupViewPager(mViewpager);

        requestPermissions();


        // TODO: 17/4/11 判断用户是否登录
        if (mUserInfo != null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPermissions = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // 未授权，显示该权限的用途说明，如果应用没有获得对应权限,则添加到列表中,准备批量申请
                mPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else if (checkSelfPermission((Manifest.permission.ACCESS_FINE_LOCATION))
                    != PackageManager.PERMISSION_GRANTED) {

                mPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            } else if (checkSelfPermission((Manifest.permission.ACCESS_COARSE_LOCATION))
                    != PackageManager.PERMISSION_GRANTED) {

                mPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            } else if (checkSelfPermission((Manifest.permission.READ_PHONE_STATE))
                    != PackageManager.PERMISSION_GRANTED) {

                mPermissions.add(Manifest.permission.READ_PHONE_STATE);
            }


            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.d(TAG, "requestPermissions: 申请的权限被拒绝后显示权限描述");
                Log.d(TAG, "requestPermissions: 写外部存储权限被禁止");
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG, "requestPermissions: 精确定位权限被禁止");
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Log.d(TAG, "requestPermissions: 模糊定位权限被禁止");
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                Log.d(TAG, "requestPermissions: 读取手机状态权限被禁止");
            } else {
                if (mPermissions.size() > 0) {
                    requestPermissions(mPermissions.toArray(new String[mPermissions.size()]),
                            PERMISSION_REQUEST_CODE);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: 授权成功！");
            } else {
                // Permission Denied
                Log.d(TAG, "onRequestPermissionsResult: Permission Denied");
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setupViewPager(ViewPager viewpager) {
        mFragmentManager = getSupportFragmentManager();
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(mFragmentManager);
        HomeFragment homeFragment = HomeFragment.newInstance(getText(R.string.home_title).toString());
        MallFragment mallFragment = MallFragment.newInstance(getText(R.string.mall_title).toString());
        MyFragment myFragment = MyFragment.newInstance(getText(R.string.my_title).toString());

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
}

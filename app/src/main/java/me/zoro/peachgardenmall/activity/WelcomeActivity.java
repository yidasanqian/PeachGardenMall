package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;

/**
 * 引导页
 */
public class WelcomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.btn_entrance_app)
    Button mBtnEntranceApp;
    @BindView(R.id.indicator)
    LinearLayout mIndicatorLayout;

    private WelcomePagerAdapter mWelcomePagerAdapter;
    private ImageView[] mWelcomeImages;
    private ImageView[] mIndicatorImages;
    private int[] mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        mBtnEntranceApp.setVisibility(View.INVISIBLE);

        mImages = new int[]{R.drawable.ic_welcome_01, R.drawable.ic_welcome_02, R.drawable.ic_welcome_03};
        mWelcomeImages = new ImageView[mImages.length];
        mIndicatorImages = new ImageView[mImages.length];
        for (int i = 0; i < mImages.length; i++) {
            // 循环加入引导图
            ImageView welcomeImg = new ImageView(this);
            mWelcomeImages[i] = welcomeImg;
            Picasso.with(this).load(mImages[i]).fit().into(mWelcomeImages[i]);

            // 加入指示器图片
            mIndicatorImages[i] = new ImageView(this);
            mIndicatorImages[i].setBackgroundResource(R.drawable.ic_indicators_default);
            if (i == 0) {
                mIndicatorImages[i].setBackgroundResource(R.drawable.ic_indicators_now);
            }

            mIndicatorLayout.addView(mIndicatorImages[i]);
        }

        mWelcomePagerAdapter = new WelcomePagerAdapter(mWelcomeImages);
        mViewPager.setAdapter(mWelcomePagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }


    @OnClick(R.id.btn_entrance_app)
    public void onClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 显示最后一个图片时显示按钮
        if (position == mIndicatorImages.length - 1) {
            mBtnEntranceApp.setVisibility(View.VISIBLE);
            // 延迟0.8s进入
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    handler.removeCallbacks(this);
                }
            }, 800);
        } else {
            mBtnEntranceApp.setVisibility(View.INVISIBLE);
        }
        // 更改指示器图片
        for (int i = 0; i < mIndicatorImages.length; i++) {
            mIndicatorImages[position].setBackgroundResource(R.drawable.ic_indicators_now);
            if (position != i) {
                mIndicatorImages[i].setBackgroundResource(R.drawable.ic_indicators_default);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private class WelcomePagerAdapter extends PagerAdapter {

        private ImageView[] mWelcomeImages;

        public WelcomePagerAdapter(ImageView[] welcomeImages) {
            mWelcomeImages = welcomeImages;
        }

        @Override
        public int getCount() {
            return mWelcomeImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 这里需要将container转为ViewPager，否则显示黑屏
            ((ViewPager) container).addView(mWelcomeImages[position]);
            return mWelcomeImages[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mWelcomeImages[position]);
        }
    }
}

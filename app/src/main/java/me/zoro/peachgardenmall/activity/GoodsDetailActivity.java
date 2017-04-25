package me.zoro.peachgardenmall.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.datasource.GoodsDatasource;
import me.zoro.peachgardenmall.datasource.GoodsRepository;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.remote.GoodsRemoteDatasource;
import me.zoro.peachgardenmall.fragment.HomeFragment;
import me.zoro.peachgardenmall.utils.DensityUtil;

/**
 * Created by dengfengdecao on 17/4/10.
 */

public class GoodsDetailActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private static final String TAG = "GoodsDetailActivity";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.tv_goods_name)
    TextView mTvGoodsName;
    @BindView(R.id.tv_goods_remark)
    TextView mTvGoodsRemark;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.iv_category)
    ImageView mIvCategory;
    @BindView(R.id.tv_select_spec)
    TextView mTvSelectSpec;
    @BindView(R.id.edit_select_spec)
    RelativeLayout mEditSelectSpec;
    @BindView(R.id.tv_promotion_count)
    TextView mTvPromotionCount;
    @BindView(R.id.tv_promotion)
    TextView mTvPromotion;
    @BindView(R.id.edit_promotion)
    RelativeLayout mEditPromotion;
    @BindView(R.id.tv_service)
    TextView mTvService;
    @BindView(R.id.edit_service)
    RelativeLayout mEditService;
    @BindView(R.id.tv_comment_number)
    TextView mTvCommentNumber;
    @BindView(R.id.edit_comment)
    RelativeLayout mEditComment;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.web_view)
    WebView mWebView;

    private GoodsRepository mGoodsRepository;

    private int mGoodsId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);

        // 当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            final int statusBarHeight = DensityUtil.getStatusBarHeight(this);
            mToolbar.post(new Runnable() {
                @Override
                public void run() {
                    mToolbar.getLayoutParams().height += statusBarHeight;
                    mToolbar.setPadding(mToolbar.getPaddingLeft(),
                            statusBarHeight + mToolbar.getPaddingTop(),
                            mToolbar.getPaddingRight(),
                            mToolbar.getPaddingBottom());

                }
            });
        }


        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        mToolbar.setOnMenuItemClickListener(this);

        mGoodsRepository = GoodsRepository.getInstance(GoodsRemoteDatasource.getInstance(
                getApplicationContext()
        ));

        mGoodsId = getIntent().getIntExtra(HomeFragment.GOODS_ID_EXTRA, -1);

    }


    private void setupBanner(List<String> imagesUrl) {
        // 设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        // 设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        // 设置图片加载器
        mBanner.setImageLoader(new PicassoImageLoader());
        // 设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        // 设置标题集合（当banner样式有显示title时）
        // mBanner.setBannerTitles(Arrays.asList(mTitles));
        // 设置轮播图片(所有设置参数方法都放在此方法之前执行)
        mBanner.setImages(imagesUrl);
        // banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoodsId != -1) {
            new FetchGoodsDetailTask().execute();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.goods_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.action_home:
                msg += "Click edit";
                break;
            /*case R.id.action_share:
                msg += "Click share";
                break;*/
        }

        if (!msg.equals("")) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @OnClick({R.id.edit_select_spec, R.id.edit_promotion, R.id.edit_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // TODO: 17/4/25 选择规格
            case R.id.edit_select_spec:
                break;
            // TODO: 17/4/25 选择促销
            case R.id.edit_promotion:
                break;
            // TODO: 17/4/25 查看评论
            case R.id.edit_comment:
                break;
        }
    }

    private class PicassoImageLoader implements ImageLoader {

        @Override
        public void displayImage(final Context context, Object path, ImageView imageView) {
            // 加载发生错误会重复三次请求，三次都失败才会显示error Place holder
            Picasso.with(context).load((String) path)
                    .fit()  // This avoided the OutOfMemoryError
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "onSuccess: 加载轮播图成功！");
                        }

                        @Override
                        public void onError() {
                            Toast.makeText(context, "服务器异常，加载轮播图失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private class FetchGoodsDetailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            mGoodsRepository.getGoodsDetail(mGoodsId, new GoodsDatasource.GetGoodsDetailCallback() {
                @Override
                public void onGoodsLoaded(final Goods goods) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            invalidateUI(goods);
                        }
                    });
                }

                @Override
                public void onDataNotAvailable(String errorMsg) {
                    showMessage(errorMsg);
                }
            });
            return null;
        }
    }

    private void showMessage(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void invalidateUI(Goods goods) {
        List<String> imagesUrl = new ArrayList<>();
        List<Goods.ImageDataEntity> imageDataEntityList = goods.getImageData();
        for (int i = 0; i < imageDataEntityList.size(); i++) {
            imagesUrl.add(imageDataEntityList.get(i).getImageUrl());
        }
        setupBanner(imagesUrl);

        mTvGoodsName.setText(goods.getGoodsName());
        mTvGoodsRemark.setText(goods.getGoodsRemark());
        mTvPrice.setText(goods.getPrice());
        int num = goods.getCommentData().getNumber();
        mTvCommentNumber.setText("（" + num + "）");
    }
}

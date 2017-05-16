package me.zoro.peachgardenmall.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.activity.AdActivity;
import me.zoro.peachgardenmall.activity.GoodsDetailActivity;
import me.zoro.peachgardenmall.adapter.GoodsRecyclerGridAdapter;
import me.zoro.peachgardenmall.datasource.BannerDatasource;
import me.zoro.peachgardenmall.datasource.BannerRepository;
import me.zoro.peachgardenmall.datasource.GoodsDatasource;
import me.zoro.peachgardenmall.datasource.GoodsRepository;
import me.zoro.peachgardenmall.datasource.domain.BannerInfo;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.remote.BannerRemoteDatasource;
import me.zoro.peachgardenmall.datasource.remote.GoodsRemoteDatasource;
import me.zoro.peachgardenmall.view.SpacesItemDecoration;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class HomeFragment extends Fragment implements OnBannerClickListener {
    private static final String TAG = "HomeFragment";
    public static final String AD_URL_EXTRA = "ad_url";
    public static final String GOODS_ID_EXTRA = "goods_id";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_right_img)
    ImageButton mToolbarRightImg;


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerGridView;
    Unbinder unbinder;

    Banner mBanner;

    private BannerRepository mBannerRepository;
    private BannerInfo mBannerInfo;

    private List<String> mImagesUrl = new ArrayList<>();
   /* private ArrayAdapter<String> mSpinnerAdapter;
    private String[] mCategory;*/

    private GoodsRepository mGoodsRepository;
    private GoodsRecyclerGridAdapter mGridAdapter;
    private List<Goods> mGoodses;
    /**
     * 默认获取第一页
     */
    private int mPageNum = 1;
    /**
     * 默认获取10条
     */
    private int mPageSize = 10;
    /**
     * 是否正在加载更多，true，表示正在加载，false，则不是
     */
    private boolean mIsLoadingMore;

    public static HomeFragment newInstance(String s) {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBannerRepository = BannerRepository.getInstance(BannerRemoteDatasource.getInstance(
                getContext().getApplicationContext()));

        mGoodsRepository = GoodsRepository.getInstance(GoodsRemoteDatasource.getInstance(
                getContext().getApplicationContext()
        ));

      /*  mImagesUrl.add("http://img3.imgtn.bdimg.com/it/u=2264776075,3168614604&fm=21&gp=0.jpg");
        mImagesUrl.add("http://img3.duitang.com/uploads/item/201604/30/20160430003024_FwSEG.thumb.700_0.jpeg");
        mImagesUrl.add("http://g.hiphotos.baidu.com/zhidao/pic/item/bd315c6034a85edf433f02544f540923dd547512.jpg");
        mImagesUrl.add("http://i-7.vcimg.com/trim/b7316e98fe939f0f9b064b8ac2de99d0351027/trim.jpg");*/

        mGoodses = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);

        View headerView = inflater.inflate(R.layout.fragment_home_with_header, null);

        mBanner = (Banner) headerView.findViewById(R.id.banner);

        if (mBannerInfo != null) {
            setupBanner(mBannerInfo);
        } else {
            new FetchBannerTask().execute();
        }
        mBanner.setOnBannerClickListener(this);

        // 初始化spinner中显示的数据
        /*mCategory = getResources().getStringArray(R.array.category);
        mSpinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mCategory);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(this);*/

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        mRecyclerGridView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        mGridAdapter = new GoodsRecyclerGridAdapter(getContext(), headerView, mGoodses);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mGridAdapter.isHeader(position) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
        mRecyclerGridView.setLayoutManager(gridLayoutManager);
        mRecyclerGridView.setAdapter(mGridAdapter);
        mRecyclerGridView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (SCROLL_STATE_IDLE == newState && recyclerView.getAdapter().getItemCount() <= mPageSize) {
                    mIsLoadingMore = false;
                    mPageNum = 1;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // 上拉加载
                if (gridLayoutManager.findLastVisibleItemPosition() ==
                        recyclerView.getAdapter().getItemCount() - 1 && !mIsLoadingMore && dy > 0) {
                    mIsLoadingMore = true;
                    mPageNum++;
                    new FetchGoodsesTask().execute();
                }
            }
        });
        mGridAdapter.setOnItemClickListener(new GoodsRecyclerGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Goods goods) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra(GOODS_ID_EXTRA, goods.getGoodsId());
                startActivity(intent);
            }
        });
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mGoodses.size() < 1) {
            new FetchGoodsesTask().execute();
        }
    }

    @Override
    public void OnBannerClick(int position) {
        int pos = position - 1;
        // 0：表示广告， 1：表示商品
        int type = 0;
        List<BannerInfo.AdDataEntity> adList = mBannerInfo.getAdList();
        List<BannerInfo.GoodsDataEntity> goodsList = mBannerInfo.getGoodsList();
        try {

            for (int i = 0; i < adList.size(); i++) {
                if (mImagesUrl.get(pos).equals(adList.get(i).getAdImageUrl())) {
                    type = 0;
                }
            }
            for (int i = 0; i < goodsList.size(); i++) {
                if (mImagesUrl.get(pos).equals(goodsList.get(i).getGoodsImageUrl())) {
                    type = 1;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "OnBannerClick: 数组越界", e);
            return;
        }

        if (type == 0) {
            Intent intent = new Intent(getActivity(), AdActivity.class);
            intent.putExtra(AD_URL_EXTRA, adList.get(pos).getAdLink());
            startActivity(intent);
        } else {
            Log.d(TAG, "OnBannerClick: 商品");
        }
    }

    // 显示客服信息
    @OnClick(R.id.toolbar_right_img)
    public void onViewClicked() {
        showServiceInfo();
    }

    private void showServiceInfo() {
        // TODO: 17/4/25 修改客服信息
        if (!getActivity().isFinishing()) {
            TextView view = new TextView(getActivity());
            view.setPadding(48, 16, 48, 16);
            view.setText(Html.fromHtml(getString(R.string.service_contact_information)));
            view.setMovementMethod(LinkMovementMethod.getInstance());
            new AlertDialog.Builder(getContext())
                    .setTitle("客服信息")
                    .setView(view)
                    .setCancelable(true)
                    .show();
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class FetchBannerTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            // 每种广告类型的数量
            int count = 2;
            Map<String, Object> p = new HashMap<>();
            p.put("count", count);
            mBannerRepository.getBannerInfo(p, new BannerDatasource.GetBannerInfoCallback() {
                @Override
                public void onBannerLoaded(final BannerInfo bannerInfo) {
                    mBannerInfo = bannerInfo;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setupBanner(bannerInfo);
                        }
                    });
                }

                @Override
                public void onDataNotAvoidable(String errorMsg) {
                    showMessage(errorMsg);
                }
            });
            return null;
        }
    }

    private void setupBanner(BannerInfo bannerInfo) {
        List<BannerInfo.AdDataEntity> adList = bannerInfo.getAdList();
        for (int i = 0; i < adList.size(); i++) {
            mImagesUrl.add(adList.get(i).getAdImageUrl());
        }
        List<BannerInfo.GoodsDataEntity> goodsList = bannerInfo.getGoodsList();
        for (int i = 0; i < goodsList.size(); i++) {
            mImagesUrl.add(goodsList.get(i).getGoodsImageUrl());
        }


        // 设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        // 设置图片加载器
        mBanner.setImageLoader(new PicassoImageLoader());
        // 设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        // 设置标题集合（当banner样式有显示title时）
        // mBanner.setBannerTitles(Arrays.asList(mTitles));
        // 设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        // 设置轮播图片(所有设置参数方法都放在此方法之前执行)
        mBanner.setImages(mImagesUrl);
        // banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    private void showMessage(String msg) {
        if (!getActivity().isFinishing()) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    private class FetchGoodsesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Map<String, Object> map = new HashMap<>();
            map.put("pn", mPageNum);
            map.put("ps", mPageSize);
            mGoodsRepository.getGoodses(map, new GoodsDatasource.GetGoodsesCallback() {
                @Override
                public void onGoodsesLoaded(ArrayList<Goods> goodses) {
                    if (goodses.size() > 0) {
                        if (mPageNum > 1) {
                            mGoodses.addAll(goodses);
                            mGridAdapter.appendData(goodses);
                        } else {
                            mGoodses = goodses;
                            mGridAdapter.replaceData(goodses);
                        }
                        mIsLoadingMore = false;
                    }
                }

                @Override
                public void onDataNotAvailable(String errorMsg) {
                    showMessage(errorMsg);
                    mIsLoadingMore = false;
                }
            });
            return null;
        }
    }
}

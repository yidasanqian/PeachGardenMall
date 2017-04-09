package me.zoro.peachgardenmall.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.activity.AdActivity;
import me.zoro.peachgardenmall.adapter.GoodsGridAdapter;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class HomeFragment extends Fragment implements OnBannerClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_right_img)
    ImageButton mToolbarRightImg;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.spinner)
    Spinner mSpinner;
    @BindView(R.id.grid_view)
    GridView mGridView;
    Unbinder unbinder;
    private List<String> mImagesUrl = new ArrayList<>();

    public static HomeFragment newInstance(String s) {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);

        // TODO: 17/4/9 banner跳转url
        mImagesUrl.add("http://img3.imgtn.bdimg.com/it/u=2264776075,3168614604&fm=21&gp=0.jpg");
        mImagesUrl.add("http://img3.duitang.com/uploads/item/201604/30/20160430003024_FwSEG.thumb.700_0.jpeg");
        mImagesUrl.add("http://g.hiphotos.baidu.com/zhidao/pic/item/bd315c6034a85edf433f02544f540923dd547512.jpg");
        mImagesUrl.add("http://i-7.vcimg.com/trim/b7316e98fe939f0f9b064b8ac2de99d0351027/trim.jpg");
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
        mBanner.setOnBannerClickListener(this);

        mSpinner.setOnItemSelectedListener(this);

        // TODO: 17/4/9 banner显示的图片
        List<Integer> images = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            images.add(R.drawable.ic_gaoyuanyuan);
        }
        GoodsGridAdapter goodsAdapter = new GoodsGridAdapter(getContext(), images);
        mGridView.setAdapter(goodsAdapter);
        mGridView.setOnItemClickListener(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void OnBannerClick(int position) {
        // TODO: 17/4/9 banner点击事件
        Intent intent = new Intent(getActivity(), AdActivity.class);
       /* intent.putExtra(BANNER_TITLE_EXTRA, mBannerInfos.get(position - 1).getRelateArticleTitle());
        intent.putExtra(BANNER_URL_EXTRA, mBannerInfos.get(position - 1).getRelateUrl());*/
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] category = getResources().getStringArray(R.array.category);
        Snackbar.make(getView(), "你点击的是:" + category[position], Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO: 17/4/9 商品点击事件
        Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
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

}

package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.MyCollectionGoodsRecyclerViewAdapter;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.GoodsDatasource;
import me.zoro.peachgardenmall.datasource.GoodsRepository;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.GoodsRemoteDatasource;
import me.zoro.peachgardenmall.utils.CacheManager;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static me.zoro.peachgardenmall.fragment.HomeFragment.GOODS_ID_EXTRA;

/**
 * Created by dengfengdecao on 17/4/9.
 */

public class MyCollectionActivity extends AppCompatActivity implements MyCollectionGoodsRecyclerViewAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MyCollectionGoodsRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<Goods> mGoodses;
    private GoodsRepository mGoodsRepository;

    /**
     * 是否正在加载更多，true，表示正在加载，false，则不是
     */
    private boolean mIsLoadingMore;
    /**
     * 默认获取第一页
     */
    private int mPageNum = 1;
    /**
     * 默认获取10条
     */
    private int mPageSize = 10;

    private UserInfo mUserInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mGoodsRepository = GoodsRepository.getInstance(GoodsRemoteDatasource.getInstance(
                getApplicationContext()
        ));

        mUserInfo = (UserInfo) CacheManager.getInstance().get(Const.USER_INFO_CACHE_KEY);

        mGoodses = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyCollectionGoodsRecyclerViewAdapter(this, mGoodses);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (SCROLL_STATE_IDLE == newState && recyclerView.getAdapter().getItemCount() == mPageSize) {
                    mIsLoadingMore = false;
                    mPageNum = 1;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // 上拉加载
                if (mLayoutManager.findLastVisibleItemPosition() ==
                        recyclerView.getAdapter().getItemCount() - 1 && !mIsLoadingMore && mUserInfo != null) {
                    mIsLoadingMore = true;
                    mPageNum++;
                }
            }
        });
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoodses.size() < 1 && mUserInfo != null) {
            new FetchStarGoodsTask().execute();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onItemClick(View view, Goods goods) {
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra(GOODS_ID_EXTRA, goods.getGoodsId());
        startActivity(intent);
    }

    private class FetchStarGoodsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", mUserInfo.getUserId());
            map.put("pn", mPageNum);
            map.put("ps", mPageSize);
            mGoodsRepository.getGoodses(map, new GoodsDatasource.GetGoodsesCallback() {
                @Override
                public void onGoodsesLoaded(ArrayList<Goods> goodses) {
                    if (goodses.size() > 0) {
                        if (mPageNum > 1) {
                            mGoodses.addAll(goodses);
                            mAdapter.appendData(goodses);
                        } else {
                            mGoodses = goodses;
                            mAdapter.replaceData(goodses);
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

    private void showMessage(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}

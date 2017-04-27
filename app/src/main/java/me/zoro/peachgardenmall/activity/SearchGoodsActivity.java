package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.GoodsGridAdapter;
import me.zoro.peachgardenmall.datasource.GoodsDatasource;
import me.zoro.peachgardenmall.datasource.GoodsRepository;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.remote.GoodsRemoteDatasource;

import static me.zoro.peachgardenmall.fragment.HomeFragment.GOODS_ID_EXTRA;
import static me.zoro.peachgardenmall.fragment.MallFragment.GOODSES_EXTRA;
import static me.zoro.peachgardenmall.fragment.MallFragment.QUERY_EXTRA;

/**
 * Created by dengfengdecao on 17/4/21.
 */

public class SearchGoodsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.grid_view)
    GridView mGridView;

    private GoodsRepository mGoodsRepository;


    private GoodsGridAdapter mGoodsGridAdapter;

    private List<Goods> mGoodses;
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

    private String mQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_goods);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mGoodsRepository = GoodsRepository.getInstance(GoodsRemoteDatasource.getInstance(
                getApplicationContext()
        ));

        if (getIntent() != null) {
            mGoodses = (ArrayList<Goods>) getIntent().getSerializableExtra(GOODSES_EXTRA);
            mQuery = getIntent().getStringExtra(QUERY_EXTRA);
        }
        mGoodsGridAdapter = new GoodsGridAdapter(this, mGoodses);
        mGridView.setAdapter(mGoodsGridAdapter);
        mGridView.setOnItemClickListener(this);
        mGridView.setOnScrollListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Goods goods = mGoodses.get(position);
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra(GOODS_ID_EXTRA, goods.getGoodsId());
        startActivity(intent);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (SCROLL_STATE_IDLE == scrollState && view.getAdapter().getCount() == mPageSize) {
            mIsLoadingMore = false;
            mPageNum = 1;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 上拉加载
        if (view.getLastVisiblePosition() == totalItemCount - 1 && visibleItemCount > 0 && !mIsLoadingMore) {
            mIsLoadingMore = true;
            mPageNum++;
            searchGoodes(mQuery);
        }
    }

    private void searchGoodes(final String query) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", query);
        params.put("pn", mPageNum);
        params.put("ps", mPageSize);
        mGoodsRepository.searchGoodses(params, new GoodsDatasource.SearchGoodsesCallback() {
            @Override
            public void onSearchSucces(ArrayList<Goods> goodses) {
                if (goodses.size() > 0) {
                    if (mPageNum > 1) {
                        mGoodses.addAll(goodses);
                        mGoodsGridAdapter.appendData(goodses);
                    } else {
                        mGoodses = goodses;
                        mGoodsGridAdapter.replaceData(goodses);
                    }
                    mIsLoadingMore = false;
                }
            }

            @Override
            public void onSearchFailure(String msg) {
                showMessage(msg);
                mIsLoadingMore = false;
            }
        });
    }

    private void showMessage(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

}

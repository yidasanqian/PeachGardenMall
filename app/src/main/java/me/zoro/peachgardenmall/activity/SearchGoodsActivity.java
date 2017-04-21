package me.zoro.peachgardenmall.activity;

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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.GoodsGridAdapter;
import me.zoro.peachgardenmall.datasource.GoodsRepository;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.remote.GoodsRemoteDatasource;
import me.zoro.peachgardenmall.fragment.MallFragment;

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
    private boolean isLoadingMore;
    /**
     * 默认获取第一页
     */
    private int mPageNum = 1;
    /**
     * 默认获取10条
     */
    private int mPageSize = 10;

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
            mGoodses = (ArrayList<Goods>) getIntent().getSerializableExtra(MallFragment.GOODSES_EXTRA);
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
        // TODO: 17/4/9 商品点击事件
        Goods goods = mGoodses.get(position);
        Toast.makeText(this, goods.getGoodsName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 上拉加载
        if (view.getLastVisiblePosition() == totalItemCount - 1 && !isLoadingMore) {
            isLoadingMore = true;
            mPageNum++;
            // TODO: 17/4/21 搜索分页
        }
    }
}

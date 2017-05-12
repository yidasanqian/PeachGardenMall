package me.zoro.peachgardenmall.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.CommentRecyclerViewAdapter;
import me.zoro.peachgardenmall.datasource.GoodsDatasource;
import me.zoro.peachgardenmall.datasource.GoodsRepository;
import me.zoro.peachgardenmall.datasource.domain.Comment;
import me.zoro.peachgardenmall.datasource.remote.GoodsRemoteDatasource;
import me.zoro.peachgardenmall.fragment.HomeFragment;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class CommentActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private List<Comment> mComments;
    private LinearLayoutManager mLayoutManager;
    private CommentRecyclerViewAdapter mCommentRecyclerViewAdapter;
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
    /**
     * 从{@link GoodsDetailActivity}传过来的商品id
     */
    private int mGoodsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mGoodsId = getIntent().getIntExtra(HomeFragment.GOODS_ID_EXTRA, -1);


        mGoodsRepository = GoodsRepository.getInstance(GoodsRemoteDatasource.getInstance(
                getApplicationContext()
        ));

        // 获取商品评论列表
        new FetchCommensTask().execute();

        mComments = new ArrayList<>();
        mCommentRecyclerViewAdapter = new CommentRecyclerViewAdapter(this, mComments);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCommentRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                if (mLayoutManager.findLastVisibleItemPosition() ==
                        recyclerView.getAdapter().getItemCount() - 1 && !mIsLoadingMore && dy > 0) {
                    mIsLoadingMore = true;
                    mPageNum++;
                    new FetchCommensTask().execute();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private class FetchCommensTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Map<String, Integer> map = new HashMap<>();
            map.put("goodsId", mGoodsId);
            map.put("pn", mPageNum);
            map.put("ps", mPageSize);
            mGoodsRepository.getCommentByGoodsId(map, new GoodsDatasource.GetCommentsCallback() {
                @Override
                public void onCommentsLoaded(List<Comment> comments) {
                    if (comments.size() > 0) {
                        if (mPageNum > 1) {
                            mComments.addAll(comments);
                            mCommentRecyclerViewAdapter.appendData(comments);
                        } else {
                            mComments = comments;
                            mCommentRecyclerViewAdapter.replaceData(comments);
                        }

                        mIsLoadingMore = false;
                    }
                }

                @Override
                public void onDataNotAvailable(String msg) {
                    showMessage(msg);
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

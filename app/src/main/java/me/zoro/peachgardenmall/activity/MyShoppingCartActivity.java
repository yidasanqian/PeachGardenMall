package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.MyShoppingCartRecyclerViewAdapter;
import me.zoro.peachgardenmall.datasource.GoodsRepository;
import me.zoro.peachgardenmall.datasource.ShoppingCartDatasource;
import me.zoro.peachgardenmall.datasource.ShoppingCartRepository;
import me.zoro.peachgardenmall.datasource.domain.Cart;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.GoodsRemoteDatasource;
import me.zoro.peachgardenmall.datasource.remote.ShoppingCartRemoteDatasource;
import me.zoro.peachgardenmall.utils.CacheManager;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static me.zoro.peachgardenmall.activity.GoodsDetailActivity.ADDRESS_ID_EXTRA;

public class MyShoppingCartActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_right_txt)
    TextView mToolbarRightTxt;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.total_money_tv)
    TextView mTotalMoneyTv;
    @BindView(R.id.settlement_tv)
    TextView mSettlementTv;

    private LinearLayoutManager mLayoutManager;
    private MyShoppingCartRecyclerViewAdapter mRecyclerViewAdapter;
    private List<Cart> mCarts;
    /**
     * true,显示可选择，否则不显示可选择
     */
    private boolean mIsShowChecked;

    /**
     * 结算的总金额
     */
    private double mTotalMoney;

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
    private GoodsRepository mGoodsRepository;
    private ShoppingCartRepository mCartRepository;

    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shopping_cart);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mGoodsRepository = GoodsRepository.getInstance(GoodsRemoteDatasource.getInstance(
                getApplicationContext()
        ));

        mCartRepository = ShoppingCartRepository.getInstance(ShoppingCartRemoteDatasource.getInstance(
                getApplicationContext()
        ));

        mCarts = new ArrayList<Cart>();

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewAdapter =
                new MyShoppingCartRecyclerViewAdapter(this, mCarts, mIsShowChecked);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
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
                if (mLayoutManager.findLastVisibleItemPosition() == mLayoutManager.getItemCount() - 1 && !mIsLoadingMore && dy > 0) {
                    mIsLoadingMore = true;
                    mPageNum++;
                }
            }
        });
        mRecyclerViewAdapter.setOnAddSubtractClickListener(new MyShoppingCartRecyclerViewAdapter.OnAddSubtractClickListener() {
            @Override
            public void onAddSubtractClick(View view, double money, int count) {
                mTotalMoney += money;
                mTotalMoneyTv.setText(String.valueOf(mTotalMoney));
            }
        });

        mUserInfo = CacheManager.getUserInfoFromCache(this);
        if (mUserInfo != null) {
            new FetchGoodsesInShoppingCart().execute(mUserInfo.getUserId());
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void updateTotalMoney(double totalMoney) {
        mTotalMoney = totalMoney;
        mTotalMoneyTv.setText(String.valueOf(totalMoney));
    }


    @OnClick({R.id.toolbar_right_txt, R.id.settlement_tv})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right_txt:
                if (mCarts.size() > 0) {
                    if (mToolbarRightTxt.getText().equals(getString(R.string.edit_shopping_cart))) {
                        mIsShowChecked = true;
                        mToolbarRightTxt.setText(getString(R.string.delete_shopping_cart));
                    } else {
                        mIsShowChecked = false;
                        mToolbarRightTxt.setText(getString(R.string.edit_shopping_cart));
                        Map<Integer, Boolean> map = mRecyclerViewAdapter.getMap();
                        Iterator<Integer> iterator = map.keySet().iterator();
                        List<Integer> removeCartIds = new ArrayList<>();
                        while (iterator.hasNext() && mCarts.size() > 0) {
                            int pos = iterator.next();
                            if (map.get(pos)) {
                                Cart cart = mCarts.remove(pos);
                                removeCartIds.add(cart.getId());
                            }
                        }
                        if (removeCartIds.size() > 0) {
                            new DeleteGoodsFromShoppingCartTask().execute(removeCartIds);
                        }
                    }
                    mRecyclerViewAdapter.replaceData(mCarts, mIsShowChecked);
                }
                break;

            // TODO: 17/4/9 结算
            case R.id.settlement_tv:
                Intent intent = new Intent(this, CreateOrderActivity.class);
              /*  intent.putExtra(GOODS_EXTRA, mGoods);
                intent.putExtra(GOODS_SPEC_KEY_EXTRA, mKey);
                intent.putExtra(GOODS_COUNT_EXTRA, mGoodsCount);*/
                intent.putExtra(ADDRESS_ID_EXTRA, mUserInfo.getAddressId());
                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private class FetchGoodsesInShoppingCart extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            int userId = params[0];
            Map<String, Integer> map = new HashMap<>();
            map.put("userId", userId);
            map.put("pn", mPageNum);
            map.put("ps", mPageSize);
            mCartRepository.getShoppingCartGoodses(map, new ShoppingCartDatasource.GetShoppingCartGoodsesCallback() {
                @Override
                public void onGoodsesLoaded(List<Cart> carts) {
                    if (carts.size() > 0) {
                        if (mPageNum > 1) {
                            mCarts.addAll(carts);
                            mRecyclerViewAdapter.appendData(carts);
                        } else {
                            mCarts = carts;
                            mRecyclerViewAdapter.replaceData(carts, mIsShowChecked);
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

    private class DeleteGoodsFromShoppingCartTask extends AsyncTask<List<Integer>, Void, Void> {
        @Override
        protected Void doInBackground(List<Integer>... params) {
            List<Integer> cartIds = params[0];
            Map<String, Object> map = new HashMap<>();
            map.put("userId", mUserInfo.getUserId());
            map.put("cartIds", cartIds);
            mCartRepository.deleteFromShoppingCart(map, new ShoppingCartDatasource.DeleteFromShoppingCartCallback() {
                @Override
                public void onDeleteSuccess(String msg) {
                    showMessage(msg);
                    new FetchGoodsesInShoppingCart().execute(mUserInfo.getUserId());
                }

                @Override
                public void onDeleteFailure(String msg) {
                    showMessage(msg);
                }
            });
            return null;
        }
    }
}

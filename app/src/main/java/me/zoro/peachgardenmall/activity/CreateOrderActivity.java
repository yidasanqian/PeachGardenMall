package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.CreateOrderGoodsRecyclerViewAdapter;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.AddressDatasource;
import me.zoro.peachgardenmall.datasource.AddressRepository;
import me.zoro.peachgardenmall.datasource.domain.Address;
import me.zoro.peachgardenmall.datasource.domain.Cart;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.AddressRemoteDatasource;
import me.zoro.peachgardenmall.utils.CacheManager;
import me.zoro.peachgardenmall.view.RichText;

/**
 * Created by dengfengdecao on 17/4/10.
 */

public class CreateOrderActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_detail_address)
    TextView mTvDetailAddress;
    @BindView(R.id.address_info)
    RelativeLayout mAddressInfo;
    @BindView(R.id.tv_coupon)
    TextView mTvCoupon;
    @BindView(R.id.tv_coupon_count)
    RichText mTvCouponCount;
    @BindView(R.id.coupon_info)
    RelativeLayout mCouponInfo;
    @BindView(R.id.tv_goods_total)
    TextView mTvGoodsTotal;
    @BindView(R.id.tv_freight)
    TextView mTvFreight;
    @BindView(R.id.tv_activity_coupon)
    TextView mTvActivityCoupon;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_total_money)
    TextView mTvTotalMoney;
    @BindView(R.id.tv_settlement)
    TextView mTvSettlement;
    @BindView(R.id.tv_activity_coupon_desc)
    TextView mTvActivityCouponDesc;
    @BindView(R.id.activity_coupon_info)
    RelativeLayout mActivityCouponInfo;
    @BindView(R.id.tv_activity_coupon_lbl)
    TextView mTvActivityCouponLbl;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;

    private AddressRepository mAddressRepository;
    private Address mAddress;
    private int mAddrId;

    private List<Cart> mCarts;

    private Goods mGoods;
    /**
     * {@link GoodsDetailActivity#mKey}
     */
    private String mKey;


    private CreateOrderGoodsRecyclerViewAdapter mRecyclerViewAdapter;

    private Goods.SpecRelationEntity mSpecRelation;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mAddressRepository = AddressRepository.getInstance(AddressRemoteDatasource.getInstance(getApplicationContext()));

        mCarts = new ArrayList<>();

        mUserInfo = CacheManager.getUserInfoFromCache(CreateOrderActivity.this);

        mAddrId = getIntent().getIntExtra(GoodsDetailActivity.ADDRESS_ID_EXTRA, -1);
        mGoods = (Goods) getIntent().getSerializableExtra(GoodsDetailActivity.GOODS_EXTRA);
        mKey = getIntent().getStringExtra(GoodsDetailActivity.GOODS_SPEC_KEY_EXTRA);
        String count = getIntent().getStringExtra(GoodsDetailActivity.GOODS_COUNT_EXTRA);
        int goodsCount = Integer.parseInt(count);

        if (mUserInfo != null) {
            setLoadingIndicator(true);

            if (mGoods != null) {
                Cart cart = new Cart();
                cart.setUserId(mUserInfo.getUserId());
                cart.setGoodsId(mGoods.getGoodsId());
                cart.setGoodsName(mGoods.getGoodsName());
                cart.setMarketPrice(mGoods.getMarketPrice());
                cart.setGoodsNum(goodsCount);
                cart.setSpecKey(mKey);
                // 规则关系列表
                List<Goods.SpecRelationEntity> specRelationList = mGoods.getSpecRelation();
                /**
                 * 处理规则关系
                 */
                for (int j = 0; j < specRelationList.size(); j++) {
                    String key = specRelationList.get(j).getKey();
                    if (key.equals(mKey)) {
                        mSpecRelation = specRelationList.get(j);
                        cart.setGoodsPrice(mSpecRelation.getPrice());
                        cart.setSpecKeyName(mSpecRelation.getValue());
                        break;
                    }
                }
                cart.setImageUrl(mGoods.getOriginalImg());
                mCarts.add(cart);
            }
        } else {
            startActivity(new Intent(CreateOrderActivity.this, LoginActivity.class));
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new CreateOrderGoodsRecyclerViewAdapter(this, mCarts);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                //设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.loading);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }

    @OnClick({R.id.address_info, R.id.coupon_info, R.id.tv_settlement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.address_info:
                Intent intent = new Intent(this, DeliveryAddressActivity.class);
                startActivity(intent);
                break;
            // TODO: 17/5/2 优惠劵
            case R.id.coupon_info:
                intent = new Intent(this, CouponActivity.class);
                startActivity(intent);
                break;
            // TODO: 17/5/1 付款
            case R.id.tv_settlement:
                intent = new Intent(this, PayActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAddress == null && mUserInfo != null) {
            new FetchAddressByIdTask().execute(mAddrId);
        } else {
            updateAddressUI(mAddress);
        }

        // 商品合计
        final double[] totalPrice = new double[1];
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                totalPrice[0] = mRecyclerViewAdapter.getTotalPrice();
                if (totalPrice[0] == 0d) {
                    handler.postDelayed(this, 1000);
                } else {
                    mTvGoodsTotal.setText(String.valueOf(totalPrice[0]));
                    // 运费
                    double freight = Double.valueOf(mGoods.getFreight());
                    double freeFreight = Double.valueOf(mGoods.getFreeFreight());
                    if (mGoods.getIsFreeShipping()) {
                        if (totalPrice[0] >= freeFreight) {
                            freight = 0;
                        }
                    }
                    mTvFreight.setText(String.valueOf(freight));
                    // 实付金额
                    double totalMoney = totalPrice[0] + freight;

                    Goods.PromEntity promEntity = mGoods.getProm();
                    double promotionMoney = Double.parseDouble(promEntity.getFullMoney());
                    mTvActivityCoupon.setText(promEntity.getFullMoney());
                    // 如果商品合计金额不小于促销活动金额，则实付金额需要减去优惠活动金额。否则隐藏活动信息
                    if (totalPrice[0] >= promotionMoney) {
                        totalMoney -= promotionMoney;
                    } else {
                        mActivityCouponInfo.setVisibility(View.GONE);
                    }
                    mTvTotalMoney.setText(String.valueOf(totalMoney));

                    setLoadingIndicator(false);
                }
            }
        }, 1000);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private class FetchAddressByIdTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            int addrId = params[0];
            Map<String, Integer> map = new HashMap<>();
            map.put("addressId", addrId);
            map.put("userId", mUserInfo.getUserId());
            mAddressRepository.getById(map, new AddressDatasource.GetByIdCallback() {
                @Override
                public void onAddressLoaded(final Address address) {
                    mAddress = address;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateAddressUI(address);
                        }
                    });
                }

                @Override
                public void onDataNotAvoidable() {
                    showMessage(Const.SERVER_AVALIABLE);
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

    private void updateAddressUI(Address address) {
        mTvName.setText(address.getConsignee());
        mTvPhone.setText(address.getMobile());
        mTvDetailAddress.setText(address.getAddress());
    }
}

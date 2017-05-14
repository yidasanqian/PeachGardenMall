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
import android.text.TextUtils;
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
import me.zoro.peachgardenmall.datasource.GoodsDatasource;
import me.zoro.peachgardenmall.datasource.GoodsRepository;
import me.zoro.peachgardenmall.datasource.domain.Address;
import me.zoro.peachgardenmall.datasource.domain.Cart;
import me.zoro.peachgardenmall.datasource.domain.Freight;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.domain.Order;
import me.zoro.peachgardenmall.datasource.domain.Promotion;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.AddressRemoteDatasource;
import me.zoro.peachgardenmall.datasource.remote.GoodsRemoteDatasource;
import me.zoro.peachgardenmall.utils.CacheManager;
import me.zoro.peachgardenmall.view.RichText;

/**
 * Created by dengfengdecao on 17/4/10.
 */

public class CreateOrderActivity extends AppCompatActivity {

    public static final int UPDATE_ADDRESS_REQUEST = 1;
    public static final String ORDER_EXTRA = "order";
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

    private ArrayList<Promotion> mPromotions;
    private ArrayList<Cart> mCarts;

    private Goods mGoods;
    /**
     * {@link GoodsDetailActivity#mKey}
     */
    private String mKey;

    /**
     * 地址id
     */
    private int mAddressId;
    private Address mAddress;

    /**
     * 运费
     */
    private double mFreight;

    /**
     * 促销活动选择优惠活动后减去的金额
     */
    private double mExpression;
    /**
     * 选择的促销活动id
     */
    private int mSelectedPromotionId;
    /**
     * 实际付款金额
     */
    private double mFactPayMoney;

    private CreateOrderGoodsRecyclerViewAdapter mRecyclerViewAdapter;

    private Goods.SpecRelationEntity mSpecRelation;
    private GoodsRepository mGoodsRepository;
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
        mGoodsRepository = GoodsRepository.getInstance(GoodsRemoteDatasource.getInstance(
                getApplicationContext()
        ));
        mUserInfo = CacheManager.getUserInfoFromCache(CreateOrderActivity.this);

        mCarts = (ArrayList<Cart>) getIntent().getSerializableExtra(MyShoppingCartActivity.CARTS_EXTRA);
        if (mCarts == null) {
            mCarts = new ArrayList<>();
        }
        mPromotions = new ArrayList<>();

        mGoods = (Goods) getIntent().getSerializableExtra(GoodsDetailActivity.GOODS_EXTRA);
        mKey = getIntent().getStringExtra(GoodsDetailActivity.GOODS_SPEC_KEY_EXTRA);
        String count = getIntent().getStringExtra(GoodsDetailActivity.GOODS_COUNT_EXTRA);
        int goodsCount = 1;
        if (!TextUtils.isEmpty(count)) {
            goodsCount = Integer.parseInt(count);
        }

        mTvSettlement.setEnabled(false);
        if (mUserInfo != null) {
            setLoadingIndicator(true);

            // 获取地址
            mAddressId = mUserInfo.getAddressId();
            new FetchAddressByIdTask().execute(mAddressId);

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

        // 获取运费
        new FetchFreightTask().execute();
    }

    private void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                // 设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.loading);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
                mTvSettlement.setEnabled(true);
            }
        }
    }

    @OnClick({R.id.address_info, R.id.coupon_info, R.id.tv_settlement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.address_info:
                Intent intent = new Intent(this, DeliveryAddressActivity.class);
                startActivityForResult(intent, UPDATE_ADDRESS_REQUEST);
                break;
            // TODO: 17/5/2 优惠劵
            case R.id.coupon_info:
                intent = new Intent(this, CouponActivity.class);
                startActivity(intent);
                break;
            // 付款
            case R.id.tv_settlement:
                if (mUserInfo != null && !mProgressBarContainer.isShown()) {
                    Order order = new Order();
                    order.setUserId(mUserInfo.getUserId());
                    order.setAddressObj(mAddress);
                    order.setAddressId(mAddressId);
                    order.setFreight(mFreight);
                    order.setPromotionMoney(mExpression);
                    /**
                     * 促销的id数组(目前只有一个)
                     */
                    List<Integer> promotionIds = new ArrayList<>();
                    promotionIds.add(mSelectedPromotionId);
                    order.setPromotionIds(promotionIds);
                    order.setGoodsInfo(mRecyclerViewAdapter.getGoodsInfoses());
                    order.setFactPayMoney(mFactPayMoney);
                    order.setPayType(1);
                    intent = new Intent(this, PayActivity.class);
                    intent.putExtra(ORDER_EXTRA, order);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_ADDRESS_REQUEST && resultCode == RESULT_OK) {
            Address address = (Address) data.getSerializableExtra(DeliveryAddressActivity.DEFAULT_ADDRESS_EXTRA);
            updateAddressUI(address);
            // new FetchAddressByIdTask().execute(address.getId());
        }
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateAddressUI(address);
                        }
                    });
                }

                @Override
                public void onDataNotAvoidable() {
                    showMessage(Const.SERVER_UNAVAILABLE);
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
        mAddress = address;
        mTvName.setText(address.getConsignee());
        mTvPhone.setText(address.getMobile());
        mTvDetailAddress.setText(address.getAddress());
    }

    private class FetchFreightTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            mGoodsRepository.getFreight(new GoodsDatasource.GetFreightCallback() {
                @Override
                public void onFreightLoaded(final Freight freight) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateFreightUI(freight);
                        }
                    });
                }

                @Override
                public void onDataNotAvailable(String msg) {
                    showMessage(msg);
                }
            });
            return null;
        }
    }

    private void updateFreightUI(final Freight freight) {
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
                    handler.removeCallbacks(this);

                    // 运费
                    mFreight = Double.valueOf(freight.getFreight());
                    double dFreeFreight = Double.valueOf(freight.getFreightFree());
                    if (freight.isIsFree()) {
                        if (totalPrice[0] >= dFreeFreight) {
                            mFreight = 0;
                        }
                    }
                    mTvFreight.setText(String.valueOf(mFreight));
                    double dTotalPrice = totalPrice[0];
                    // 实付金额
                    double totalMoney = dTotalPrice + mFreight;
                    /**
                     * 从 {@link GoodsDetailActivity}跳转过来,则带有{@link Goods} 实体，否则从{@link MyShoppingCartActivity}
                     * 跳转过来
                     */
                    if (mGoods != null) {

                        List<Promotion> promotions = mGoods.getProm();
                        updateTotalPriceAndTotalMoneyUI(dTotalPrice, totalMoney, promotions);
                    } else {
                        // 获取活动信息
                        new FetchPromotionTask(dTotalPrice, totalMoney).execute();
                    }

                    setLoadingIndicator(false);
                }
            }
        }, 1000);


    }

    /**
     * 更新商品合计和实付金额UI
     *
     * @param dTotalPrice 商品合计金额
     * @param totalMoney  实付金额
     * @param promotions  促销信息
     */
    private void updateTotalPriceAndTotalMoneyUI(double dTotalPrice, double totalMoney, List<Promotion> promotions) {
        double maxPromotionMoney = 0, promotionMoney = 0;

        /**
         * 选择最优的优惠活动
         */
        Promotion selectedPromotion = null;
        if (promotions != null && promotions.size() > 0) {
            for (int i = 0; i < promotions.size(); i++) {
                Promotion promotion = promotions.get(i);
                promotionMoney = Double.parseDouble(promotion.getFullMoney());
                if (promotionMoney > maxPromotionMoney && dTotalPrice >= promotionMoney) {
                    maxPromotionMoney = promotionMoney;
                    selectedPromotion = promotion;
                }

            }

            if (selectedPromotion != null) {
                mSelectedPromotionId = selectedPromotion.getId();
                mExpression = Double.valueOf(selectedPromotion.getExpression());
                mTvActivityCouponDesc.setText(selectedPromotion.getName());
                mTvActivityCoupon.setText(selectedPromotion.getExpression());
            }
        }

        // 如果商品合计金额不小于促销活动金额，则实付金额需要减去优惠活动金额。否则隐藏活动信息
        if (dTotalPrice >= maxPromotionMoney) {
            totalMoney -= mExpression;
        } else {
            mActivityCouponInfo.setVisibility(View.GONE);
        }
        mTvGoodsTotal.setText(String.valueOf(dTotalPrice));
        mFactPayMoney = totalMoney;
        mTvTotalMoney.setText(String.valueOf(totalMoney));
    }

    private class FetchPromotionTask extends AsyncTask<Void, Void, Void> {
        private double totalPrice;
        private double totalMoney;

        public FetchPromotionTask(double totalPrice, double totalMoney) {
            this.totalPrice = totalPrice;
            this.totalMoney = totalMoney;
        }

        @Override
        protected Void doInBackground(Void... params) {
            mGoodsRepository.getPromotion(new GoodsDatasource.GetPromotionsCallback() {
                @Override
                public void onPromotionsLoaded(final ArrayList<Promotion> promotions) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updatePromotionUI(totalPrice, totalMoney, promotions);
                        }
                    });
                }

                @Override
                public void onDataNotAvailable(String msg) {

                }
            });
            return null;
        }
    }

    private void updatePromotionUI(double totalPrice, double totalMoney, ArrayList<Promotion> promotions) {
        updateTotalPriceAndTotalMoneyUI(totalPrice, totalMoney, promotions);
    }
}

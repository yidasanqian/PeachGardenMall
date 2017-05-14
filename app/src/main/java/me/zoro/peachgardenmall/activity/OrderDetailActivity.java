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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import me.zoro.peachgardenmall.datasource.OrderDatasource;
import me.zoro.peachgardenmall.datasource.OrderRepository;
import me.zoro.peachgardenmall.datasource.domain.Address;
import me.zoro.peachgardenmall.datasource.domain.Cart;
import me.zoro.peachgardenmall.datasource.domain.Order;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.OrderRemoteDatasource;
import me.zoro.peachgardenmall.utils.CacheManager;

/**
 * Created by dengfengdecao on 17/5/12.
 */

public class OrderDetailActivity extends AppCompatActivity {
    public static final String ORDER_EXTRA = "order";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_contact_phone)
    TextView mTvContactPhone;
    @BindView(R.id.tv_contact_address)
    TextView mTvContactAddress;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_freight)
    TextView mTvFreight;
    @BindView(R.id.tv_fact_pay)
    TextView mTvFactPay;
    @BindView(R.id.btn_order_action)
    Button mBtnOrderAction;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private CreateOrderGoodsRecyclerViewAdapter mRecyclerViewAdapter;
    private List<Cart> mCarts;
    private OrderRepository mOrderRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mOrderRepository = OrderRepository.getInstance(OrderRemoteDatasource.getInstance(
                getApplicationContext()
        ));

        String outTraceNo = getIntent().getStringExtra(PaymentSuccessActivity.ORDER_TRACE_NO_EXTRA);
        UserInfo userInfo = CacheManager.getUserInfoFromCache(this);
        if (userInfo != null) {
            setLoadingIndicator(true);
            new FetchOrderByTraceNoTask(userInfo.getUserId()).execute(outTraceNo);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        mCarts = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new CreateOrderGoodsRecyclerViewAdapter(this, mCarts);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private class FetchOrderByTraceNoTask extends AsyncTask<String, Void, Void> {
        private int userId;

        public FetchOrderByTraceNoTask(int userId) {
            this.userId = userId;
        }

        @Override
        protected Void doInBackground(String... params) {
            String traceNo = params[0];
            Map<String, Object> reqParams = new HashMap<>();
            reqParams.put("userId", userId);
            reqParams.put("orderSn", traceNo);
            mOrderRepository.getOrderDetail(reqParams, new OrderDatasource.GetOrderCallback() {
                @Override
                public void onOrderLoaded(final Order order) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            invalidateUI(order);
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

    private void showMessage(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
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
            }
        }
    }

    private void invalidateUI(Order order) {
        mTvNickname.setText(order.getConsignee());
        mTvContactPhone.setText(order.getMobile());
        mTvContactAddress.setText(order.getAddressStr());
        mTvFreight.setText(order.getShippingPrice());
        mTvFactPay.setText(order.getTotalAmount());

        // 处理活动字段
        order.setPromotionMoney(Double.parseDouble(order.getOrderPromAmount()));
        List<Integer> promotionIds = new ArrayList<>();
        promotionIds.add(order.getOrderPromId());
        order.setPromotionIds(promotionIds);
        // 处理实际付款金额字段
        order.setFactPayMoney(Double.parseDouble(order.getTotalAmount()));
        // 处理联系地址字段
        Address addressObj = new Address();
        addressObj.setConsignee(order.getConsignee());
        addressObj.setMobile(order.getMobile());
        addressObj.setAddress(order.getAddressStr());

        List<Order.GoodsInfo> goodsInfoses = order.getGoodsInfo();
        int goodsSize = goodsInfoses.size();
        for (int i = 0; i < goodsSize; i++) {
            Cart cart = new Cart();
            Order.GoodsInfo goodsInfo = goodsInfoses.get(i);
            cart.setUserId(order.getUserId());
            cart.setGoodsId(goodsInfo.getGoodsId());
            cart.setGoodsSn(goodsInfo.getGoodsSn());
            cart.setGoodsName(goodsInfo.getGoodsName());
            cart.setMarketPrice(goodsInfo.getMarketPrice());
            cart.setGoodsPrice(goodsInfo.getGoodsPrice());
            //cart.setMemberGoodsPrice(goodsInfos.getMemberGoodsPrice());
            cart.setGoodsNum(goodsInfo.getGoodsNum());
            cart.setSpecKey(goodsInfo.getSpecKey());
            cart.setSpecKeyName(goodsInfo.getSpecKeyName());
            cart.setAddTime(order.getAddTime());
            cart.setPromId(goodsInfo.getPromId());
            cart.setPromType(goodsInfo.getPromType());
            cart.setImageUrl(goodsInfo.getGoodsImsg());
            cart.setFreight(order.getShippingPrice());
            mCarts.add(cart);
        }

        mRecyclerViewAdapter.replaceData(mCarts);

        mBtnOrderAction.setTag(order);
        int orderType = order.getOrderType();
        if (orderType == MyOrderActivity.PENDING_PAYMENT) {
            mBtnOrderAction.setText(R.string.payment);
        } else if (orderType == MyOrderActivity.PENDING_DELIVERY) {
            mBtnOrderAction.setText(R.string.pending_delivery);
        } else if (orderType == MyOrderActivity.PENDING_RECEIVING) {
            mBtnOrderAction.setText(R.string.confirm_receive);
        } else if (orderType == MyOrderActivity.PENDING_EVALUATE) {
            mBtnOrderAction.setText(R.string.evaluate);
        }

        setLoadingIndicator(false);
    }

    @OnClick(R.id.btn_order_action)
    public void onViewClicked() {
        final Order order = (Order) mBtnOrderAction.getTag();
        int orderType = order.getOrderType();
        if (orderType == MyOrderActivity.PENDING_PAYMENT) {
            // 去付款
            Intent intent = new Intent(this, PayActivity.class);
            intent.putExtra(OrderDetailActivity.ORDER_EXTRA, order);
            startActivity(intent);
        } else if (orderType == MyOrderActivity.PENDING_DELIVERY) {
            // todo 提醒发货
            mBtnOrderAction.setText(R.string.pending_delivery);
        } else if (orderType == MyOrderActivity.PENDING_RECEIVING) {
            // todo 确认收货
            setLoadingIndicator(true);
            Map<String, Object> reqParams = new HashMap<>();
            reqParams.put("userId", order.getUserId());
            reqParams.put("out_trade_no", order.getOutTraceNo());
            mOrderRepository.updateOrderStatus(reqParams, new OrderDatasource.UpdateOrderStatusCallback() {
                @Override
                public void onUpdateSuccess() {
                    setLoadingIndicator(false);

                    showMessage(getString(R.string.confirm_receive_success));
                    new FetchOrderByTraceNoTask(order.getUserId()).execute(order.getOutTraceNo());
                }

                @Override
                public void onUpdateFailure(String msg) {
                    setLoadingIndicator(false);

                    showMessage(msg);
                }
            });
        } else if (orderType == MyOrderActivity.PENDING_EVALUATE) {
            // 去评价
            Intent intent = new Intent(this, PublishCommentActivity.class);
            startActivity(intent);
        }
    }
}

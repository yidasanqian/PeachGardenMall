package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.AddressDatasource;
import me.zoro.peachgardenmall.datasource.AddressRepository;
import me.zoro.peachgardenmall.datasource.domain.Address;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.AddressRemoteDatasource;
import me.zoro.peachgardenmall.utils.CacheManager;
import me.zoro.peachgardenmall.utils.PreferencesUtil;
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
    @BindView(R.id.goods_img_iv)
    ImageView mGoodsImgIv;
    @BindView(R.id.goods_name_tv)
    TextView mGoodsNameTv;
    @BindView(R.id.goods_count_tv)
    TextView mGoodsCountTv;
    @BindView(R.id.goods_extra_info_tv)
    TextView mGoodsExtraInfoTv;
    @BindView(R.id.goods_money_tv)
    TextView mGoodsMoneyTv;
    @BindView(R.id.goods_strike_money_tv)
    TextView mGoodsStrikeMoneyTv;
    @BindView(R.id.goods_info)
    RelativeLayout mGoodsInfo;
    @BindView(R.id.tv_total_money)
    TextView mTvTotalMoney;
    @BindView(R.id.tv_settlement)
    TextView mTvSettlement;

    private AddressRepository mAddressRepository;
    private Address mAddress;
    private int mAddrId;

    private Goods mGoods;
    /**
     * {@link GoodsDetailActivity#mKey}
     */
    private String mKey;

    private int mGoodsCount;
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

        mAddrId = getIntent().getIntExtra(GoodsDetailActivity.ADDRESS_ID_EXTRA, -1);

        mGoods = (Goods) getIntent().getSerializableExtra(GoodsDetailActivity.GOODS_EXTRA);
        mKey = getIntent().getStringExtra(GoodsDetailActivity.GOODS_SPEC_KEY_EXTRA);
        String count = getIntent().getStringExtra(GoodsDetailActivity.GOODS_COUNT_EXTRA);
        mGoodsCount = Integer.parseInt(count);


        mGoodsNameTv.setText(mGoods.getGoodsName());
        mGoodsCountTv.setText(count);
        mGoodsMoneyTv.setText(mGoods.getPrice());
        Picasso.with(this)
                .load(mGoods.getOriginalImg())
                .into(mGoodsImgIv);

        // 规则关系列表
        List<Goods.SpecRelationEntity> specRelationList = mGoods.getSpecRelation();
        /**
         * 处理规则关系
         */
        for (int j = 0; j < specRelationList.size(); j++) {
            String key = specRelationList.get(j).getKey();
            if (key.equals(mKey)) {
                double money = Double.parseDouble(specRelationList.get(j).getPrice());
                double price = money * mGoodsCount;
                mGoodsExtraInfoTv.setText(specRelationList.get(j).getValue());
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
        if (mAddress == null) {
            new FetchAddressByIdTask().execute(mAddrId);
        } else {
            updateAddressUI(mAddress);
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
            UserInfo userInfo = (UserInfo) CacheManager.getInstance().get(Const.USER_INFO_CACHE_KEY);
            if (userInfo == null) {
                userInfo = PreferencesUtil.getUserInfoFromPref(CreateOrderActivity.this);
            }
            if (userInfo == null) {
                startActivity(new Intent(CreateOrderActivity.this, LoginActivity.class));
                return null;
            }
            int addrId = params[0];
            Map<String, Integer> map = new HashMap<>();
            map.put("addressId", addrId);
            map.put("userId", userInfo.getUserId());
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

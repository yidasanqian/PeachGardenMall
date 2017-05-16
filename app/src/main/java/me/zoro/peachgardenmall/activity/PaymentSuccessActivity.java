package me.zoro.peachgardenmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.datasource.domain.Address;
import me.zoro.peachgardenmall.datasource.domain.Order;

/**
 * Created by dengfengdecao on 17/5/12.
 */

public class PaymentSuccessActivity extends AppCompatActivity {
    public static final String ORDER_ID_EXTRA = "order_id";
    public static final String ORDER_TRACE_NO_EXTRA = "order_trace_no";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_contact_phone)
    TextView mTvContactPhone;
    @BindView(R.id.tv_contact_address)
    TextView mTvContactAddress;
    @BindView(R.id.tv_fact_pay)
    TextView mTvFactPay;
    @BindView(R.id.btn_check_the_order)
    Button mBtnCheckTheOrder;
    @BindView(R.id.btn_to_home)
    Button mBtnToHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        Order order = (Order) getIntent().getSerializableExtra(PayActivity.ORDER_DETAIL_EXTRA);
        if (order != null) {
            // 处理联系地址字段
            Address addressObj = new Address();
            addressObj.setConsignee(order.getConsignee());
            addressObj.setMobile(order.getMobile());
            addressObj.setAddress(order.getAddressStr());
            order.setAddressObj(addressObj);
            mTvNickname.setText(order.getAddressObj().getConsignee());
            mTvContactPhone.setText(order.getAddressObj().getMobile());
            mTvContactAddress.setText(order.getAddressObj().getAddress());
            mTvFactPay.setText(String.valueOf(order.getFactPayMoney()));

            mBtnCheckTheOrder.setTag(order.getOutTraceNo());
        }

    }

    @OnClick({R.id.btn_check_the_order, R.id.btn_to_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_check_the_order:
                String outTraceNo = (String) mBtnCheckTheOrder.getTag();
                Intent intent = new Intent(this, OrderDetailActivity.class);
                intent.putExtra(ORDER_TRACE_NO_EXTRA, outTraceNo);
                startActivity(intent);
                break;
            case R.id.btn_to_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

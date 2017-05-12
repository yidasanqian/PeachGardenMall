package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.MyOrderFragmentPagerAdapter;
import me.zoro.peachgardenmall.fragment.OrderTabFragment;
import me.zoro.peachgardenmall.fragment.PendingOrderTabFragment;

/**
 * Created by dengfengdecao on 17/4/9.
 */

public class MyOrderActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    /**
     * 订单类型:0所有订单 1待付款 2待发货 3待收货 4待评价
     */
    public static final int ALL_ORDER = 0;
    public static final int PENDING_PAYMENT = 1;
    public static final int PENDING_DELIVERY = 2;
    public static final int PENDING_RECEIVING = 3;
    public static final int PENDING_EVALUATE = 4;

    public static final String ORDER_TYPE_KEY = "order_type";


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.my_order_tl)
    TabLayout mMyOrderTl;
    @BindView(R.id.my_order_vp)
    ViewPager mMyOrderVp;

    private MyOrderFragmentPagerAdapter mMyOrderFragmentPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        mSearchView.setOnQueryTextListener(this);

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(OrderTabFragment.newInstance(ALL_ORDER));
        fragments.add(PendingOrderTabFragment.newInstance(PENDING_PAYMENT));
        fragments.add(PendingOrderTabFragment.newInstance(PENDING_DELIVERY));
        fragments.add(PendingOrderTabFragment.newInstance(PENDING_RECEIVING));
        fragments.add(PendingOrderTabFragment.newInstance(PENDING_EVALUATE));
        mMyOrderFragmentPagerAdapter = new MyOrderFragmentPagerAdapter(getSupportFragmentManager(), this, fragments);
        mMyOrderVp.setAdapter(mMyOrderFragmentPagerAdapter);
        mMyOrderTl.setupWithViewPager(mMyOrderVp);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO: 17/4/22 搜索订单
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}

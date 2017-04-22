package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.MyOrderFragmentPagerAdapter;

/**
 * Created by dengfengdecao on 17/4/9.
 */

public class MyOrderActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

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

        mMyOrderFragmentPagerAdapter = new MyOrderFragmentPagerAdapter(getSupportFragmentManager(), this);
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

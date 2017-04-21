package me.zoro.peachgardenmall.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.MyShoppingCartRecyclerViewAdapter;
import me.zoro.peachgardenmall.datasource.domain.Goods;

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

    private MyShoppingCartRecyclerViewAdapter mRecyclerViewAdapter;
    private List<Goods> mGoodses;
    /**
     * true,显示可选择，否则不显示可选择
     */
    private boolean mIsShowChecked;

    private double mTotalMoney;


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

        // TODO: 17/4/9 获取购物车的商品信息列表
        mGoodses = new ArrayList<Goods>();
        Goods goods = new Goods();
        Goods goods1 = new Goods();
        goods.setGoodsName("高圆圆");
        goods.setPrice(String.valueOf(90));
        goods1.setGoodsName("高圆圆1");
        goods1.setPrice(String.valueOf(80));
        mGoodses.add(goods);
        mGoodses.add(goods1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter =
                new MyShoppingCartRecyclerViewAdapter(this, mGoodses, mIsShowChecked);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerViewAdapter.setOnAddSubtractClickListener(new MyShoppingCartRecyclerViewAdapter.OnAddSubtractClickListener() {
            @Override
            public void onAddSubtractClick(View view, double money, int count) {
                mTotalMoney += money;
                mTotalMoneyTv.setText(String.valueOf(mTotalMoney));
            }
        });

    }

    public void updateTotalMoney(double totalMoney) {
        mTotalMoney = totalMoney;
        mTotalMoneyTv.setText(String.valueOf(totalMoney));
    }


    @OnClick({R.id.toolbar_right_txt, R.id.settlement_tv})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right_txt:
                if (mGoodses.size() > 0) {
                    if (mToolbarRightTxt.getText().equals(getString(R.string.edit_shopping_cart))) {
                        mIsShowChecked = true;
                        mToolbarRightTxt.setText(getString(R.string.delete_shopping_cart));
                    } else {
                        mIsShowChecked = false;
                        mToolbarRightTxt.setText(getString(R.string.edit_shopping_cart));
                        Map<Integer, Boolean> map = mRecyclerViewAdapter.getMap();
                        Iterator<Integer> iterator = map.keySet().iterator();
                        while (iterator.hasNext() && mGoodses.size() > 0) {
                            int pos = iterator.next();
                            if (map.get(pos)) {
                                mGoodses.remove(pos);
                            }
                        }
                    }
                    mRecyclerViewAdapter.replaceData(mGoodses, mIsShowChecked);
                }
                break;

            // TODO: 17/4/9 结算
            case R.id.settlement_tv:
                break;
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

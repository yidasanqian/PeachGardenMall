package me.zoro.peachgardenmall.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.GoodsFilterAdapter;

public class GoodsListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GoodsListActivity";
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.tb_comprehensive_sorting)
    Button mTbComprehensiveSorting;
    @BindView(R.id.tb_sales_sorting)
    Button mTbSalesSorting;
    @BindView(R.id.btn_filter)
    Button mBtnFilter;
    @BindView(R.id.grid_view)
    GridView mGridView;

    Button mBtnPromotion;
    Button mBtnSelfSupport;
    RecyclerView mLeftRecyclerView;
    RecyclerView mRightRecyclerView;

    TextView mTvGoodsCount;
    TextView mTvReset;
    TextView mTvConfirm;


    private PopupWindow mPopupWindow;
    private String[] mCategory = new String[]{"直辖市", "特别行政区", "黑龙江"};
    private String[][] mRightCategory = new String[][]{{"北京", "上海", "天津", "重庆"}, {"香港", "澳门"},
            {"哈尔滨", "齐齐哈尔", "牡丹江", "大庆", "伊春", "双鸭山", "鹤岗", "鸡西", "佳木斯", "七台河", "黑河", "绥化", "大兴安岭"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);

        initPopupWindow();
    }

    private void initPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.filter_content, null);
        mBtnPromotion = (Button) contentView.findViewById(R.id.btn_promotion);
        mBtnSelfSupport = (Button) contentView.findViewById(R.id.btn_self_support);
        mLeftRecyclerView = (RecyclerView) contentView.findViewById(R.id.left_recycler_view);
        mRightRecyclerView = (RecyclerView) contentView.findViewById(R.id.right_recycler_view);
        mTvGoodsCount = (TextView) contentView.findViewById(R.id.tv_goods_count);
        mTvReset = (TextView) contentView.findViewById(R.id.tv_reset);
        mTvConfirm = (TextView) contentView.findViewById(R.id.tv_confirm);


        mBtnPromotion.setOnClickListener(this);
        mBtnSelfSupport.setOnClickListener(this);
        mTvReset.setOnClickListener(this);
        mTvConfirm.setOnClickListener(this);


        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        GoodsFilterAdapter leftAdapter = new GoodsFilterAdapter(this, mCategory);
        mLeftRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLeftRecyclerView.setAdapter(leftAdapter);

        mRightRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRightRecyclerView.setVisibility(View.GONE);
        leftAdapter.setOnItemClickListener(new GoodsFilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showMessage(mCategory[position]);
                mRightRecyclerView.setVisibility(View.VISIBLE);
                final String[] secondLevel = mRightCategory[position];
                GoodsFilterAdapter rightAdapter = new GoodsFilterAdapter(GoodsListActivity.this, secondLevel);
                mRightRecyclerView.setAdapter(rightAdapter);
                rightAdapter.setOnItemClickListener(new GoodsFilterAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        showMessage(secondLevel[position]);
                    }
                });
            }
        });

    }

    private void showMessage(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.tb_comprehensive_sorting, R.id.tb_sales_sorting, R.id.btn_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // TODO: 17/4/13 商品排序
            case R.id.tb_comprehensive_sorting:
                break;
            case R.id.tb_sales_sorting:
                break;
            case R.id.btn_filter:
                mPopupWindow.showAsDropDown(mBtnFilter);
                break;

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_promotion:
                Log.d(TAG, "onClick: 促销");
                break;
            case R.id.btn_self_support:
                Log.d(TAG, "onClick: 自营");
                break;
            case R.id.tv_reset:
                Log.d(TAG, "onClick: 重置");
                break;
            case R.id.tv_confirm:
                Log.d(TAG, "onClick: 确定");
                break;
        }

    }
}

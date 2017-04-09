package me.zoro.peachgardenmall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.MyShoppingCartRecyclerViewAdapter;
import me.zoro.peachgardenmall.model.Goods;

/**
 *
 */
public class MyShoppingCartFragment extends Fragment {
    private static final String TAG = "MyShoppingCartFragment";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.total_money_tv)
    TextView mTotalMoneyTv;
    @BindView(R.id.settlement_tv)
    TextView mSettlementTv;
    Unbinder unbinder;

    public MyShoppingCartFragment() {
        // Required empty public constructor
    }


    public static MyShoppingCartFragment newInstance() {
        MyShoppingCartFragment fragment = new MyShoppingCartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_shopping_cart, container, false);
        unbinder = ButterKnife.bind(this, root);

        // TODO: 17/4/9 获取购物车的商品信息列表
        List<Goods> goodses = new ArrayList<Goods>();
        Goods goods = new Goods();
        goods.setName("高圆圆");
        goodses.add(goods);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyShoppingCartRecyclerViewAdapter recyclerViewAdapter =
                new MyShoppingCartRecyclerViewAdapter(getContext(), goodses);
        mRecyclerView.setAdapter(recyclerViewAdapter);

        return root;
    }


    @OnClick(R.id.settlement_tv)
    public void onViewClicked() {
        // TODO: 17/4/9 结算
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

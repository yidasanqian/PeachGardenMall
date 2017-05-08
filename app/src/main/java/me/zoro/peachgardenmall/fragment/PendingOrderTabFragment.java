package me.zoro.peachgardenmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.PendingOrderRecyclerViewAdapter;
import me.zoro.peachgardenmall.datasource.domain.Order;

/**
 * 待付款，待发货，待收货，待评价订单的fragment
 * Created by dengfengdecao on 17/5/8.
 */

public class PendingOrderTabFragment extends Fragment {

    private static final String TAG = "PendingOrderTabFragment";
    private static final String PENDING_TAB_KEY = "pending_tab";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    private PendingOrderRecyclerViewAdapter mRecyclerViewAdapter;
    private List<Order> mOrders;

    private String mPendingTab;

    public static PendingOrderTabFragment newInstance(String pendingTab) {

        Bundle args = new Bundle();
        args.putString(PENDING_TAB_KEY, pendingTab);
        PendingOrderTabFragment fragment = new PendingOrderTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPendingTab = getArguments().getString(PENDING_TAB_KEY);
        }
        mOrders = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_list, container, false);
        unbinder = ButterKnife.bind(this, root);

        mRecyclerViewAdapter = new PendingOrderRecyclerViewAdapter(getContext(), mOrders);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        Log.d(TAG, "onCreateView: mPendingTab ==> " + mPendingTab);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

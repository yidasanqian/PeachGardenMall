package me.zoro.peachgardenmall.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.PendingOrderRecyclerViewAdapter;
import me.zoro.peachgardenmall.datasource.OrderDatasource;
import me.zoro.peachgardenmall.datasource.OrderRepository;
import me.zoro.peachgardenmall.datasource.domain.Order;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.datasource.remote.OrderRemoteDatasource;
import me.zoro.peachgardenmall.utils.CacheManager;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static me.zoro.peachgardenmall.activity.MyOrderActivity.ORDER_TYPE_KEY;

/**
 * 待付款，待发货，待收货，待评价订单的fragment
 * Created by dengfengdecao on 17/5/8.
 */

public class PendingOrderTabFragment extends Fragment {

    private static final String TAG = "PendingOrderTabFragment";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    private OrderRepository mOrderRepository;


    private LinearLayoutManager mLayoutManager;
    private PendingOrderRecyclerViewAdapter mRecyclerViewAdapter;
    private ArrayList<Order> mOrders;


    /**
     * 订单类型:0所有订单 1待付款 2待发货 3待收货 4待评价
     */
    private int mOrderType;

    /**
     * 是否正在加载更多，true，表示正在加载，false，则不是
     */
    private boolean mIsLoadingMore;
    /**
     * 默认获取第一页
     */
    private int mPageNum = 1;
    /**
     * 默认获取10条
     */
    private int mPageSize = 10;

    private UserInfo mUserInfo;

    public static PendingOrderTabFragment newInstance(int orderType) {

        Bundle args = new Bundle();
        args.putInt(ORDER_TYPE_KEY, orderType);
        PendingOrderTabFragment fragment = new PendingOrderTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrderType = getArguments().getInt(ORDER_TYPE_KEY);
        }

        mOrderRepository = OrderRepository.getInstance(OrderRemoteDatasource.getInstance(
                getContext().getApplicationContext()
        ));

        mOrders = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_list, container, false);
        unbinder = ButterKnife.bind(this, root);

        mRecyclerViewAdapter = new PendingOrderRecyclerViewAdapter(getContext(), mOrders);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (SCROLL_STATE_IDLE == newState && recyclerView.getAdapter().getItemCount() <= mPageSize) {
                    mIsLoadingMore = false;
                    mPageNum = 1;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
// 上拉加载
                if (mLayoutManager.findLastVisibleItemPosition() ==
                        recyclerView.getAdapter().getItemCount() - 1 && !mIsLoadingMore && mUserInfo != null && dy > 0) {
                    mIsLoadingMore = true;
                    mPageNum++;
                    new FetchOrdersTask().execute();
                }
            }
        });

        Log.d(TAG, "onCreateView: mOrderType ==> " + mOrderType);

        mUserInfo = CacheManager.getUserInfoFromCache(getContext());
        if (mUserInfo != null) {
            new FetchOrdersTask().execute(mUserInfo.getUserId());
        } else {

        }
        return root;
    }

    private class FetchOrdersTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            int userId = params[0];
            Map<String, Integer> reqParams = new HashMap<>();
            reqParams.put("userId", userId);
            reqParams.put("orderType", mOrderType);
            reqParams.put("pn", mPageNum);
            reqParams.put("ps", mPageSize);
            mOrderRepository.getOrders(reqParams, new OrderDatasource.GetOrdersCallback() {
                @Override
                public void onOrdersLoaded(ArrayList<Order> orders) {
                    if (orders.size() > 0) {
                        if (mPageNum > 1) {
                            mOrders.addAll(orders);
                            mRecyclerViewAdapter.appendData(orders);
                        } else {
                            mOrders = orders;
                            mRecyclerViewAdapter.replaceData(orders);
                        }
                        mIsLoadingMore = false;

                    }
                }

                @Override
                public void onDataNotAvailable(String msg) {
                    showMessage(msg);
                    mIsLoadingMore = false;
                }
            });
            return null;
        }
    }

    private void showMessage(String msg) {
        if (!getActivity().isFinishing()) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

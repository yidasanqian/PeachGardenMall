package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.activity.GoodsDetailActivity;
import me.zoro.peachgardenmall.activity.MyOrderActivity;
import me.zoro.peachgardenmall.activity.OrderDetailActivity;
import me.zoro.peachgardenmall.datasource.GoodsDatasource;
import me.zoro.peachgardenmall.datasource.GoodsRepository;
import me.zoro.peachgardenmall.datasource.domain.Goods;
import me.zoro.peachgardenmall.datasource.domain.Order;
import me.zoro.peachgardenmall.datasource.remote.GoodsRemoteDatasource;
import me.zoro.peachgardenmall.fragment.HomeFragment;

import static me.zoro.peachgardenmall.activity.PaymentSuccessActivity.ORDER_TRACE_NO_EXTRA;

/**
 * 全部订单的adapter
 * Created by dengfengdecao on 16/12/5.
 */

public class AllOrderRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = -1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private Context mContext;
    private ArrayList<Order> mOrders;

    public AllOrderRecyclerViewAdapter(Context context, ArrayList<Order> orders) {
        mContext = context;
        mOrders = orders;

    }

    @Override
    public int getItemViewType(int position) {
        // item 第一个位置position为0，之后递增
        if (isEmpty()) {
            return TYPE_EMPTY;
        }

        return TYPE_ITEM;
    }

    private boolean isEmpty() {
        return mOrders.size() == 0;
    }

    public void replaceData(ArrayList<Order> orders) {
        mOrders = orders;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            View viewItem = LayoutInflater.from(mContext).inflate(R.layout.empty_order_item,
                    parent, false);
            return new RecyclerEmptyViewHolder(viewItem);
        }
        View viewItem = LayoutInflater.from(mContext).inflate(R.layout.order_rvi,
                parent, false);

        return RecyclerItemViewHolder.newInstance(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // item 第一个位置position为0，之后递增
        if (holder instanceof RecyclerEmptyViewHolder) {
            RecyclerEmptyViewHolder viewHolder = (RecyclerEmptyViewHolder) holder;
        } else {
            RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;

            Order order = getItem(position);
            List<Order.GoodsInfo> goodsInfoList = order.getGoodsInfo();
            int size = goodsInfoList.size();
            viewHolder.mTvGoodsCount.setText(String.valueOf(size));
            Order.GoodsInfo goodsInfo = goodsInfoList.get(0);
            Picasso.with(mContext)
                    .load(goodsInfo.getGoodsImsg())
                    .fit()
                    .into(viewHolder.mIvGoodsImg);
            viewHolder.mTvGoodsName.setText(goodsInfo.getGoodsName());
            viewHolder.mTvGoodsMoney.setText(goodsInfo.getGoodsPrice());
            viewHolder.mTvFactPay.setText(order.getTotalAmount());
            viewHolder.mGoodsInfo.setTag(order.getOutTraceNo());
            int orderType = order.getOrderType();
            if (orderType == MyOrderActivity.PENDING_PAYMENT) {
                viewHolder.mTvStatus.setText(R.string.pending_payment);
            } else if (orderType == MyOrderActivity.PENDING_DELIVERY) {
                viewHolder.mTvStatus.setText(R.string.pending_delivery);
            } else if (orderType == MyOrderActivity.PENDING_RECEIVING) {
                viewHolder.mTvStatus.setText(R.string.pending_receiving);
            } else if (orderType == MyOrderActivity.PENDING_EVALUATE) {
                viewHolder.mTvStatus.setText(R.string.pending_evaluate);
            }
        }

    }

    private Order getItem(int position) {
        return mOrders.get(position);
    }


    @Override
    public int getItemCount() {
        return mOrders.size() > 0 ? mOrders.size() : 1;
    }

    public void appendData(ArrayList<Order> orders) {
        mOrders.addAll(orders);
        notifyDataSetChanged();
    }


    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_goods_count)
        TextView mTvGoodsCount;
        @BindView(R.id.tv_status)
        TextView mTvStatus;
        @BindView(R.id.iv_goods_img)
        ImageView mIvGoodsImg;
        @BindView(R.id.tv_goods_name)
        TextView mTvGoodsName;
        @BindView(R.id.tv_goods_money)
        TextView mTvGoodsMoney;
        @BindView(R.id.goods_info)
        LinearLayout mGoodsInfo;
        @BindView(R.id.tv_fact_pay)
        TextView mTvFactPay;

        private WeakReference<Context> mTarget;


        @OnClick(R.id.goods_info)
        public void onViewClicked() {
            if (mTarget.get() != null) {
                Context target = mTarget.get();
                String outTraceNo = (String) mGoodsInfo.getTag();
                Intent intent = new Intent(target, OrderDetailActivity.class);
                intent.putExtra(ORDER_TRACE_NO_EXTRA, outTraceNo);
                target.startActivity(intent);
            }
        }

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTarget = new WeakReference<Context>(itemView.getContext());
        }

        public static RecyclerView.ViewHolder newInstance(View viewItem) {
            return new RecyclerItemViewHolder(viewItem);
        }

    }

    static class RecyclerEmptyViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
        @BindView(R.id.grid_view)
        GridView mGridView;
        private GoodsDatasource mGoodsRepository;
        private List<Goods> mGoodses;
        /**
         * 默认获取第一页
         */
        private int mPageNum = 1;
        /**
         * 默认获取10条
         */
        private int mPageSize = 10;
        /**
         * 是否正在加载更多，true，表示正在加载，false，则不是
         */
        private boolean mIsLoadingMore;
        private GoodsGridAdapter mGridAdapter;

        public RecyclerEmptyViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);

            mGoodsRepository = GoodsRepository.getInstance(GoodsRemoteDatasource.getInstance(
                    viewItem.getContext().getApplicationContext()
            ));

            mGoodses = new ArrayList<>();

            mGridAdapter = new GoodsGridAdapter(viewItem.getContext(), mGoodses);
            mGridView.setAdapter(mGridAdapter);
            mGridView.setOnItemClickListener(this);
            mGridView.setOnScrollListener(this);
            new FetchGoodsesTask().execute();


        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Goods goods = mGoodses.get(position);
            Intent intent = new Intent(view.getContext(), GoodsDetailActivity.class);
            intent.putExtra(HomeFragment.GOODS_ID_EXTRA, goods.getGoodsId());
            view.getContext().startActivity(intent);
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (SCROLL_STATE_IDLE == scrollState && view.getAdapter().getCount() <= mPageSize) {
                mIsLoadingMore = false;
                mPageNum = 1;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // 上拉加载
            if (view.getLastVisiblePosition() == totalItemCount - 1 && visibleItemCount > 0 && !mIsLoadingMore) {
                mIsLoadingMore = true;
                mPageNum++;
                new FetchGoodsesTask().execute();
            }
        }

        private class FetchGoodsesTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... params) {
                Map<String, Object> map = new HashMap<>();
                map.put("pn", mPageNum);
                map.put("ps", mPageSize);
                mGoodsRepository.getGoodses(map, new GoodsDatasource.GetGoodsesCallback() {
                    @Override
                    public void onGoodsesLoaded(ArrayList<Goods> goodses) {
                        if (goodses.size() > 0) {
                            if (mPageNum > 1) {
                                mGoodses.addAll(goodses);
                                mGridAdapter.appendData(goodses);
                            } else {
                                mGoodses = goodses;
                                mGridAdapter.replaceData(goodses);
                            }
                            mIsLoadingMore = false;
                        }
                    }

                    @Override
                    public void onDataNotAvailable(String errorMsg) {
                        mIsLoadingMore = false;
                    }
                });
                return null;
            }
        }
    }
}

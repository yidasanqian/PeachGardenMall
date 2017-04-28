package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.datasource.domain.Order;

/**
 * Created by dengfengdecao on 16/12/5.
 */

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = -1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private Context mContext;
    private List<Order> mOrders;

    public OrderRecyclerViewAdapter(Context context, List<Order> orders) {
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

    public void replaceData(List<Order> orders) {
        mOrders = orders;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            View viewItem = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,
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
            viewHolder.mTvEmptyHint.setText(R.string.empty_data_hint);
        } else {
            RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;

            Order order = getItem(position);

        }

    }

    private Order getItem(int position) {
        return mOrders.get(position);
    }


    @Override
    public int getItemCount() {
        return mOrders.size() > 0 ? mOrders.size() : 1;
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

        @OnClick(R.id.goods_info)
        public void onViewClicked() {
        }

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public static RecyclerView.ViewHolder newInstance(View viewItem) {
            return new RecyclerItemViewHolder(viewItem);
        }

    }

    private class RecyclerEmptyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvEmptyHint;

        public RecyclerEmptyViewHolder(View viewItem) {
            super(viewItem);
            mTvEmptyHint = (TextView) viewItem.findViewById(android.R.id.text1);
        }
    }
}

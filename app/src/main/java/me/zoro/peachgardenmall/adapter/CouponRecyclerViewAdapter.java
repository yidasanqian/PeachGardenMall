package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.datasource.domain.Coupon;

/**
 * Created by dengfengdecao on 17/5/2.
 */

public class CouponRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY = -1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;


    private List<Coupon> mCoupons;

    public CouponRecyclerViewAdapter(List<Coupon> coupons) {
        mCoupons = coupons;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == TYPE_EMPTY) {
            View viewItem = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,
                    parent, false);
            return new RecyclerEmptyViewHolder(viewItem);
        }
        View viewItem = LayoutInflater.from(context).inflate(R.layout.coupon_rvi,
                parent, false);

        return RecyclerItemViewHolder.newInstance(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerEmptyViewHolder) {
            RecyclerEmptyViewHolder viewHolder = (RecyclerEmptyViewHolder) holder;
            viewHolder.mTvEmptyHint.setText(R.string.empty_data_hint);
        } else {
            RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return mCoupons.size() > 0 ? mCoupons.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty()) {
            return TYPE_EMPTY;
        }

        return TYPE_ITEM;
    }

    private boolean isEmpty() {
        return mCoupons.size() == 0;
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_money)
        TextView mTvMoney;
        @BindView(R.id.tv_full_cut)
        TextView mTvFullCut;
        @BindView(R.id.tv_suit)
        TextView mTvSuit;

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public static RecyclerView.ViewHolder newInstance(View viewItem) {
            return new RecyclerItemViewHolder(viewItem);
        }
    }

    private static class RecyclerEmptyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvEmptyHint;

        public RecyclerEmptyViewHolder(View viewItem) {
            super(viewItem);
            mTvEmptyHint = (TextView) viewItem.findViewById(android.R.id.text1);
        }
    }
}

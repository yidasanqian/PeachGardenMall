package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.datasource.domain.Cart;
import me.zoro.peachgardenmall.datasource.domain.Goods;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class CreateOrderGoodsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int TYPE_EMPTY = -1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private Context mContext;
    private List<Cart> mCarts;

    /**
     * 商品合计金额
     */
    private double mTotalPrice;

    public OnItemClickListener mListener;

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, (Goods) v.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Goods cart);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CreateOrderGoodsRecyclerViewAdapter(Context context, List<Cart> carts) {
        mContext = context;
        mCarts = carts;
    }

    public void replaceData(List<Cart> carts) {
        mCarts = carts;
        notifyDataSetChanged();
    }

    public void appendData(List<Cart> carts) {
        mCarts.addAll(carts);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            View viewItem = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,
                    parent, false);
            return new RecyclerEmptyViewHolder(viewItem);
        }

        View viewItem = LayoutInflater.from(mContext).inflate(R.layout.create_order_goods_item,
                parent, false);
        viewItem.setOnClickListener(this);
        return RecyclerItemViewHolder.newInstance(viewItem);
    }

    public double getTotalPrice() {
        return mTotalPrice;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerEmptyViewHolder) {
            RecyclerEmptyViewHolder viewHolder = (RecyclerEmptyViewHolder) holder;
            viewHolder.mTvEmptyHint.setText(R.string.empty_data_hint);
        } else {
            RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
            Cart cart = getItem(position);
            viewHolder.itemView.setTag(cart);
            viewHolder.mGoodsNameTv.setText(cart.getGoodsName());
            int goodsCount = cart.getGoodsNum();
            viewHolder.mGoodsCountTv.setText(String.valueOf(goodsCount));
            Picasso.with(mContext)
                    .load(cart.getImageUrl())
                    .into(viewHolder.mGoodsImgIv);

            // 商品单价
            double money = Double.parseDouble(cart.getGoodsPrice());
            // 商品合计
            mTotalPrice += money * goodsCount;
            viewHolder.mGoodsMoneyTv.setText(String.valueOf(money));
            viewHolder.mGoodsStrikeMoneyTv.setText(cart.getMarketPrice());
            viewHolder.mGoodsExtraInfoTv.setText(cart.getSpecKeyName());

        }
    }

    private Cart getItem(int position) {
        return mCarts.get(position);
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
        return mCarts.size() == 0;
    }

    @Override
    public int getItemCount() {
        return mCarts.size() > 0 ? mCarts.size() : 1;
    }


    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.goods_img_iv)
        ImageView mGoodsImgIv;
        @BindView(R.id.goods_name_tv)
        TextView mGoodsNameTv;
        @BindView(R.id.goods_count_tv)
        TextView mGoodsCountTv;
        @BindView(R.id.goods_extra_info_tv)
        TextView mGoodsExtraInfoTv;
        @BindView(R.id.goods_money_tv)
        TextView mGoodsMoneyTv;
        @BindView(R.id.goods_strike_money_tv)
        TextView mGoodsStrikeMoneyTv;

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

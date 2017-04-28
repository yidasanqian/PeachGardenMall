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
import me.zoro.peachgardenmall.datasource.domain.Goods;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class MyCollectionGoodsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int TYPE_EMPTY = -1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private Context mContext;
    private List<Goods> mGoodses;

    public OnItemClickListener mListener;

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, (Goods) v.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Goods goods);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyCollectionGoodsRecyclerViewAdapter(Context context, List<Goods> goodses) {
        mContext = context;
        mGoodses = goodses;
    }

    public void replaceData(List<Goods> goodses) {
        mGoodses = goodses;
        notifyDataSetChanged();
    }

    public void appendData(List<Goods> goodses) {
        mGoodses.addAll(goodses);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            View viewItem = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,
                    parent, false);
            return new RecyclerEmptyViewHolder(viewItem);
        }

        View viewItem = LayoutInflater.from(mContext).inflate(R.layout.my_collection_rvi,
                parent, false);
        viewItem.setOnClickListener(this);
        return RecyclerItemViewHolder.newInstance(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerEmptyViewHolder) {
            RecyclerEmptyViewHolder viewHolder = (RecyclerEmptyViewHolder) holder;
            viewHolder.mTvEmptyHint.setText(R.string.empty_data_hint);
        } else {
            RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
            Goods goods = getItem(position);
            viewHolder.itemView.setTag(goods);
            Picasso.with(mContext)
                    .load(goods.getOriginalImg())
                    .centerCrop()
                    .into(viewHolder.mIvGoodsImg);
            viewHolder.mTvGoodsName.setText(goods.getGoodsName());
            viewHolder.mTvGoodsMoney.setText(goods.getPrice());
        }
    }

    private Goods getItem(int position) {
        return mGoodses.get(position);
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
        return mGoodses.size() == 0;
    }

    @Override
    public int getItemCount() {
        return mGoodses.size() > 0 ? mGoodses.size() : 1;
    }


    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_goods_img)
        ImageView mIvGoodsImg;
        @BindView(R.id.tv_goods_name)
        TextView mTvGoodsName;
        @BindView(R.id.tv_goods_money)
        TextView mTvGoodsMoney;

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

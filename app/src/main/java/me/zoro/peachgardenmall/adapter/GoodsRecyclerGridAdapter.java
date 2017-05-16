package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.activity.GoodsListActivity;
import me.zoro.peachgardenmall.datasource.domain.Goods;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class GoodsRecyclerGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int TYPE_EMPTY = -1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private Context mContext;
    private View mHeaderView;
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


    public GoodsRecyclerGridAdapter(Context context, View headerView, List<Goods> goodses) {
        mContext = context;
        mHeaderView = headerView;
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
        if (viewType == TYPE_HEADER) {
            return new RecyclerHeaderViewHolder(mHeaderView);
        }
        View viewItem = LayoutInflater.from(mContext).inflate(R.layout.goods_gv_item,
                parent, false);
        viewItem.setOnClickListener(this);
        return RecyclerItemViewHolder.newInstance(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerHeaderViewHolder) {
            RecyclerHeaderViewHolder viewHolder = (RecyclerHeaderViewHolder) holder;

        } else {
            RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
            // 绑定数据时要注意，因为多出Header在前，所以需要－1
            Goods goods = getItem(position - 1);
            viewHolder.mGoodsNameTv.setText(goods.getGoodsName());
            Picasso.with(mContext)
                    .load(goods.getImageUrl())
                    .into(viewHolder.mGoodsImgIv);

            if (mContext instanceof GoodsListActivity) {
                viewHolder.mTvGoodsMoney.setText(goods.getPrice());

                viewHolder.mLlGoodsPrice.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mLlGoodsPrice.setVisibility(View.GONE);
            }

            viewHolder.itemView.setTag(goods);
        }
    }

    public Goods getItem(int i) {
        return mGoodses.get(i);
    }

    // 获取Item的数量，因为添加了Header和Footer，View的数量应该加2
    @Override
    public int getItemCount() {
        return mGoodses.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_img_iv)
        ImageView mGoodsImgIv;
        @BindView(R.id.goods_name_tv)
        TextView mGoodsNameTv;
        @BindView(R.id.tv_goods_money)
        TextView mTvGoodsMoney;
        @BindView(R.id.ll_goods_price)
        LinearLayout mLlGoodsPrice;

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public static RecyclerView.ViewHolder newInstance(View viewItem) {
            return new RecyclerItemViewHolder(viewItem);
        }
    }

    private static class RecyclerHeaderViewHolder extends RecyclerView.ViewHolder {
        public RecyclerHeaderViewHolder(View viewItem) {
            super(viewItem);
        }
    }
}

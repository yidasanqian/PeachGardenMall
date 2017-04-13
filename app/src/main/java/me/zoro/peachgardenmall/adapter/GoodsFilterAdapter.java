package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.zoro.peachgardenmall.R;


/**
 * Created by dengfengdecao on 17/4/13.
 */

public class GoodsFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private String[] mCategory;

    private OnItemClickListener mOnItemClickListener;


    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public GoodsFilterAdapter(Context context, String[] category) {
        mContext = context;
        mCategory = category;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(mContext).inflate(R.layout.simple_list_item, parent, false);
        viewItem.setOnClickListener(this);
        return RecyclerItemViewHolder.newInstance(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        String category = getItem(position);
        viewHolder.mTvEmptyHint.setText(category);
        viewHolder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }


    private String getItem(int position) {
        return mCategory[position];
    }

    @Override
    public int getItemCount() {
        return mCategory.length;
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTvEmptyHint;

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            mTvEmptyHint = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public static RecyclerView.ViewHolder newInstance(View viewItem) {
            return new RecyclerItemViewHolder(viewItem);
        }

    }
}

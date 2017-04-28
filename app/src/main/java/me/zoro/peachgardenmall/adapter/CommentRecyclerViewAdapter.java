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
import de.hdodenhof.circleimageview.CircleImageView;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.datasource.domain.Comment;

/**
 * Created by dengfengdecao on 16/12/5.
 */

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = -1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;


    private Context mContext;
    private List<Comment> mComments;

    public CommentRecyclerViewAdapter(Context context, List<Comment> comments) {
        mContext = context;
        mComments = comments;
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
        return mComments.size() == 0;
    }

    public void replaceData(List<Comment> comments) {
        mComments = comments;
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
        View viewItem = LayoutInflater.from(mContext).inflate(R.layout.comment_rvi,
                parent, false);

        return RecyclerItemViewHolder.newInstance(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // item 第一个位置position为0，之后递增
        if (holder instanceof RecyclerEmptyViewHolder) {
            RecyclerEmptyViewHolder viewHolder = (RecyclerEmptyViewHolder) holder;
            viewHolder.mTvEmptyHint.setText(R.string.empty_data_hint);
        } else {
            RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
            Comment comment = getItem(position);

        }
    }

    private Comment getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public int getItemCount() {
        return mComments.size() > 0 ? mComments.size() : 1;
    }


    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_avatar)
        CircleImageView mIvAvatar;
        @BindView(R.id.tv_nickname)
        TextView mTvNickname;
        @BindView(R.id.tv_comment_content)
        TextView mTvCommentContent;
        @BindView(R.id.tv_goods_spec)
        TextView mTvGoodsSpec;

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

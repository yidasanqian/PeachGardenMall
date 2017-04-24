package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.activity.CreateAddressActivity;
import me.zoro.peachgardenmall.datasource.domain.Address;

/**
 * Created by dengfengdecao on 17/4/24.
 */

public class AddressRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String ADDRESS_EXTRA = "address";

    private static final int TYPE_EMPTY = -1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private List<Address> mAddresses;

    public AddressRecyclerViewAdapter(List<Address> addresses) {
        mAddresses = addresses;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == TYPE_EMPTY) {
            View viewItem = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,
                    parent, false);
            return new RecyclerEmptyViewHolder(viewItem);
        }
        View viewItem = LayoutInflater.from(context).inflate(R.layout.delivery_address_rvi,
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
            Address address = mAddresses.get(position);
            if (address != null) {
                if (address.isIsDefault()) {
                    viewHolder.mLlAddrItem.setBackgroundResource(R.drawable.delivery_addr_item_background);

                } else {
                    viewHolder.mLlAddrItem.setBackgroundResource(R.drawable.delivery_addr_item_normal_background);
                }
                viewHolder.mTvNickname.setText(address.getConsignee());
                viewHolder.mTvContactPhone.setText(address.getMobile());
                viewHolder.mTvContactAddress.setText(address.getAddress());
                viewHolder.mIbEditAddress.setTag(address);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mAddresses.size() > 0 ? mAddresses.size() : 1;
    }

    public int getItemViewType(int position) {
        // item 第一个位置position为0，之后递增
        if (isEmpty()) {
            return TYPE_EMPTY;
        }

        return TYPE_ITEM;
    }

    private boolean isEmpty() {
        return mAddresses.size() == 0;
    }

    public void replaceData(List<Address> addresses) {
        mAddresses = addresses;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }


    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_addr_item)
        LinearLayout mLlAddrItem;
        @BindView(R.id.tv_nickname)
        TextView mTvNickname;
        @BindView(R.id.tv_contact_phone)
        TextView mTvContactPhone;
        @BindView(R.id.tv_contact_address)
        TextView mTvContactAddress;
        @BindView(R.id.ib_edit_address)
        ImageButton mIbEditAddress;

        private Context mContext;

        @OnClick(R.id.ib_edit_address)
        public void onViewClicked() {
            Address address = (Address) mIbEditAddress.getTag();
            Intent intent = new Intent(mContext, CreateAddressActivity.class);
            intent.putExtra(ADDRESS_EXTRA, address);
            mContext.startActivity(intent);
        }


        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
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

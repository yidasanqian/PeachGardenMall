package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.activity.GoodsListActivity;
import me.zoro.peachgardenmall.datasource.domain.Goods;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class GoodsGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<Goods> mGoodses;

    public GoodsGridAdapter(Context context, List<Goods> goodses) {
        mContext = context;
        mGoodses = goodses;
    }

    @Override
    public int getCount() {
        return mGoodses.size();
    }

    @Override
    public Goods getItem(int i) {
        return mGoodses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.goods_gv_item, viewGroup, false);
            if (mContext instanceof GoodsListActivity) {
                viewHolder = new ViewHolder((ImageView) view.findViewById(R.id.goods_img_iv),
                        (TextView) view.findViewById(R.id.goods_name_tv),
                        (TextView) view.findViewById(R.id.tv_goods_money),
                        (LinearLayout) view.findViewById(R.id.ll_goods_price));
                viewHolder.mLinearLayout.setVisibility(View.VISIBLE);
            } else {
                viewHolder = new ViewHolder((ImageView) view.findViewById(R.id.goods_img_iv),
                        (TextView) view.findViewById(R.id.goods_name_tv));
            }

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Goods goods = getItem(i);
        viewHolder.mName.setText(goods.getGoodsName());
        Picasso.with(mContext)
                .load(goods.getImageUrl())
                .into(viewHolder.mIcon);

        if (mContext instanceof GoodsListActivity) {
            viewHolder.mPrice.setText(goods.getPrice());
        }
        return view;
    }

    public void replaceData(List<Goods> goodses) {
        mGoodses = goodses;
        notifyDataSetChanged();
    }

    public void appendData(List<Goods> goodses) {
        mGoodses.addAll(goodses);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private ImageView mIcon;
        private TextView mName;
        private TextView mPrice;
        private LinearLayout mLinearLayout;

        public ViewHolder(ImageView icon, TextView name) {
            this.mName = name;
            this.mIcon = icon;
        }

        public ViewHolder(ImageView icon, TextView name, TextView price, LinearLayout linearLayout) {
            this.mName = name;
            this.mIcon = icon;
            this.mPrice = price;
            this.mLinearLayout = linearLayout;
        }
    }
}

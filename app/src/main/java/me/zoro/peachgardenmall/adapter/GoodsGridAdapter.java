package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.datasource.domain.Goods;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class GoodsGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<Goods> mGoodses;

    public GoodsGridAdapter(Context context, List<Goods> images) {
        mContext = context;
        mGoodses = images;
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
            viewHolder = new ViewHolder((ImageView) view.findViewById(R.id.goods_img_iv),
                    (TextView) view.findViewById(R.id.goods_name_tv));
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Goods goods = getItem(i);
        viewHolder.mName.setText(goods.getGoodsName());
        Picasso.with(mContext)
                .load(goods.getImageUrl())
                .fit()
                .into(viewHolder.mIcon);

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
        protected ImageView mIcon;
        protected TextView mName;

        /**
         * The constructor to construct a navigation view tag
         *
         * @param name the name view of the item
         * @param icon the icon view of the item
         */
        public ViewHolder(ImageView icon, TextView name) {
            this.mName = name;
            this.mIcon = icon;
        }
    }
}

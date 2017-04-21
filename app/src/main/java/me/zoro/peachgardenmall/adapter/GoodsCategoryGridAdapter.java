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
import me.zoro.peachgardenmall.datasource.domain.GoodsCategory;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class GoodsCategoryGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<GoodsCategory> mGoodsCategories;

    public GoodsCategoryGridAdapter(Context context, List<GoodsCategory> goodsCategories) {
        mContext = context;
        mGoodsCategories = goodsCategories;
    }

    @Override
    public int getCount() {
        return mGoodsCategories.size();
    }

    @Override
    public GoodsCategory getItem(int i) {
        return mGoodsCategories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.goods_category_gv_item, viewGroup, false);
            viewHolder = new ViewHolder((ImageView) view.findViewById(R.id.goods_img_iv),
                    (TextView) view.findViewById(R.id.goods_name_tv));
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        GoodsCategory goodsCategory = getItem(i);
        Picasso.with(mContext)
                .load(goodsCategory.getImageUrl())
                .fit()
                .into(viewHolder.mIcon);
        return view;
    }

    public void replaceData(List<GoodsCategory> goodsCategories) {
        mGoodsCategories = goodsCategories;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        protected ImageView mIcon;
        protected TextView mName;

        public ViewHolder(ImageView icon, TextView name) {
            this.mName = name;
            this.mIcon = icon;
        }
    }
}

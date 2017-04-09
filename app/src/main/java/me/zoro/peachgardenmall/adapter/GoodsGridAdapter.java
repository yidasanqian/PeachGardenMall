package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.zoro.peachgardenmall.R;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class GoodsGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<Integer> mImages;

    public GoodsGridAdapter(Context context, List<Integer> images) {
        mContext = context;
        mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object getItem(int i) {
        return mImages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemViewTag viewTag;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.goods_gv_item, viewGroup, false);
            viewTag = new ItemViewTag((ImageView) view.findViewById(R.id.goods_img_iv),
                    (TextView) view.findViewById(R.id.goods_name_tv));
            view.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) view.getTag();
        }

        viewTag.mName.setText("高圆圆");
        viewTag.mIcon.setImageResource(R.drawable.ic_gaoyuanyuan);
        return view;
    }

    static class ItemViewTag {
        protected ImageView mIcon;
        protected TextView mName;

        /**
         * The constructor to construct a navigation view tag
         *
         * @param name the name view of the item
         * @param icon the icon view of the item
         */
        public ItemViewTag(ImageView icon, TextView name) {
            this.mName = name;
            this.mIcon = icon;
        }
    }
}

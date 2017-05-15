package me.zoro.peachgardenmall.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

/**
 * http://stackoverflow.com/questions/28531996/android-recyclerview-gridlayoutmanager-column-spacing
 * Created by dengfengdecao on 17/5/15.
 */

public class SpacesItemDecoration extends ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {


        // header view 不分割
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left = 0;
            outRect.top = 0;
            outRect.left = 0;
            outRect.right = 0;
        } else {
            outRect.left = space;
            outRect.top = space;
            outRect.right = space;
            outRect.bottom = space;
        }
    }
}

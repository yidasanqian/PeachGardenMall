package me.zoro.peachgardenmall.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import me.zoro.peachgardenmall.R;

public class RichText extends AppCompatTextView {

    public RichText(Context context) {
        this(context, null);
    }

    public RichText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        /**
         * 取得自定义属性值
         */
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RichText);
        int drawableWidth = ta.getDimensionPixelSize(R.styleable.RichText_drawableWidth, -1);
        int drawableHeight = ta.getDimensionPixelSize(R.styleable.RichText_drawableHeight, -1);
        /**
         * 取得TextView的Drawable(左上右下四个组成的数组值)
         */
        Drawable[] drawables = getCompoundDrawables();
        Drawable textDrawable = null;
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                textDrawable = drawable;
            }
        }
        /**
         * 设置宽高
         */
        if (textDrawable != null && drawableWidth != -1 && drawableHeight != -1) {
            textDrawable.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        /**
         * 设置给TextView
         */
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        /**
         * 回收ta
         */
        ta.recycle();
    }
}

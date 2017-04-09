package me.zoro.peachgardenmall.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by dengfengdecao on 16/11/2.
 */

public class TintDrawableUtil {


    public static Drawable tintDrawable(Drawable src, @ColorInt int tintColor) {
        final Drawable wrapDrawable = DrawableCompat.wrap(src);
        DrawableCompat.setTintList(wrapDrawable, ColorStateList.valueOf(tintColor));
        return wrapDrawable;
    }
}

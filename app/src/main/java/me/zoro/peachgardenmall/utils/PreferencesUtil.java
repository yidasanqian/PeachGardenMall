package me.zoro.peachgardenmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dengfengdecao on 16/11/14.
 */

public class PreferencesUtil {

    public static SharedPreferences getDefaultPreferences(Context context, String prefName) {
        SharedPreferences sp = null;
        if (context != null) {
            sp = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        }
        return sp;
    }
}

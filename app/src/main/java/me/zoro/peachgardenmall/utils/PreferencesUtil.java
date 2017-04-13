package me.zoro.peachgardenmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;

import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;

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

    public static void persistentToken(Context context, String token) {
        // 持久化token
        PreferencesUtil.getDefaultPreferences(context, Const.PREF_TOKEN)
                .edit()
                .putString(Const.TOKEN_KEY, token)
                .apply();
    }

    public static void persistentUserInfo(Context context, UserInfo userInfo) {
        String userinfo = new GsonBuilder().create().toJson(userInfo, UserInfo.class);
        PreferencesUtil.getDefaultPreferences(context, Const.PREF_USER_INFO)
                .edit()
                .putString(Const.USERINFO_KEY, userinfo)
                .apply();
    }

    public static UserInfo getUserInfoFromPref(Context context) {
        String userStr = PreferencesUtil.getDefaultPreferences(context, Const.PREF_USER_INFO)
                .getString(Const.USERINFO_KEY, null);

        return new GsonBuilder().setLenient().create().fromJson(userStr, UserInfo.class);
    }

    public static String getTokenFromPref(Context context) {
        String token = PreferencesUtil.getDefaultPreferences(context, Const.PREF_TOKEN)
                .getString(Const.TOKEN_KEY, null);

        return token;
    }
}

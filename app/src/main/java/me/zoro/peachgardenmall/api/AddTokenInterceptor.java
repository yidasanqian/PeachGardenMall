package me.zoro.peachgardenmall.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.utils.PreferencesUtil;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dengfengdecao on 16/11/14.
 */

public class AddTokenInterceptor implements Interceptor {

    private static final String TAG = "AddTokenInterceptor";
    private Context mContext;

    public AddTokenInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        SharedPreferences sp = PreferencesUtil
                .getDefaultPreferences(mContext, Const.PREF_TOKEN);
        if (sp == null) {
            return null;
        }

        String token = sp.getString(Const.TOKEN_KEY, null);

        if (!TextUtils.isEmpty(token)) {
            // 添加header
           /* Request newRequest = original.newBuilder()
                    .addHeader("token", token)
                    .build();*/

            // 添加请求参数
            HttpUrl url = original.url().newBuilder()
                    .addQueryParameter("token", token)
                    .build();

            Request newRequest = original.newBuilder()
                    .method(original.method(), original.body())
                    .url(url)
                    .build();
            Log.d(TAG, "intercept: TOKEN <== " + token);
            return chain.proceed(newRequest);
        } else {
            // 未登录
            return chain.proceed(original);
        }

    }
}

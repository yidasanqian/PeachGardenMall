package me.zoro.peachgardenmall.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static me.zoro.peachgardenmall.common.AppConfig.SERVER_HOST;

/**
 * API/HTTP client heart
 * Created by dengfengdecao on 16-7-7.
 */
public final class ServiceGenerator {
    private static final String TAG = "ServiceGenerator";
    private static final String API_BASE_URL = SERVER_HOST + "/Home/";

    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
            .setLevel(HttpLoggingInterceptor.Level.BASIC);

    private static final Gson sGson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd hh:mm:ss")
            .setLenient()
            .create();

    public static <S> S createService(Context context, Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)    // 添加日志拦截器
                //.addInterceptor(new ReceivedCookiesInterceptor(sContext))
                .addInterceptor(new AddTokenInterceptor(context))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Accept-Charset", "utf-8")
                                .build();

                        return chain.proceed(request);
                    }
                });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(sGson))
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        Log.d(TAG, "createService: context <== " + context + "\t url=" + retrofit.baseUrl().url() + "\tbaseurl="
                + retrofit.baseUrl());

        return retrofit.create(serviceClass);
    }
}

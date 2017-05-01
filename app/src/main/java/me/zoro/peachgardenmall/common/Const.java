package me.zoro.peachgardenmall.common;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public final class Const {


    /**
     * 服务器返回的数据结构
     * code：0,
     * message: "success",
     * result: { key1: value1, key2: value2, ... },
     * count:120
     */
    public static final String CODE = "code";
    public static final String MESSAGE = "message";
    public static final String RESULT = "result";
    public static final String COUNT = "count";

    /**
     * Preferences
     */
    public static final String PREF_TOKEN = "pref_token";
    public static final String PREF_USER_INFO = "pref_user_info";
    public static final String TOKEN_KEY = "token";
    public static final String USERINFO_KEY = "userinfo";


    /**
     * Cache
     */
    public static final String USER_INFO_CACHE_KEY = "user";


    public static final String IMAGE_URL = "image_url";

    public static final String SERVER_AVALIABLE = "服务器异常，请稍后重试";

    public static final String CREATE_ORDER_TAG = "create_order";
}

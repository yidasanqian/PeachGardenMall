package me.zoro.peachgardenmall.utils;

import android.support.v4.util.LruCache;

/**
 * Created by dengfengdecao on 17/4/18.
 */

public class CacheManager {

    private static CacheManager sCacheManager;

    private static LruCache<String, Object> sCache;

    private CacheManager() {
        // 获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int maxSize = maxMemory / 8;
        sCache = new LruCache<>(maxSize);
    }

    public static CacheManager getInstance() {
        if (sCacheManager == null) {
            sCacheManager = new CacheManager();
        }
        return sCacheManager;
    }

    public void put(String key, Object value) {
        sCache.put(key, value);
    }

    public Object get(String key) {
        return sCache.get(key);
    }
}

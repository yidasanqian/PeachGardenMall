package me.zoro.peachgardenmall.utils;

import android.content.Context;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;


/**
 * Created by dengfengdecao on 17/4/18.
 */

public class CacheManager {

    private static final String TAG = "CacheManager";
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

    public String getTotalCacheSize(Context context) {
        long cacheSize = 0;
        try {
            cacheSize = getFolderSize(context.getCacheDir());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cacheSize += getFolderSize(context.getExternalCacheDir());
            }
        } catch (Exception e) {
            Log.e(TAG, "getTotalCacheSize: 获取缓存目录文件大小", e);
        }

        return Formatter.formatFileSize(context, cacheSize);
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    private long getFolderSize(File file) throws Exception {
        long size = 0;
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            // 如果下面还有文件
            if (fileList[i].isDirectory()) {
                size += getFolderSize(fileList[i]);
            } else {
                size += fileList[i].length();
            }
        }
        return size;
    }

    public void clearCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private void deleteDir(File cacheDir) {
        if (cacheDir != null && cacheDir.exists() && cacheDir.isDirectory()) {
            for (File file :
                    cacheDir.listFiles()) {
                // 如果是文件则直接删除，否则递归file
                if (file.isFile()) {
                    boolean isDelete = file.delete();
                    Log.d(TAG, "deleteDir: isDelete <==" + isDelete);
                } else {
                    deleteDir(file);
                }

            }
        }
    }
}

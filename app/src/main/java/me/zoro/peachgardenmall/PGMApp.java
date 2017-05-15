package me.zoro.peachgardenmall;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import java.util.List;

import me.zoro.peachgardenmall.activity.MainActivity;

/**
 * Created by dengfengdecao on 17/4/7.
 */

public class PGMApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        if (shouldInit()) {
            Beta.initDelay = 10 * 1000;  // 延迟初始化
            Beta.canShowUpgradeActs.add(MainActivity.class);// 指定只在哪个Activity弹窗升级
            // 注册bugly
            Bugly.init(getApplicationContext(), "13464d7501", false);
        }
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}

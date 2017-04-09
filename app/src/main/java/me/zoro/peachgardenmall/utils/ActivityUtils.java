package me.zoro.peachgardenmall.utils;

/**
 * This provides methods to help Activities load their UI.
 * Created by dengfengdecao on 16/10/21.
 */

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, tag);
        transaction.commit();
    }

    public static void switchFragmentOnActivity(@NonNull FragmentManager fragmentManager,
                                                @NonNull Fragment from,
                                                @NonNull Fragment to,
                                                int frameId, String tag) {
        if (from != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // 要切换的fragment已经在activity上,则隐藏,否则添加到activity上
            if (to.isAdded()) {
                transaction.hide(from).show(to);
            } else {
                transaction.hide(from).add(frameId, to, tag);
            }
            if (Build.VERSION.SDK_INT >= 24) {
                transaction.commitNow();
            } else {
                // fix : java.lang.IllegalStateException: FragmentManager is already executing transactions
                fragmentManager.executePendingTransactions();
                transaction.commit();
            }
        }
    }

    public static void replaceFragmentOnActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment from,
                                                 @NonNull Fragment to,
                                                 int frameId, String tag) {
        if (from != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // 要切换的fragment已经在activity上,则隐藏,否则添加到activity上
            if (to.isAdded()) {
                transaction.hide(from).show(to);
            } else {
                transaction.hide(from).add(frameId, to, tag);
            }
            if (Build.VERSION.SDK_INT >= 24) {
                transaction.commitNow();
            } else {
                // fix : java.lang.IllegalStateException: FragmentManager is already executing transactions
                fragmentManager.executePendingTransactions();
                transaction.commit();
            }
        }
    }

}

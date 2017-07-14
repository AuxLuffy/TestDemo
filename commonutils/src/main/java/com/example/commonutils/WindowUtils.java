package com.example.commonutils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by xuxiaowei on 2016/9/19.
 */
public class WindowUtils {

    private static DisplayMetrics metrics = null;
    private static String LOCK = "Synchronous lock";


    /**
     * 获得屏幕的宽
     * @param context
     * @return
     */
    public static int widthPixels(Context context) {
        synchronized (LOCK){
            if (metrics == null) {
                metrics = new DisplayMetrics();
            }
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        //((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获得屏幕的高
     * @param context
     * @return
     */
    public static int heightPixels(Context context) {
        synchronized (LOCK){
            if (metrics == null) {
                metrics = new DisplayMetrics();
            }
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        //((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return displayMetrics.heightPixels;
    }


    /**
     * 计算状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


}
package com.example.commonutils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by 彤 on 2016/5/4.
 * 实现Activity管理类工具
 */
public class ActivityManager {
    private Stack<Activity> activities;
    private static ActivityManager manager;

    private ActivityManager() {
        activities = new Stack<>();
    }

    public static ActivityManager getInstance() {
        if (manager == null) {
            manager = new ActivityManager();
        }
        return manager;
    }

    /**
     * @param activity 添加activity到栈
     */
    public void addToStack(Activity activity) {
        if (activities == null) {
            activities = new Stack<>();
        }
        activities.add(activity);
    }

    /**
     * 获取最顶层的activity
     */
    public Activity getLastActivity() {
        if (!activities.isEmpty()) {
            return activities.lastElement();
        } else {
            return null;
        }
    }

    /**
     * 获取最底层的activity
     */
    public Activity getFirstActivity() {
        if (!activities.isEmpty()) {
            return activities.firstElement();
        } else {
            return null;
        }
    }

    /**
     * 从栈中移除activity
     */
    public void removeActivity(Activity activity) {
        if (activities != null && activities.size() > 0) {
            if (activity != null)
                activity.finish();
            activities.remove(activity);
            activity = null;
        }
    }

    /**
     * 移除所有的activity。当结束程序是调用
     */
    public void clearAllActivity() {
        if (activities != null) {
            while (activities.size() > 0) {
                Activity activity = getLastActivity();
                if (activity == null)
                    break;
                removeActivity(activity);
            }
        }
    }
}

package com.example.commonutils;

import android.content.Context;
import android.view.WindowManager;

/**
 * @description: 屏幕工具类
 * @author: 袁东华
 * created at 2016/11/12 17:44
 */
public class ScreenUtil {
    private int width;
    private int height;
    private Context context;

    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        if (width == 0) {
            if (context != null) {
                WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
                 width = wm.getDefaultDisplay().getWidth();
            }
        }
        return width;
    }
    public int getHeight() {
        if (height == 0) {
            if (context != null) {
                WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
                height = wm.getDefaultDisplay().getHeight();
            }
        }
        return height;
    }

    private static ScreenUtil instance;

    private ScreenUtil() {

    }

    public static ScreenUtil getInstance() {
        if (instance == null) {
            synchronized (ScreenUtil.class) {
                if (instance == null) {
                    instance = new ScreenUtil();
                }
            }
        }
        return instance;
    }
}

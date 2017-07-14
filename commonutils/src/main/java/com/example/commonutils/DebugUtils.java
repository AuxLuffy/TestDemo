package com.example.commonutils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 彤 on 2016/4/28.
 */
public class DebugUtils {

    private Toast mToast;
    private static DebugUtils mDebugUtils;
    /**
     * toast提示
     */

    public static DebugUtils getInstance(){
        if (mDebugUtils == null) {
            synchronized (DebugUtils.class) {
                if (mDebugUtils == null) {
                    mDebugUtils = new DebugUtils();
                }
            }
        }
        return mDebugUtils;
    }
    public void dToast(Context context, String str) {

        if (mToast != null) {
            mToast.cancel();
        }
        if(TextUtils.isEmpty(str)){
            str = "";
        }
        Context xtx = context.getApplicationContext();
        mToast = Toast.makeText(xtx, str, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void dToast(Context context, int strRes) {
        if (mToast != null) {
            mToast.cancel();
        }
        if (context != null) {
            Context xtx = context.getApplicationContext();
            mToast = Toast.makeText(xtx, strRes, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    /**
     * Log输出工具
     */
    public static void DLog(String key, String msg) {
        Log.d(key, msg);
    }
}

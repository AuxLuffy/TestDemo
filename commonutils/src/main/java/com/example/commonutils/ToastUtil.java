package com.example.commonutils;

import android.content.Context;
import android.widget.Toast;

/**
 * @description: Toast工具类
 * @author: 袁东华
 * created at 2016/11/13 15:41
 */
public class ToastUtil {
    private static ToastUtil instance;
    private Toast toast;
    private static Context context;

    private ToastUtil() {
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }


    /**
     * @description: 设置显示的内容
     * @author: 袁东华
     * created at 2016/11/13 16:06
     */
    public ToastUtil setText(String content) {
        toast.setText(content);
        this.show();
        return this;
    }

    public void show() {
        toast.show();
    }

    ;

    /**
     * @description:
     * @author: 袁东华
     * created at 2016/11/13 15:58
     */
    public static void initContext(Context c) {
        context = c;
    }

    public static ToastUtil getInstance() {
        if (instance == null) {
            synchronized (ToastUtil.class) {
                if (instance == null) {
                    instance = new ToastUtil();
                }
            }
        }
        return instance;
    }


}

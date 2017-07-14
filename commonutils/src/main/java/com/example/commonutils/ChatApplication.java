package com.example.commonutils;

import android.content.Context;

/**
 * @description: chat获取app中的环境
 * @author:袁东华
 * @time:2016/12/19 下午12:55
 */
public class ChatApplication {
    private static ChatApplication instance;
    private Context context;

    /**
     * @description:
     * @author: 袁东华
     * created at 2016/11/13 15:58
     */
    public void initContext(Context c) {
        context = c;
    }

    public Context getContext() {
        return context;
    }

    private ChatApplication() {
    }

    public static ChatApplication getInstance() {
        if (instance == null) {
            synchronized (ChatApplication.class) {
                if (instance == null) {
                    instance = new ChatApplication();
                }
            }
        }
        return instance;
    }


}

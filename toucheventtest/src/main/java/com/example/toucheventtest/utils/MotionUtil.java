package com.example.toucheventtest.utils;

import android.view.MotionEvent;

/**
 * Created by sunzh on 2017/7/14.
 */

public class MotionUtil {
    public static String getAction(MotionEvent event) {
        int action = event.getAction();
        String str = "";
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                str = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                str = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_CANCEL:
                str = "ACTION_CANCEL";
                break;
            case MotionEvent.ACTION_UP:
                str = "ACTION_UP";
                break;
        }
        return str;
    }
}

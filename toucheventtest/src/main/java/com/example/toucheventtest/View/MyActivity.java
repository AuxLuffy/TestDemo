package com.example.toucheventtest.View;

import android.app.Activity;
import android.view.MotionEvent;

import com.example.commonutils.LogUtil;
import com.example.toucheventtest.utils.MotionUtil;

/**
 * Created by sunzh on 2017/7/14.
 */

public class MyActivity extends Activity {
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i("action:" + MotionUtil.getAction(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i(MotionUtil.getAction(event));
        return super.onTouchEvent(event);
    }
}

package com.example.toucheventtest.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.commonutils.LogUtil;
import com.example.toucheventtest.utils.MotionUtil;

/**
 * Created by sunzh on 2017/7/14.
 */

public class MyViewGroup extends RelativeLayout {
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i("action:" + MotionUtil.getAction(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.i("action:" + MotionUtil.getAction(ev));
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            return super.onInterceptTouchEvent(ev);
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i("action:" + MotionUtil.getAction(event));
        return true;
//        return super.onTouchEvent(event);
    }
}

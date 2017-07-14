package com.example.toucheventtest.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.commonutils.LogUtil;
import com.example.toucheventtest.utils.MotionUtil;

/**
 * Created by sunzh on 2017/7/14.
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i("action:" + MotionUtil.getAction(ev));
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i("action:" + MotionUtil.getAction(event));
        return true;
    }

}

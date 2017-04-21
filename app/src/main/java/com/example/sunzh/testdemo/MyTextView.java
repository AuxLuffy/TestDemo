package com.example.sunzh.testdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by sunzh on 2017/3/21.
 */

public class MyTextView extends TextView {
    public static final String TAG = "MyTextView";

    public MyTextView(Context context) {
        this(context, null);
//        setText("test test");
    }


    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("MyTextView", "onMeasure()");
        Log.e(TAG, "width: " + getWidth() + ", height: " + getHeight());
        Log.e(TAG, "MeasuredWidth: " + getMeasuredWidth() + ", MeasuredHeight: " + getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("MyTextView", "onLayout()");
        Log.e(TAG, "width: " + getWidth() + ", height: " + getHeight());
        Log.e(TAG, "MeasuredWidth: " + getMeasuredWidth() + ", MeasuredHeight: " + getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw()");
//        measure(0, 0);
        Log.e(TAG, "width: " + getWidth() + ", height: " + getHeight());
        Log.e(TAG, "MeasuredWidth: " + getMeasuredWidth() + ", MeasuredHeight: " + getMeasuredHeight());

    }

    /**
     * 只有布局的方式才会调用
     * 重写目的：得到子view(getChildAt(int index))
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e(TAG, "onFinishInflate()");
    }

    /**
     * 重写得到子view
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "onAttachedToWindow()");
    }
}

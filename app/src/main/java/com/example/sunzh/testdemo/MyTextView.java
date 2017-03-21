package com.example.sunzh.testdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by sunzh on 2017/3/21.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    public MyTextView(Context context) {
        this(context, null);
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
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("MyTextView", "onLayout()");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("MyTextView", "onDraw()");
    }
}

package com.example.sunzh.testdemo;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by sunzh on 2017/4/25.
 */

public class MySwipeRefreshView extends SwipeRefreshLayout {

    private Context mContext;

    public boolean isLoading() {
        return isLoading;
    }

    //正在加载状态
    private boolean isLoading;
    private String tag = "MySwipeRefreshView";

    public void setmOnLoadListener(OnLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }

    private OnLoadListener mOnLoadListener;

    public interface OnLoadListener {
        void onLoad();
    }

    public MySwipeRefreshView(Context context) {
        super(context);
        mContext = context;
    }

    public MySwipeRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    private ListView mlistview;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (getChildCount() >= 1) {
            View childAt = getChildAt(0);
            if (childAt instanceof ListView) {
                mlistview = (ListView) childAt;
                setListViewOnScroll();
            }
        }
    }

    private void setListViewOnScroll() {
        mlistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (canLoadMore()) {
                    loadData();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * 处理数据加载的逻辑
     */
    private void loadData() {
        Log.d(tag, "加载数据...");
        if (mOnLoadListener != null && !isLoading) {
            //设置加载状态，让布局显示出来
            setLoading(true);
            mOnLoadListener.onLoad();
        }
    }

    /**
     * 设置加载状态，是否加载传入boolean进行判断
     *
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;

        View mfooterview = View.inflate(mContext, R.layout.loadmore_layout, null);
        if (isLoading) {
            //显示布局
            mlistview.addFooterView(mfooterview);
        } else {
            //隐藏布局
            mlistview.removeFooterView(mfooterview);
            //重置滑动的坐标
            mDownY = 0;
            mUpY = 0;
        }
    }

    /**
     * 判断是否满足加载更多的条件
     *
     * @return
     */
    private boolean canLoadMore() {
        //首先是上拉状态
        boolean condition1 = mDownY - mUpY >= ViewConfiguration.get(mContext).getScaledTouchSlop();
        if (condition1) {
            Log.i(tag, "是上拉状态");
        }
        //当前页面可见的item是最后一个条目
        boolean condition2 = false;
        if (mlistview != null && mlistview.getAdapter() != null) {
            condition2 = mlistview.getLastVisiblePosition() == mlistview.getAdapter().getCount() - 1;
        }
        if (condition2) {
            Log.i(tag, "是最后一个条目");
        }
        //不是正在加载的状态
        boolean condition3 = !isLoading;
        if (condition3) {
            Log.i(tag, "不是正在加载状态");
        }
        return condition1 && condition2 && condition3;
    }

    private float mDownY, mUpY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //移动的起点
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (canLoadMore()) {
                    //加载数据
                    loadData();
                }
                break;
            case MotionEvent.ACTION_UP:
                //移动的终点
                mUpY = getY();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

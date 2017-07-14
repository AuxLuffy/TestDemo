package com.example.sunzh.testdemo;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RefreshActivity extends AppCompatActivity {

    /**
     * swipeRefresh
     */
    private TextView mTvTitle;
    private RelativeLayout mTitleLayout;
    private ListView mListview;
    private SwipeRefreshLayout mSwipeRL;
    private List<String> mData = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mData.add("工作好累 +" + i);
        }
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mData);
        mListview.setAdapter(mAdapter);
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTitleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        mListview = (ListView) findViewById(R.id.listview);
        mSwipeRL = (SwipeRefreshLayout) findViewById(R.id.swipeRL);
        //不能在onCreate中设置，这个表示当前是刷新状态，如果一进来就是刷新状态，swiperefreshlayout会屏蔽掉刷新事件
//        mSwipeRL.setRefreshing(true);
        //设置可以是否可以下拉
//        mSwipeRL.setEnabled(true);

        mSwipeRL.setColorSchemeResources(R.color.red, R.color.blue, R.color.green);
        mSwipeRL.setProgressBackgroundColorSchemeResource(R.color.trans_green);

        mSwipeRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //开始刷新，设置当前状态为刷新状态
                mSwipeRL.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.add(0, "工作好累 +" + random.nextInt(50));
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(RefreshActivity.this, "刷新了一条数据", Toast.LENGTH_SHORT).show();
                        //加载完数据设置为不刷新状态，将下拉进度收起来
                        mSwipeRL.setRefreshing(false);
                    }
                }, 2000);
            }
        });

//        mSwipeRL.setmOnLoadListener(new MySwipeRefreshView.OnLoadListener() {
//            @Override
//            public void onLoad() {
////                mSwipeRL.setLoading(true);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        loadMore();
//                        mSwipeRL.setLoading(false);
//                    }
//                }, 2000);
//            }
//        });
        mListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (!mSwipeRL.isRefreshing()) {
                    if (scrollState == SCROLL_STATE_IDLE && lastVisableItem >= mAdapter.getCount()) {
                        if (mAdapter.getCount() <= 30) {
                            loadMore();
                        } else {
                            Toast.makeText(RefreshActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastVisableItem = firstVisibleItem + visibleItemCount;
            }
        });
    }

    private int lastVisableItem;

    /**
     * 加载更多
     */
    private void loadMore() {
        Log.e("listview", "加载更多");
        mData.add("工作好累 +" + random.nextInt(50));
        mAdapter.notifyDataSetChanged();

        ContentValues contentValues = new ContentValues();//contentvalues里维护的是一个hashmap
        contentValues.put("title","Lenovo is a great company");
//        SQLiteDatabase db = SQLiteDatabase.openDatabase();
    }
}

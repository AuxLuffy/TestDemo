package com.example.sunzh.testdemo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "onCreate()");
//        textView = new MyTextView(this);
//        textView.setPadding(10, 10, 10, 10);
        setContentView(R.layout.activity_main);
        init();


        /*setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        Log.e("TAG", "onCreate()");
        Log.e("Activity", "onCreate()__textview.height:" + textView.getHeight() + ", textview.width:" + textView.getWidth());*/
    }

    private void init() {
        textView = (TextView) findViewById(R.id.textview);
        textView.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("TAG", "onRestart()");
        Log.e("Activity", "onRestart()__textview.height:" + textView.getHeight() + ", textview.width:" + textView.getWidth());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG", "onStart()");
        Log.e("Activity", "onStart()__textview.height:" + textView.getHeight() + ", textview.width:" + textView.getWidth());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "onResume()");
        Log.e("Activity", "onResume()__textview.height:" + textView.getHeight() + ", textview.width:" + textView.getWidth());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy()");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e("TAG", "onWindowFocusChanged()____hasFocus():" + hasFocus);
        Log.e("Activity", "onWindowFocusChanged()__textview.height:" + textView.getHeight() + ", textview.width:" + textView.getWidth());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("TAG", "onConfigurationChanged()");
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("TAG", "onRestoreInstanceState()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e("TAG", "onSaveInstanceState()");
    }

    /**
     * 当系统容易杀死此activity时会在onpause后调用
     * 1、当用户按下HOME键时。
     这是显而易见的，系统不知道你按下HOME后要运行多少其他的程序，自然也不知道activity A是否会被销毁，故系统会调用onSaveInstanceState，让用户有机会保存某些非永久性的数据。以下几种情况的分析都遵循该原则
     2、长按HOME键，选择运行其他的程序时。
     3、按下电源按键（关闭屏幕显示）时。
     4、从activity A中启动一个新的activity时。
     5、屏幕方向切换时，例如从竖屏切换到横屏时。
     http://gundumw100.iteye.com/blog/1115080


     android:theme="@android:style/Theme.Dialog"
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("TAG", "onSaveInstanceState()");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview:
//                toggleOrientation();
                openActivity(RefreshActivity.class);
                break;
        }
    }

    private void openActivity(Class activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
    }

    private void toggleOrientation() {
        Configuration configuration = getResources().getConfiguration();
        int orientation = configuration.orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {//此时是横屏
            //切换到竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {//当前是竖屏
            //切换至横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    @Override
    public Object onRetainCustomNonConfigurationInstance() {

        return super.onRetainCustomNonConfigurationInstance();

    }
}

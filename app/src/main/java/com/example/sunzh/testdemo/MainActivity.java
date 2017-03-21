package com.example.sunzh.testdemo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        Log.e("TAG", "onCreate()");
        Log.e("Activity", "onCreate()__textview.height:" + textView.getHeight() + ", textview.width:" + textView.getWidth());
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
}
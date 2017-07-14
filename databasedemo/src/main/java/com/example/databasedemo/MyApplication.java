package com.example.databasedemo;

import android.app.Application;
import android.content.Context;

import com.example.databasedemo.database.DBHelper;

/**
 * Created by sunzh on 2017/5/3.
 */

public class MyApplication extends Application {
    private Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        DBHelper.initDBHelper(appContext);
    }
}

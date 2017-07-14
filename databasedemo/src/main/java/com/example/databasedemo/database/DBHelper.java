package com.example.databasedemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sunzh on 2017/5/2.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE = "countinfo";
    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_NAME = "name";
    public static final String DATABASE = "Employees.db";
    public static final int DB_VERSION = 1;
    private static String tag = "DBHelper";
    private static DBHelper instance;
    private static Context mContext;

    private DBHelper(Context context) {
        /**
         * Para 1: 上下文
         * Para 2: 数据库名
         * Para 3: 游标工厂，可以设置为null
         * Para 4: 版本号
         */
        super(context, DATABASE, null, DB_VERSION);
    }

    public static void initDBHelper(Context context) {
        mContext = context;
    }

    public static DBHelper getInstance() {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(mContext);
                }
            }
        }
        return instance;
    }

    /**
     * 只有第一次创建时才会调用
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE + "(" + TABLE_ROW_ID + " integer not null primary key autoincrement," + TABLE_ROW_NAME + " varchar)");
        db.execSQL("insert into " + TABLE + " values(null,'Rose')");
        db.execSQL("insert into " + TABLE + " values(null,'Jack')");
        Log.d(tag, "数据库创建了,名字：" + this.getDatabaseName());
    }

    /**
     * 只有更新版本的时候才会调用
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(tag, "更新数据库");
    }
}

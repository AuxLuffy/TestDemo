package com.example.databasedemo.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by sunzh on 2017/5/3.
 */

public class DAO {
    private static String tag = "DAO";

    static {
        db = DBHelper.getInstance().getWritableDatabase();
    }

    private static SQLiteDatabase db;

    public static void deleta() {
        int delete = db.delete(DBHelper.TABLE, "name=?", new String[]{"sunzhangfei"});
        if (delete > -1) {
            Log.i(tag, "删除成功");
        } else {
            Log.e(tag, "删除失败");
        }
    }

    public static void add() {
        ContentValues values = new ContentValues();
        values.put(DBHelper.TABLE_ROW_NAME, "sunzhangfei");
        long insert = db.insert(DBHelper.TABLE, DBHelper.TABLE_ROW_NAME, values);
        if (insert >= -1) {
            Log.i(tag, "插入成功");
        } else {
            Log.e(tag, "插入失败");
        }
    }

    public static void update() {
        ContentValues values = new ContentValues();
        values.put(DBHelper.TABLE_ROW_NAME, "sunzhangfei1");
        int update = db.update(DBHelper.TABLE, values, "name=?", new String[]{"sunzhangfei"});
        if (update > -1) {
            Log.i(tag, "更新成功");
        } else {
            Log.e(tag, "更新失败");
        }
    }

    public static void query() {
        Cursor query = db.query(DBHelper.TABLE, null, null, null, null, null, null);
        StringBuilder sb = new StringBuilder();
        while (query.moveToNext()){
            int id = query.getInt(0);
            String name = query.getString(1);
            sb.append(id+", "+name);
        }
    }
}

package com.example.commonutils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 彤 on 2016/5/17.
 */
public class SharePreferenceUtils {
    private static SharePreferenceUtils sharePreferenceUtils;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREFER_NAME = "lenovoservice_logstate";
    private SharePreferenceUtils(Context context){
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFER_NAME,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    //单例模式
    public static SharePreferenceUtils getInstance(Context context){
        if(sharePreferenceUtils == null){
            sharePreferenceUtils = new SharePreferenceUtils(context);
        }
        return sharePreferenceUtils;
    }
    public void saveString(String key, String value){
        editor.putString(key,value);
        editor.commit();
    }
    public String getString(String key){
        return sharedPreferences.getString(key,"");
    }

    public void saveInt(String key, int value){
        editor.putInt(key,value);
        editor.commit();
    }
    public int getInt(String key){
        return sharedPreferences.getInt(key,0);
    }

    public void saveFloat(String key, float value){
        editor.putFloat(key,value);
        editor.commit();
    }
    public float getFloat(String key){
        return sharedPreferences.getFloat(key,0.0f);
    }
    public void saveBoo(String key, boolean boo){
        editor.putBoolean(key,boo);
        editor.commit();
    }
    public boolean getBoo(String key){
        return sharedPreferences.getBoolean(key,false);
    }
    public void removeKey(String key){
        editor.remove(key);
        editor.commit();
    }
}

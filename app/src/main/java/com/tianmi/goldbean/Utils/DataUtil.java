package com.tianmi.goldbean.Utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.tianmi.goldbean.GoldApplication;

/**
 * Created by fangchao on 2016/12/23.
 */

public class DataUtil {
    public static void putPreferences(String key, String value){
        Editor editor =  GoldApplication.getSP().edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getPreferences(String key, String defaultValue){
        String value = GoldApplication.getSP().getString(key, defaultValue);
        return value;
    }
    public static void clear(){
        Editor editor =  GoldApplication.getSP().edit();
        editor.clear();
        editor.commit();

    }
}

package com.tianmi.goldbean.Utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

/**
 * Created by fangchao on 2016/12/13.
 */

public class ActivityUtil {



    public static void startActivity(Activity curActivity, Class<?> nextActivity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            curActivity.startActivity(new Intent(curActivity, nextActivity), ActivityOptions.makeSceneTransitionAnimation(curActivity).toBundle());
        }else {
            curActivity.startActivity(new Intent(curActivity, nextActivity));
        }
    }

    public static void startActivity(Activity curActivity, Class<?> nextActivity, String str){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            Intent intent = new Intent(curActivity, nextActivity);
            intent.putExtra("key", str);
            curActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(curActivity).toBundle());
        }else {
            Intent intent = new Intent(curActivity, nextActivity);
            intent.putExtra("key", str);
            curActivity.startActivity(intent);
        }
    }
    public static void nextActivity(Activity curActivity, Class<?> nextActivity, int requestCode, String str){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            curActivity.startActivityForResult(new Intent(curActivity, nextActivity).putExtra("content", str), requestCode, ActivityOptions.makeSceneTransitionAnimation(curActivity).toBundle());
        }else {
            curActivity.startActivityForResult(new Intent(curActivity, nextActivity).putExtra("content", str), requestCode);
        }

    }
    public static void nextActivity(Activity activity, Intent intent, int requestCode){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            activity.startActivityForResult(intent, requestCode, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }else {
            activity.startActivityForResult(intent, requestCode);
        }

    }

    public static void nextActivity(Activity curActivity, Class<?> nextActivity, String str){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            Intent intent = new Intent(curActivity, nextActivity);
            intent.putExtra("value", str);
            curActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(curActivity).toBundle());
        }else {
            Intent intent = new Intent(curActivity, nextActivity);
            intent.putExtra("value", str);
            curActivity.startActivity(intent);
        }
    }

    public static void nextActivity(Activity curActivity, Class<?> nextActivity, Bundle b){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            Intent intent = new Intent(curActivity, nextActivity);
            intent.putExtras(b);
            curActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(curActivity).toBundle());
        }else {
            Intent intent = new Intent(curActivity, nextActivity);
            intent.putExtras(b);
            curActivity.startActivity(intent);
        }
    }

    public static void shareNextActivity(Activity curActivity, Class<?> nextActivity,View view, String shareName){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            Intent intent = new Intent(curActivity, nextActivity);
            curActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(curActivity, view, shareName).toBundle());
        }else {
            Intent intent = new Intent(curActivity, nextActivity);
            curActivity.startActivity(intent);
        }
    }

    public static void shareNextActivity(Activity curActivity, Class<?> nextActivity,View view, String shareName, Bundle b){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            Intent intent = new Intent(curActivity, nextActivity);
            intent.putExtras(b);
            curActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(curActivity, view, shareName).toBundle());
        }else {
            Intent intent = new Intent(curActivity, nextActivity);
            intent.putExtras(b);
            curActivity.startActivity(intent);
        }
    }
    public static void shareNextActivityForResult(Activity curActivity, Class<?> nextActivity,View view, String shareName){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            Intent intent = new Intent(curActivity, nextActivity);
            curActivity.startActivityForResult(intent, 0, ActivityOptions.makeSceneTransitionAnimation(curActivity, view, shareName).toBundle());
        }else {
            Intent intent = new Intent(curActivity, nextActivity);
            curActivity.startActivityForResult(intent, 0);
        }
    }


}

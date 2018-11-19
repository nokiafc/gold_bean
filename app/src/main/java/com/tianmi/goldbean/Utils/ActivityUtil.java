package com.tianmi.goldbean.Utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by fangchao on 2016/12/13.
 */

public class ActivityUtil {
    public static void setStatusBarColor(Activity activity,  String colorString){
        Window window = activity.getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.parseColor(colorString));
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }



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
    public static void startActivity(Activity curActivity, Class<?> nextActivity, Bundle b){
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

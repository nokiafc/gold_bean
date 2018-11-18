package com.tianmi.goldbean;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.mob.MobSDK;

import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;

public class GoldApplication extends Application {
    public static OkHttpClient client;
    public static Gson gson;
    private List<Activity> startList = new LinkedList<Activity>();
    public static GoldApplication goldInstance;
    private List<Activity> outList = new LinkedList<Activity>();

    public static SharedPreferences sp;
    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);//初始sharedSdk
        initOkHttpClient();
        sp = getSharedPreferences("com.tianmi.goldbean", 0);
    }

    public static GoldApplication getAppInstance() {
        if (goldInstance == null) {
            goldInstance = new GoldApplication();
        }

        return goldInstance;
    }

    public void finishActivity() {
        for (Activity activity : startList) {
            activity.finish();
        }
        startList.clear();
    }

    public void addActivity(Activity activity) {
        startList.add(activity);
    }

    public void finishOutActivity(){
        for (Activity activity : outList) {
            activity.finish();
        }
        outList.clear();
    }
    public void addOutActivity(Activity activity){
        outList.add(activity);
    }
    private void initOkHttpClient() {
        gson = new Gson();
        client = new OkHttpClient();


    }
    public static OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient();
        }
        return client;
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static SharedPreferences getSP() {
        return sp;
    }


}

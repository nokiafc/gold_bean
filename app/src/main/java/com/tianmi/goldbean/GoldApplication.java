package com.tianmi.goldbean;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;

public class GoldApplication extends Application {
    public static OkHttpClient client;
    public static Gson gson;

    public static SharedPreferences sp;
    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttpClient();
        sp = getSharedPreferences("com.tianmi.goldbean", 0);
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

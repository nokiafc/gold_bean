package com.tianmi.goldbean;

import android.app.Application;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;

public class GoldApplication extends Application {
    public static OkHttpClient client;
    public static Gson gson;
    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttpClient();
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
}

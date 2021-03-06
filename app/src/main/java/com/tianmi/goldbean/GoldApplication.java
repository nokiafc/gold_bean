package com.tianmi.goldbean;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tianmi.goldbean.config.Config;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
//import com.mob.MobSDK;

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
    public static IWXAPI api;
    @Override
    public void onCreate() {
        super.onCreate();

        initOkHttpClient();
        sp = getSharedPreferences("com.tianmi.goldbean", 0);
        initUmeng();
        getWechatApi();
    }
    private void getWechatApi(){
        if(api == null){
            api = WXAPIFactory.createWXAPI(this, Config.APP_ID, true);
        }
    }
    private void initUmeng(){
        UMConfigure.init(this, "5bf6ad36b465f5c37d0000e6", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(true);
        PlatformConfig.setWeixin("wxab96184a9724d08d", "690edcab026711419a4ba75a2aa76005");
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

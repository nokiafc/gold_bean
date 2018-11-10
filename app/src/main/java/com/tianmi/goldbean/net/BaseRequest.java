package com.tianmi.goldbean.net;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tianmi.goldbean.Config;
import com.tianmi.goldbean.GoldApplication;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by fangchao on 2016/12/14.
 */

public class BaseRequest {
    private JsonCallback jsonCallback;
    private Activity activity;
    private Gson gson;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private final OkHttpClient clientPNG = new OkHttpClient();
    private final int SUCCESS_RESULT = 0;
    private final int SUCCESS_NO_RESULT = 1;
    private final int TOKEN_INVALID = 2;
    private final int PASSWORD_ERROR = 3;
    private final int NET_ERROR = 4;
    private final int FAIL_MESSAGE = 5;
    private final int ERROR_ORDER = 6;
    private final int DATA_ERROR = 8;
    private final int PIC_ERROR = 9;
    private int serversLoadTimes = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if (msg.what == SUCCESS_RESULT) {//请求成功
                    Object object = msg.obj;
                    jsonCallback.onResponse(object, object.toString());
                } else if (msg.what == SUCCESS_NO_RESULT) {
                    Object object = msg.obj;
                    jsonCallback.onResponse(true, object.toString());
                }

            } catch (Exception e) {

            }


        }
    };


    public void setCallback(JsonCallback jsonCallback) {
        this.jsonCallback = jsonCallback;
    }

    public void post(String url, Map<String, Object> map, final Activity activity) {
        this.activity = activity;
        gson = GoldApplication.getGson();
        JSONObject json = new JSONObject(map);

        RequestBody requestBody = RequestBody.create(JSON, gson.toJson(map));
        Request request = new Request.Builder()
                .url(Config.BASE_URL + url)
                .post(requestBody)
                .build();

        GoldApplication.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    String jsonStr = response.body().string();

                    if (!TextUtils.isEmpty(jsonStr)) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);

                            String statusCode = jsonObject.getString("code");
                            Log.d("FC", "statusCode----" + statusCode);
                            if (statusCode.equals("200")) {

                                String data;
                                if (jsonObject.has("data")) {
                                    data = jsonObject.getString("data");
                                    if (!data.equals("null")) {
                                        Log.d("FC", "data----" + data);

                                        Message msg = Message.obtain();
                                        Object object = gson.fromJson(data, jsonCallback.getType());

                                        msg.what = SUCCESS_RESULT;
                                        msg.obj = object;


                                        handler.sendMessage(msg);
                                    } else {
                                        String message = jsonObject.getString("message");
                                        Message msg = Message.obtain();
                                        msg.what = SUCCESS_NO_RESULT;
                                        msg.obj = message;
                                        handler.sendMessage(msg);
                                    }

                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }


    public Map<Object, Object> getMap(Map<Object, Object> map) {
        Map<Object, Object> m = new HashMap<Object, Object>();
        m.put("content", map);
        return m;
    }

    public Map<String, Object> getMapObject(Map<String, Object> map) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("content", map);
        return m;
    }

}

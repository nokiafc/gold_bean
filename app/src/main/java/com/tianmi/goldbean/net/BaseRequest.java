package com.tianmi.goldbean.net;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tianmi.goldbean.GoldApplication;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.config.Config;

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
    private final int SUCCESS_PIC = 2;

    private final int NET_ERROR = 4;
    private int serversLoadTimes = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if (msg.what == SUCCESS_RESULT) {//请求成功
                    Object object = msg.obj;
                    jsonCallback.onResponse(object, object.toString());
                } else if (msg.what == SUCCESS_PIC) {
                    String picUrl = (String)msg.getData().get("picUrl");
                    jsonCallback.onResponse(picUrl, "");
                }else if(msg.what == SUCCESS_NO_RESULT){
                    jsonCallback.onResponse(true, "");
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
                .addHeader("accessToken", DataUtil.getPreferences("accessToken", ""))
                .post(requestBody)
                .build();
        Log.d("FC", DataUtil.getPreferences("accessToken", ""));
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
                                        Log.d("FC", "noresult");
                                        Message msg = Message.obtain();
                                        msg.what = SUCCESS_NO_RESULT;
                                        handler.sendMessage(msg);
                                    }

                                }else {
                                    Log.d("FC", "noresult");
                                    Message msg = Message.obtain();
                                    msg.what = SUCCESS_NO_RESULT;
                                    handler.sendMessage(msg);
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

    public synchronized void upLoadImg(List<String> urlList) {
        if (urlList == null || urlList.size() == 0) {
            return;
        }
        serversLoadTimes = 0;
        File f = new File(urlList.get(0));

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fileName", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
//        for (int i = 0; i < urlList.size(); i++) {
//            File f = new File(urlList.get(i));
//            if (f != null) {
//                builder.addFormDataPart("img" + i, f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
//            }
//        }


        gson = GoldApplication.getGson();
        MultipartBody requestBody = builder.build();//
        Request request = new Request.Builder()
                .url(Config.PICTURE_URL)//地址
                .addHeader("accessToken", DataUtil.getPreferences("accessToken", ""))
                .post(requestBody)//添加请求体
                .build();

        clientPNG.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String message = e.getMessage();
                if (null != e.getMessage() && e.getMessage().equals("timeout") && serversLoadTimes <= 5) {
                    serversLoadTimes++;
                    clientPNG.newCall(call.request()).enqueue(this);
                } else {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = NET_ERROR;
                    handler.sendMessage(msg);
                }
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
                                        Bundle b = new Bundle();
                                        b.putString("picUrl", data);
                                        msg.setData(b);
                                        msg.what = SUCCESS_PIC;
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
                } else {
                    Log.e("error", response.toString());
                    Message msg = Message.obtain();
                    msg.what = NET_ERROR;
                    handler.sendMessage(msg);
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

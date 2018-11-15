package com.tianmi.goldbean.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.GoldApplication;
import com.tianmi.goldbean.MainActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.RecyclerBean;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

public class SplashActivity extends BaseActivity {
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                ActivityUtil.startActivity(SplashActivity.this, MainActivity.class);

            }else {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GoldApplication.getAppInstance().addActivity(this);
        init();
    }
    private void init(){
        if(DataUtil.getPreferences("accessToken", "").equals("")){//第一次登陆没有存token
            Message msg = Message.obtain();
            msg.what= 0;
            handler.sendMessageDelayed(msg, 1000);
        }else {//请求个接口判断token是否过期
            getMainInfo();
        }
    }

    private void getMainInfo(){
        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.getMainInfo(1, 10);
        requestInterface.setCallback(new JsonCallback<List<RecyclerBean>>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(List<RecyclerBean> list, String message) throws IOException {
                Message msg = Message.obtain();
                msg.what= 1;
                handler.sendMessageDelayed(msg, 1000);
            }
        });


    }
}

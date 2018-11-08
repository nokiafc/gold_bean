package com.tianmi.goldbean.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tianmi.goldbean.R;

public class SplashActivity extends Activity {
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }
    private void init(){
        new Thread(){
            @Override
            public void run() {
                try{
                    Thread.sleep(1500);
                }catch (Exception e){

                }
                handler.sendEmptyMessage(-1);

            }
        }.start();
    }
}

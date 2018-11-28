package com.tianmi.goldbean.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.GoldApplication;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.ImageVerifyDialog;
import com.tianmi.goldbean.bean.SonManagerBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

public class ForgetPsdActivity extends BaseActivity implements View.OnClickListener , ImageVerifyDialog.NewImgCallBack{
    private Button next, codeBtn;
    private EditText phoneEdit, smsEdit;
    private String userId = "";

    @Override
    public void getNewMsg() {
        getImgCode();
    }

    @Override
    public void getSmsMessage(String str) {
        getMessage(str);
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (msg.arg1 == 0) {
                        codeBtn.setText("获取验证码");
                        codeBtn.setClickable(true);
                    } else {
                        codeBtn.setText("(" + msg.arg1 + ")秒");
                        codeBtn.setClickable(false);
                    }
                    break;
            }
        }
    }
    private MyHandler handler = new MyHandler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_forget_psd);
        GoldApplication.getAppInstance().addActivity(this);
        initTitle("忘记密码");
        init();
    }
    private void init(){
        smsEdit = (EditText)findViewById(R.id.edit_verify_code) ;
        phoneEdit = (EditText)findViewById(R.id.edit_phone) ;
        codeBtn = (Button)findViewById(R.id.button_verify);
        codeBtn.setOnClickListener(this);
        next = (Button)findViewById(R.id.btn_forget_next);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(phoneEdit.getText().toString() == null || phoneEdit.getText().toString().equals("")|| phoneEdit.getText().toString().length() <11){
            Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()){
            case R.id.button_verify://获取验证码
                imgDialog = new ImageVerifyDialog(this, bitmap);
                imgDialog.setNewImg(this);
                imgDialog.show();
                getImgCode();

                break;
            case R.id.btn_forget_next://下一步
                if(smsEdit.getText() == null || smsEdit.getText().toString().equals("")){
                    Toast.makeText(this, "请输入短信验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //先判断短信是否正确
                checkSMS(phoneEdit.getText().toString(), smsEdit.getText().toString(), "updatePassword");
                break;
        }
    }
    private void checkSMS(String phone, String smsCode, String source){
        RequestInterface request  = new RequestInterface(this);
        request.verifySMS(phone, smsCode, source);
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                //验证成功 跳转输入密码页面
                ActivityUtil.startActivity(ForgetPsdActivity.this, ChangePsdActivity.class, phoneEdit.getText().toString());
                finishAfterTransition();
            }
        });
    }

    private void getImgCode(){
        RequestInterface request = new RequestInterface(this);
        request.getImgCode(phoneEdit.getText().toString(), "updatePassword");
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                byte[] array = (byte[])o;
                bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
                if(imgDialog != null){
                    imgDialog.setNewImg(bitmap);
                }

            }
        });

    }
    private Bitmap bitmap;
    private ImageVerifyDialog imgDialog;


    private void getMessage(String str){
        RequestInterface request = new RequestInterface(this);
        request.getSMS(phoneEdit.getText().toString(), "updatePassword", str);
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();
                codeBtn.setText("获取验证码");
                codeBtn.setClickable(true);
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 59; i >= 0; i--) {
                            Message msg = Message.obtain();
                            msg.arg1 = i;
                            handler.sendMessage(msg);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

    }
}

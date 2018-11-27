package com.tianmi.goldbean.login;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.GoldApplication;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.ImageVerifyDialog;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.RegisterBean;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Request;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, ImageVerifyDialog.NewImgCallBack {
    private EditText userName, userPsd, smsEdit;
    private Button registerBtn;
    private Button messageBtn;



    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        if (msg.arg1 == 0) {
                            messageBtn.setText("获取验证码");
                            messageBtn.setClickable(true);
                        } else {
                            messageBtn.setText("(" + msg.arg1 + ")秒");
                            messageBtn.setClickable(false);
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
        setContentView(R.layout.activity_register);
        GoldApplication.getAppInstance().addActivity(this);
        init();

        //1进入这个页面就出现图片验证
        //2提交提示必须输入图片验证码
    }
    private void init(){
        smsEdit = (EditText)findViewById(R.id.edit_sms);
        messageBtn = (Button)findViewById(R.id.button_verify);
        messageBtn.setOnClickListener(this);
        userName = (EditText)findViewById(R.id.edit_username);
        userPsd = (EditText)findViewById(R.id.edit_psd);
        registerBtn = (Button)findViewById(R.id.btn_register);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void getNewMsg() {
        getImgCode();
    }
    public void getSmsMessage(String str){
        getMessage(str);
    }
    private void getImgCode(){
        RequestInterface request = new RequestInterface(this);
        request.getImgCode(userName.getText().toString(), "regist");
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

    @Override
    public void onClick(View v) {
        if(userName.getText().toString() == null || userName.getText().toString().equals("") || userName.getText().toString().length()<11){
            Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()){
            case R.id.btn_register://注册
                if(smsEdit.getText() == null || smsEdit.getText().toString().equals("")){
                    Toast.makeText(this, "请输入短信验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //先判断短信是否正确
                checkSMS(userName.getText().toString(), smsEdit.getText().toString(), "regist");
                //再注册
            break;
            case R.id.button_verify://获取短信验证码
                imgDialog = new ImageVerifyDialog(this, bitmap);
                imgDialog.setNewImg(this);
                imgDialog.show();
                getImgCode();
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
                register();
            }
        });
    }
    private void getMessage(String str){
        RequestInterface request = new RequestInterface(this);
        request.getSMS(userName.getText().toString(), "regist", str);
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();
                messageBtn.setText("获取验证码");
                messageBtn.setClickable(true);
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
    private void register(){
        String name = userName.getText().toString().trim();
        String password = userPsd.getText().toString().trim();
        if(name.equals("") || name == null){
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.equals("") || password == null){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.register(name, password, 0, 0, "11");
        requestInterface.setCallback(new JsonCallback<RegisterBean>() {
            @Override
            public void onError(Request request, String e) {
                Toast.makeText(getApplicationContext(), e,Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onResponse(RegisterBean bean, String message) throws IOException {
                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                finishAfterTransition();
            }
        });

    }
}

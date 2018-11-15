package com.tianmi.goldbean.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.GoldApplication;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.RegisterBean;

import java.io.IOException;

import okhttp3.Request;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText userName, userPsd;
    private Button registerBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_register);
        GoldApplication.getAppInstance().addActivity(this);
        init();
    }
    private void init(){
        userName = (EditText)findViewById(R.id.edit_username);
        userPsd = (EditText)findViewById(R.id.edit_psd);
        registerBtn = (Button)findViewById(R.id.btn_register);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                register();
            break;
        }
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

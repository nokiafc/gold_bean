package com.tianmi.goldbean.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.tianmi.goldbean.MainActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.LoginBean;

import java.io.IOException;

import okhttp3.Request;

public class LoginActivity extends Activity implements View.OnClickListener {
    private RelativeLayout forgetPsdLayout, newRegisterLayout;
    private Button login;
    private EditText userName, password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init(){
        userName = (EditText)findViewById(R.id.edit_username) ;
        password = (EditText)findViewById(R.id.edit_psd) ;
        login = (Button)findViewById(R.id.btn_login);
        login.setOnClickListener(this);
        forgetPsdLayout = (RelativeLayout)findViewById(R.id.layout_forget_psd);
        forgetPsdLayout.setOnClickListener(this);
        newRegisterLayout = (RelativeLayout)findViewById(R.id.layout_new_register);
        newRegisterLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.layout_forget_psd){
            Intent i = new Intent(this, ForgetPsdActivity.class);
            startActivity(i);
        }else if(id == R.id.layout_new_register){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }else if(id == R.id.btn_login){
            login();

        }
    }
    private void login(){
        String name = userName.getText().toString().trim();
        String psd = password.getText().toString().trim();

        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.login(name, psd);
        requestInterface.setCallback(new JsonCallback<LoginBean>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(LoginBean o, String message) throws IOException {
            Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(loginIntent);
            finish();
            }
        });
    }
}

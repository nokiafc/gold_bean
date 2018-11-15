package com.tianmi.goldbean.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.GoldApplication;
import com.tianmi.goldbean.MainActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.MyDialog;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.LoginBean;

import java.io.IOException;

import okhttp3.Request;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout forgetPsdLayout, newRegisterLayout;
    private Button login;
    private EditText userName, password;
    private Dialog myDialog ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_login);
        GoldApplication.getAppInstance().addActivity(this);
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
            ActivityUtil.startActivity(this, ForgetPsdActivity.class);
        }else if(id == R.id.layout_new_register){
            ActivityUtil.startActivity(this, RegisterActivity.class);
        }else if(id == R.id.btn_login){
            login();

        }
    }
    private void login(){
        myDialog = MyDialog.createLoadingDialog(this, "加载中...");
        myDialog.show();

        String name = userName.getText().toString().trim();
        String psd = password.getText().toString().trim();

        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.login(name, psd);
        requestInterface.setCallback(new JsonCallback<LoginBean>() {
            @Override
            public void onError(Request request, String e) {
                myDialog.dismiss();
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(LoginBean bean, String message) throws IOException {
                myDialog.dismiss();

            DataUtil.putPreferences("accessToken", bean.getAccessToken());
            DataUtil.putPreferences("userId", bean.getUserId()+"");
            DataUtil.putPreferences("userPhone", bean.getUserPhone()+"");
                DataUtil.putPreferences("userRecommendCode", bean.getUserRecommendCode()+"");
                DataUtil.putPreferences("userRole", bean.getUserRole()+"");
                DataUtil.putPreferences("merchantsFlag", bean.getMerchantsFlag()+"");
            ActivityUtil.startActivity(LoginActivity.this, MainActivity.class);
            finish();
            }
        });
    }
}

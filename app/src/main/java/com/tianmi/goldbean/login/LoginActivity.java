package com.tianmi.goldbean.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tianmi.goldbean.MainActivity;
import com.tianmi.goldbean.R;

public class LoginActivity extends Activity implements View.OnClickListener {
    private RelativeLayout forgetPsdLayout, newRegisterLayout;
    private Button login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init(){
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
            Intent loginIntent = new Intent(this, MainActivity.class);
            startActivity(loginIntent);
        }
    }
}

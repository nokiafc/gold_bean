package com.tianmi.goldbean.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;

public class ForgetPsdActivity extends BaseActivity {
    private Button next;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psd);
        initTitle("忘记密码");
        init();
    }
    private void init(){
        next = (Button)findViewById(R.id.btn_forget_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgetPsdActivity.this, ChangePsdActivity.class);
                startActivity(i);
            }
        });
    }
}

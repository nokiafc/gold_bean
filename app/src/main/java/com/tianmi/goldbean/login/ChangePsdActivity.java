package com.tianmi.goldbean.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;

import okhttp3.Request;

public class ChangePsdActivity extends BaseActivity {
    private EditText psdEdit, psdAgainEdit;
    private Button confirmBtn;
    private String phone = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psd);
        initTitle("修改密码");
        phone = getIntent().getStringExtra("key");
        init();
    }
    private void init(){
        psdAgainEdit = (EditText)findViewById(R.id.edit_psd_again);
        psdEdit = (EditText)findViewById(R.id.edit_psd);
        confirmBtn = (Button)findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(psdEdit.getText() == null || psdEdit.getText().toString().equals("")){
                    Toast.makeText(ChangePsdActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(psdAgainEdit.getText() == null || psdAgainEdit.getText().toString().equals("")){
                    Toast.makeText(ChangePsdActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!psdEdit.getText().toString().equals(psdAgainEdit.getText().toString())){
                    Toast.makeText(ChangePsdActivity.this, "两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                setPsd();
            }
        });
    }

    private void setPsd(){
        RequestInterface request = new RequestInterface(this);
        request.changePsd(phone, psdEdit.getText().toString());
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                Toast.makeText(ChangePsdActivity.this, e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                finishAfterTransition();
            }
        });
    }
}

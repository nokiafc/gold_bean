package com.tianmi.goldbean.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.net.RequestInterface;

public class SonActivity extends BaseActivity implements View.OnClickListener{
    private EditText phoneEdit;
    private Button addBtn, deleteBtn;
    private String userPhone ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_son);
        initTitle("子管理员");
        init();
    }
    private void init(){
        phoneEdit = (EditText)findViewById(R.id.add_edit);
        addBtn = (Button)findViewById(R.id.add_btn);
        addBtn.setOnClickListener(this);

        deleteBtn = (Button)findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        userPhone = phoneEdit.getText().toString().trim();
        switch (v.getId()){

            case R.id.delete_btn:
                getUsers(userPhone);
                break;
            case R.id.add_btn://添加子管理员, 需要先通过手机号查询出userid，然后调接口添加
                getUsers(userPhone);
                break;

        }
    }

    public void getUsers(String userPhone){
        if(userPhone == null || userPhone.equals("")){
            Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestInterface request = new RequestInterface(this);
        request.managerGetUserInfo(userPhone, 1, 10);
    }
}

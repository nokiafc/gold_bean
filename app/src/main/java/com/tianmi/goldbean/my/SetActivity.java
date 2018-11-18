package com.tianmi.goldbean.my;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.GoldApplication;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.MyDialog;
import com.tianmi.goldbean.Utils.RechargeDialog;
import com.tianmi.goldbean.login.LoginActivity;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;

import okhttp3.Request;

public class SetActivity extends BaseActivity implements View.OnClickListener{
    private Button loginOut;
    private RelativeLayout managerLayout;
    private String userRole = DataUtil.getPreferences("userRole", "");
    private String userId = DataUtil.getPreferences("userId", "");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_set);
        initTitle("设置");
        init();
    }
    private void init(){
        managerLayout = (RelativeLayout)findViewById(R.id.manager_layout);
        managerLayout.setOnClickListener(this);
        if(userRole.equals("1")){//管理员显示出操作布局
            managerLayout.setVisibility(View.VISIBLE);
        }
        loginOut = (Button)findViewById(R.id.btn_out);
        loginOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.manager_layout:
                ActivityUtil.startActivity(this, ManagerActivity.class);

                break;
            case R.id.btn_out:
                loginOut();
                break;
        }
    }
    private Dialog myDialog ;
    private void loginOut(){
        myDialog = MyDialog.createLoadingDialog(this, "正在退出...");
        myDialog.show();
        RequestInterface request = new RequestInterface(this);
        request.loginOut(Integer.parseInt(userId));
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                myDialog.dismiss();
                Toast.makeText(SetActivity.this, e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                myDialog.dismiss();
                DataUtil.clear();
                GoldApplication.getAppInstance().finishOutActivity();
                ActivityUtil.startActivity(SetActivity.this, LoginActivity.class);
                finishAfterTransition();

            }
        });
    }
}

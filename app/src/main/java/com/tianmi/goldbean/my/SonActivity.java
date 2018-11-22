package com.tianmi.goldbean.my;

import android.app.Dialog;
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
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.MyDialog;
import com.tianmi.goldbean.bean.SonManagerBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

public class SonActivity extends BaseActivity implements View.OnClickListener{
    private EditText phoneEdit;
    private Button addBtn, deleteBtn;
    private String userPhone ;
    private String userId = DataUtil.getPreferences("userId", "");
    private Dialog myDialog;
    private String flag = "1";//1是添加，0是删除

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

            case R.id.add_btn:
                flag = "1";
                getUsers(userPhone, flag);
                break;
            case R.id.delete_btn://添加子管理员, 需要先通过手机号查询出userid，然后调接口添加
                flag = "0";
                getUsers(userPhone, flag);
                break;

        }
    }

    public void getUsers(String userPhone, final String flag){
        if(userPhone == null || userPhone.equals("")){
            Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        myDialog = MyDialog.createLoadingDialog(this, "提交中...");
        myDialog.show();
        RequestInterface request = new RequestInterface(this);
        request.managerGetUserInfo(userPhone, 1, 10);
        request.setCallback(new JsonCallback<List<SonManagerBean >>() {
            @Override
            public void onError(Request request, String e) {
                myDialog.dismiss();
                Toast.makeText(SonActivity.this, e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<SonManagerBean > list, String message) throws IOException {
                int sonUserId = list.get(0).getUserId();
                if(flag.equals("1")){
                    setSonManager(sonUserId);
                }else if(flag.equals("0")) {
                    removeSonManager(sonUserId);
                }

            }
        });
    }

    private void removeSonManager(int sonUserId){
        RequestInterface request = new RequestInterface(this);
        request.removeSonManager(sonUserId, Integer.parseInt(userId));
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                myDialog.dismiss();
                Toast.makeText(SonActivity.this, e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                myDialog.dismiss();
                Toast.makeText(SonActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setSonManager(int sonUserId){
        RequestInterface request = new RequestInterface(SonActivity.this);
        request.addSonManager(sonUserId, Integer.parseInt(userId));
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                myDialog.dismiss();
                Toast.makeText(SonActivity.this, e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                myDialog.dismiss();
                Toast.makeText(SonActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

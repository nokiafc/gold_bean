package com.tianmi.goldbean.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.RechargeDialog;

public class ManagerActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout verifyLayout, addLayout, cashLayout, friendLayout, memberLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_manager);
        initTitle("管理员");
        init();
    }
    private void init(){
        memberLayout = findViewById(R.id.member_layout);
        memberLayout.setOnClickListener(this);
        friendLayout = findViewById(R.id.friend_layout);
        friendLayout.setOnClickListener(this);
        verifyLayout = (RelativeLayout)findViewById(R.id.manager_verify);
        verifyLayout.setOnClickListener(this);
        addLayout = (RelativeLayout)findViewById(R.id.manager_add);
        addLayout.setOnClickListener(this);
        cashLayout = (RelativeLayout)findViewById(R.id.cash_layout);
        cashLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.manager_verify://审核上架
                ActivityUtil.startActivity(this, ManagerVerifyActivity.class);
                break;
            case R.id.manager_add://添加子管理员
                ActivityUtil.startActivity(this, SonActivity.class);
                break;
            case R.id.cash_layout://添加子管理员
                ActivityUtil.startActivity(this, ManagerCashActivity.class);
                break;
            case R.id.friend_layout:
                ActivityUtil.startActivity(this, CheckFriendInfoActivity.class);
                break;
            case R.id.member_layout://审核用户上传的朋友圈信息
                ActivityUtil.startActivity(this, CheckmemberUpListActivity.class);
                break;
        }
    }
}

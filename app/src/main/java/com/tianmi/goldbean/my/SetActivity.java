package com.tianmi.goldbean.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.RechargeDialog;

public class SetActivity extends BaseActivity implements View.OnClickListener{
    private Button loginOut;
    private RelativeLayout managerLayout;
    private String userRole = DataUtil.getPreferences("userRole", "");
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
        loginOut = (Button)findViewById(R.id.btn_confirm_cash);
        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeDialog dialog = new RechargeDialog(SetActivity.this , "选择到账方式");
                dialog.showDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.manager_layout:
                ActivityUtil.startActivity(this, ManagerActivity.class);

                break;
            case R.id.btn_confirm_cash:
                break;
        }
    }
}

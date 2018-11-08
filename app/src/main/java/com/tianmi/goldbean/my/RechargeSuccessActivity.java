package com.tianmi.goldbean.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;

public class RechargeSuccessActivity extends BaseActivity {
    private Button rechargeBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initTitle("充值");
        init();
    }
    private void init(){
        rechargeBtn = (Button)findViewById(R.id.btn_confirm_recharge);
        rechargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}

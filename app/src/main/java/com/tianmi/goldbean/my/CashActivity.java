package com.tianmi.goldbean.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.RechargeDialog;

public class CashActivity extends BaseActivity {
    private Button cashBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        initTitle("提现");
        init();
    }
    private void init(){
        cashBtn = (Button)findViewById(R.id.btn_confirm_cash);
        cashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeDialog dialog = new RechargeDialog(CashActivity.this , "选择到账方式");
                dialog.showDialog();
            }
        });
    }
}

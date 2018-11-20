package com.tianmi.goldbean.my;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.MyDialog;
import com.tianmi.goldbean.Utils.RechargeDialog;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;

import okhttp3.Request;

public class CashActivity extends BaseActivity implements RechargeDialog.MyPayCallBack{
    private Button cashBtn;
    private String userId = DataUtil.getPreferences("userId", "");
    private Dialog myDialog;
    private EditText cashEdit;
    private String amountAccount = "";
    private TextView canUseMoney;
    private String cashAmount = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_cash);
        amountAccount = getIntent().getStringExtra("key");
        initTitle("提现");
        init();
    }
    private void init(){
        canUseMoney = (TextView)findViewById(R.id.text_use_money);
        canUseMoney.setText(amountAccount);
        cashEdit = (EditText)findViewById(R.id.edit_cash) ;
        cashBtn = (Button)findViewById(R.id.btn_confirm_cash);
        cashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashAmount = cashEdit.getText().toString().trim();
                if(cashAmount == null || cashAmount.equals("")){
                    Toast.makeText(CashActivity.this, "请输入正确的金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(cashAmount) <10){
                    Toast.makeText(CashActivity.this, "最小提现金额10元", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog = new RechargeDialog(CashActivity.this , "到账方式");
                dialog.setPayCall(CashActivity.this);
                dialog.hintWechat();
                dialog.showDialog();
            }
        });
    }
    private RechargeDialog dialog;
    @Override
    public void pay() {
        dialog.dismiss();
        myDialog = MyDialog.createLoadingDialog(this, "提交中...");
        cash(cashAmount);//提现
    }
    private void cash(String amount){
        RequestInterface request = new RequestInterface(this);
        Log.d("FC", amount+"===="+Integer.parseInt(userId));
        request.cash(Integer.parseInt(userId), amount);
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                myDialog.dismiss();
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                myDialog.dismiss();
            }
        });
    }
}

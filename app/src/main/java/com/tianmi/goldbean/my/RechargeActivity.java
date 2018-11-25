package com.tianmi.goldbean.my;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.alipay.sdk.app.PayTask;
//import com.mob.wrappers.PaySDKWrapper;
import com.alipay.sdk.app.PayTask;
import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.RechargeDialog;
import com.tianmi.goldbean.bean.RechargeBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.my.PayResult;

import java.io.IOException;
import java.util.Map;

import okhttp3.Request;

public class RechargeActivity extends BaseActivity implements RechargeDialog.MyPayCallBack {
    private Button rechargeBtn;
    private EditText numEdit;
    private String userId = DataUtil.getPreferences("userId", "");
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(RechargeActivity.this, "支付成功: ");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(RechargeActivity.this, "支付失败: ");
                    }
                    break;
                }

                default:
                    break;
            }
        };
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_recharge);
        initTitle("充值");
        init();
    }
    private RechargeDialog dialog;
    private void init(){
        numEdit = (EditText)findViewById(R.id.num_edit);
        rechargeBtn = (Button)findViewById(R.id.btn_confirm_recharge);
        rechargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numEdit.getText().toString() == null || numEdit.getText().toString().equals("") ||
                        Integer.parseInt(numEdit.getText().toString()) <= 0){
                    Toast.makeText(RechargeActivity.this, "请输入正确的充值金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog = new RechargeDialog(RechargeActivity.this, "选择支付方式");
                dialog.setPayCall(RechargeActivity.this);
                dialog.showDialog();
            }
        });
    }

    @Override
    public void pay(String imgFlag) {
        if(imgFlag.equals("1")){//支付宝
            dialog.dismiss();
            alipay(Integer.parseInt(numEdit.getText().toString()));
        }else {//微信
            Toast.makeText(this, "微信支付暂未开通", Toast.LENGTH_SHORT).show();
        }


    }
    private void alipay(int amount){
        RequestInterface request = new RequestInterface(this);
        request.alipay(Integer.parseInt(userId), amount+"");
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(Object object, String message) throws IOException {
                showAlipay((String)object);
            }
        });

    }
    private void showAlipay(final String orderInfo){
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton("确定", null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    private static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
}

package com.tianmi.goldbean.message;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.GoldApplication;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.MyDialog;
import com.tianmi.goldbean.bean.AccountWaterBean;
import com.tianmi.goldbean.login.LoginActivity;
import com.tianmi.goldbean.my.ManagerActivity;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;

import okhttp3.Request;

public class MessageItemActivity extends BaseActivity {
    private TextView msgText;
    private AccountWaterBean bean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_message_item);
        bean = (AccountWaterBean)getIntent().getExtras().getSerializable("bean");
        initTitle("消息内容");
        init();
    }
    private void init(){
        msgText = (TextView)findViewById(R.id.msg_content);
        switch (bean.getDealSource()){
            case 1:
                msgText.setText(bean.getCreateTime()+"充值"+bean.getDealAmount()+"元");
                break;
            case 2:
                msgText.setText(bean.getCreateTime()+"获得红包"+bean.getDealAmount()+"元");
                break;
            case 3:
                msgText.setText(bean.getCreateTime()+"获得分享奖励"+bean.getDealAmount()+"元");
                break;
            case 4:
                msgText.setText(bean.getCreateTime()+"红包退款"+bean.getDealAmount()+"元");
                break;
            case 5:
                msgText.setText(bean.getCreateTime()+"发红包"+bean.getDealAmount()+"元");
                break;
            case 6:
                msgText.setText(bean.getCreateTime()+"提现"+bean.getDealAmount()+"元");
                break;
        }

    }
}

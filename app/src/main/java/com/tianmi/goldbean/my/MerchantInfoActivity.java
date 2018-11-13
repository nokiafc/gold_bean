package com.tianmi.goldbean.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.net.BaseRequest;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Request;

public class MerchantInfoActivity extends BaseActivity {
    private TextView merchantName, merchantAddress, merchantPhone;
    private String merchantUserId = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchante_info);
        merchantUserId = getIntent().getStringExtra("merchantUserId");
        initTitle("商家信息");
        init();
        getMerchantInfo();
    }
    private void init(){
        merchantName = (TextView)findViewById(R.id.text_merchant_name);
        merchantAddress = (TextView)findViewById(R.id.text_merchant_address);
        merchantPhone = (TextView)findViewById(R.id.text_merchant_phone);

    }
    private void getMerchantInfo(){
        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.getMerchantsInfo(Integer.parseInt(merchantUserId));
    }


}

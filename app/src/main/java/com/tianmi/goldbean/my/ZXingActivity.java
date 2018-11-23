package com.tianmi.goldbean.my;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.GoldApplication;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.MyDialog;
import com.tianmi.goldbean.Utils.QRCodeUtil;
import com.tianmi.goldbean.Utils.Utils;
import com.tianmi.goldbean.login.LoginActivity;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;

import okhttp3.Request;

public class ZXingActivity extends BaseActivity implements View.OnClickListener{
    private ImageView zxingImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_zxing);
        initTitle("我的二维码");
        init();
    }
    private void init(){
        zxingImg = (ImageView)findViewById(R.id.zxing_img);
        zxingImg.setImageBitmap(QRCodeUtil.createQRCodeBitmap("https://www.baidu.com", 400));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}

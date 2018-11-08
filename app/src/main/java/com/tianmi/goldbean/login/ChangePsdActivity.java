package com.tianmi.goldbean.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;

public class ChangePsdActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psd);
        initTitle("修改密码");
    }
}

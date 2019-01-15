package com.tianmi.goldbean.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;

public class GoodsVerfiyActivity extends BaseActivity {
    private Button completeBtn,  friendBtn;
    private String goodsId = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_verify);
        goodsId = (String)getIntent().getStringExtra("goodsId");
        initTitle("审核");
        init();
    }
    private void init(){
        completeBtn = (Button)findViewById(R.id.btn_complete);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        friendBtn = findViewById(R.id.btn_friend);
        friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SetFriendActivity.class);
                i.putExtra("goodsId", goodsId);
                startActivity(i);
                finish();
            }
        });
    }
}

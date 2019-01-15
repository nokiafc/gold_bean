package com.tianmi.goldbean.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.net.RequestInterface;

import me.iwf.photopicker.widget.MultiPickResultView;

public class StartFriendActivity extends BaseActivity {
    private String goodsId = "";
    private TextView title;
    private MultiPickResultView multiPick;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_friend);
        goodsId = getIntent().getStringExtra("goodsId");
        init();
        getFriendInfo(Integer.parseInt(goodsId));
    }
    private void init(){
        title = findViewById(R.id.title);
        multiPick = (MultiPickResultView)findViewById(R.id.multiPick);

    }

    private void getFriendInfo(int goodsID){
        RequestInterface request = new RequestInterface(this);
        request.getFriendInfo(goodsID);

    }
}
